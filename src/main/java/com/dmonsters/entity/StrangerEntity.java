package com.dmonsters.entity;

import com.dmonsters.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

public final class StrangerEntity extends Monster {
    public StrangerEntity(EntityType<? extends Monster> type, Level level) {
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
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        this.playSound(ModSounds.STRANGER_ATTACK.get(), 1.0F, 1.0F);
        if (target instanceof Player) {
            this.playSound(ModSounds.STRANGER_IMPACT.get(), 1.0F, 1.0F);
            target.push(0.0D, this.getRandom().nextFloat() + 0.1F, 0.0D);
            target.hurtServer(level, level.damageSources().generic(), 10.0F);
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.STRANGER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.STRANGER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.STRANGER_DEATH.get();
    }
}
