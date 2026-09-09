package com.dmonsters.entity;

import com.dmonsters.registry.ModSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class EntrailEntity extends Monster {
    private static final Identifier SLIME_ID = Identifier.fromNamespaceAndPath("minecraft", "slime");

    public EntrailEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
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

        Vec3 motion = this.getDeltaMovement();
        if (!this.onGround() && motion.y < 0.0D) {
            this.setDeltaMovement(motion.x, motion.y * 0.6D, motion.z);
        }
        super.aiStep();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (!source.is(DamageTypeTags.IS_FIRE)) {
            EntityType<?> slimeType = BuiltInRegistries.ENTITY_TYPE.getValue(SLIME_ID);
            if (slimeType != null) {
                Entity entity = slimeType.create(level, EntitySpawnReason.TRIGGERED);
                if (entity instanceof Slime slime) {
                    slime.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    level.addFreshEntity(slime);
                }
            }
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        this.playSound(ModSounds.ENTRAIL_ATTACK.get(), 1.0F, 1.0F);
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTRAIL_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTRAIL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTRAIL_DEATH.get();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }
}
