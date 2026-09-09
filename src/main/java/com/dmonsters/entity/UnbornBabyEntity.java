package com.dmonsters.entity;

import com.dmonsters.config.DeadlyMonstersConfig;
import com.dmonsters.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class UnbornBabyEntity extends Monster {
    private int blindRefreshTick;

    public UnbornBabyEntity(EntityType<? extends Monster> type, Level level) {
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
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
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

        if (!this.level().isClientSide()
                && DeadlyMonstersConfig.VALUES.babyBlindness.get()
                && this.isAggressive()
                && this.getTarget() != null) {
            if (++this.blindRefreshTick >= 20) {
                this.blindRefreshTick = 0;
                this.getTarget().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
            }
        } else {
            this.blindRefreshTick = 0;
        }
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 600));
        }
        this.playSound(ModSounds.BABY_ATTACK.get(), 1.0F, 1.0F);
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BABY_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BABY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BABY_DEATH.get();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }
}
