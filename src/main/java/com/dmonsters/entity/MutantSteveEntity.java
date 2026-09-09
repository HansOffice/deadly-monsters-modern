package com.dmonsters.entity;

import com.dmonsters.config.DeadlyMonstersConfig;
import com.dmonsters.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;

public final class MutantSteveEntity extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_ARMS_RAISED =
            SynchedEntityData.defineId(MutantSteveEntity.class, EntityDataSerializers.BOOLEAN);

    public MutantSteveEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        if (DeadlyMonstersConfig.VALUES.mutantSteveBreakBlocks.get()) {
            this.goalSelector.addGoal(1, new MutantSteveAttackGoal(this, 2.0D, false));
        } else {
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0D, false));
        }
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_ARMS_RAISED, false);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide()
                && this.level().isBrightOutside()
                && this.level().canSeeSky(this.blockPosition())
                && this.getLightLevelDependentMagicValue() > 0.5F
                && this.getRandom().nextFloat() < 0.05F) {
            this.igniteForSeconds(8.0F);
        }
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            this.playSound(ModSounds.MUTANT_ATTACK.get(), 1.0F, 1.0F);
            living.knockback(
                    2.0D,
                    target.getX() - this.getX(),
                    target.getZ() - this.getZ(),
                    level.damageSources().mobAttack(this),
                    (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        return true;
    }

    public boolean isArmsRaised() {
        return this.entityData.get(DATA_ARMS_RAISED);
    }

    private void setArmsRaised(boolean raised) {
        this.entityData.set(DATA_ARMS_RAISED, raised);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MUTANT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.MUTANT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MUTANT_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

    private static final class MutantSteveAttackGoal extends MeleeAttackGoal {
        private final MutantSteveEntity mutant;
        private int raiseArmTicks;
        private int breakTicks;

        private MutantSteveAttackGoal(MutantSteveEntity mutant, double speed, boolean longMemory) {
            super(mutant, speed, longMemory);
            this.mutant = mutant;
        }

        @Override
        public void start() {
            super.start();
            this.raiseArmTicks = 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.mutant.setArmsRaised(false);
        }

        @Override
        public void tick() {
            super.tick();
            this.raiseArmTicks++;
            this.mutant.setArmsRaised(this.raiseArmTicks >= 5 && this.raiseArmTicks < 10);
            if (++this.breakTicks >= 20 && !this.mutant.isInWater()) {
                this.breakTicks = 0;
                if (this.mutant.level() instanceof ServerLevel level
                        && level.getGameRules().get(GameRules.MOB_GRIEFING)) {
                    boolean destroyed = this.destroyAround(level, 0, 0.25F);
                    destroyed |= this.destroyAround(level, 1, 0.5F);
                    destroyed |= this.destroyAround(level, 2, 0.75F);
                    if (destroyed) {
                        this.mutant.swing(InteractionHand.MAIN_HAND);
                    }
                }
            }
        }

        private boolean destroyAround(ServerLevel level, int yOffset, float chance) {
            boolean destroyed = false;
            BlockPos origin = this.mutant.blockPosition();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if ((dx == 0 && dz == 0) || this.mutant.getRandom().nextFloat() >= chance) {
                        continue;
                    }
                    BlockPos pos = origin.offset(dx, yOffset, dz);
                    BlockState state = level.getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getDestroySpeed(level, pos);
                    if (hardness > -1.0F && hardness < 5.0F) {
                        destroyed |= level.destroyBlock(pos, true);
                    }
                }
            }
            return destroyed;
        }
    }
}
