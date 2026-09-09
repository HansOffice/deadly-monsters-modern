package com.dmonsters.entity;

import com.dmonsters.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class FreezerEntity extends Monster {
    private static final int FREEZE_INTERVAL = 40;
    private int freezeTicks;

    public FreezerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }

        if (target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 600));
        }
        this.playSound(ModSounds.FREEZER_ATTACK.get(), 1.0F, 1.0F);
        return true;
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide()) {
            if (this.level().isBrightOutside()
                    && this.level().canSeeSky(this.blockPosition())
                    && this.getLightLevelDependentMagicValue() > 0.5F
                    && this.getRandom().nextFloat() < 0.05F) {
                this.igniteForSeconds(8.0F);
            }

            if (++this.freezeTicks >= FREEZE_INTERVAL) {
                this.freezeEnvironment();
                this.freezeTicks = 0;
            }
        } else {
            this.spawnSnowParticle();
        }

        super.aiStep();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.FREEZER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.FREEZER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.FREEZER_DEATH.get();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    private void freezeEnvironment() {
        int radius = this.isAggressive() ? 2 : 0;
        BlockPos origin = this.blockPosition();

        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = origin.offset(dx, yOffset, dz);
                    if (this.level().getBlockState(pos).is(Blocks.WATER)) {
                        this.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                    }
                }
            }
        }

        BlockState snow = Blocks.SNOW.defaultBlockState();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = origin.offset(dx, 0, dz);
                if (this.level().getBlockState(pos).isAir() && snow.canSurvive(this.level(), pos)) {
                    this.level().setBlockAndUpdate(pos, snow);
                }
            }
        }
    }

    private void spawnSnowParticle() {
        double motionX = this.getRandom().nextGaussian() * 0.15D;
        double motionY = this.getRandom().nextGaussian() * 0.15D;
        double motionZ = this.getRandom().nextGaussian() * 0.15D;
        double width = this.getBbWidth();
        double height = this.getBbHeight();
        this.level().addParticle(
                ParticleTypes.SNOWFLAKE,
                this.getX() + this.getRandom().nextFloat() * width * 2.0D - width,
                this.getY() + 0.5D + this.getRandom().nextFloat() * height,
                this.getZ() + this.getRandom().nextFloat() * width * 2.0D - width,
                motionX,
                motionY,
                motionZ);
    }
}
