package com.dmonsters.entity;

import com.dmonsters.registry.ModEntities;
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
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class ZombieChickenEntity extends Monster {
    public ZombieChickenEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }

        this.playSound(ModSounds.ZOMBIE_CHICKEN_ATTACK.get(), 1.0F, 1.0F);
        if (target instanceof Chicken chicken) {
            double x = chicken.getX();
            double y = chicken.getY();
            double z = chicken.getZ();
            float yaw = chicken.getYRot();
            float pitch = chicken.getXRot();
            chicken.discard();
            ZombieChickenEntity converted = new ZombieChickenEntity(ModEntities.ZOMBIE_CHICKEN.get(), level);
            converted.snapTo(x, y, z, yaw, pitch);
            level.addFreshEntity(converted);
        }
        return true;
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
    protected SoundEvent getAmbientSound() {
        return ModSounds.ZOMBIE_CHICKEN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ZOMBIE_CHICKEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ZOMBIE_CHICKEN_DEATH.get();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }
}
