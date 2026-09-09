package com.dmonsters.entity;

import com.dmonsters.config.DeadlyMonstersConfig;
import com.dmonsters.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class TopielecEntity extends Monster {
    private Vec3 movementVector = Vec3.ZERO;
    private Vec3 lastWaterPosition;
    private int deepWaterRefresh;

    public TopielecEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }

        this.refreshPlayerTarget(level);
        if (this.isInWater()) {
            this.lastWaterPosition = this.position();
            this.setAirSupply(300);
            this.updateWaterMovement(level);
        } else {
            this.updateOutOfWater(level);
        }
    }

    private void refreshPlayerTarget(ServerLevel level) {
        double searchDistance = DeadlyMonstersConfig.VALUES.topielecSearchDistance.get();
        LivingEntity target = this.getTarget();
        if (target instanceof Player player
                && player.isAlive()
                && !player.isSpectator()
                && !player.isCreative()
                && player.distanceToSqr(this) <= searchDistance * searchDistance) {
            return;
        }
        Player nearest = level.getNearestPlayer(this, searchDistance);
        if (nearest != null && !nearest.isSpectator() && !nearest.isCreative()) {
            this.setTarget(nearest);
        } else {
            this.setTarget(null);
        }
    }

    private void updateWaterMovement(ServerLevel level) {
        LivingEntity target = this.getTarget();
        if (target instanceof Player player) {
            if (this.distanceTo(player) < 2.0F && !player.isCreative() && !player.isPassenger()) {
                player.teleportTo(this.getX(), this.getY(), this.getZ());
                if (this.deepWaterRefresh-- <= 0 || this.movementVector.lengthSqr() < 1.0E-4D) {
                    this.deepWaterRefresh = 40;
                    Vec3 deepDirection = this.findDeepWaterDirection(level);
                    if (deepDirection.lengthSqr() > 1.0E-4D) {
                        this.movementVector = deepDirection.normalize().scale(0.5D);
                    }
                }
            } else {
                this.deepWaterRefresh = 0;
                Vec3 toward = player.position().subtract(this.position());
                if (toward.lengthSqr() > 1.0E-4D) {
                    this.movementVector = toward.normalize().scale(0.5D);
                }
            }

            if (this.movementVector.lengthSqr() > 1.0E-4D) {
                this.setYRot((float) (Math.atan2(this.movementVector.z, this.movementVector.x) * 180.0D / Math.PI));
            }
        } else if (this.getRandom().nextInt(50) == 0 || this.movementVector.lengthSqr() < 1.0E-4D) {
            this.deepWaterRefresh = 0;
            float angle = this.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            this.movementVector = new Vec3(
                    Math.cos(angle) * 0.2D,
                    -0.1D + this.getRandom().nextFloat() * 0.2D,
                    Math.sin(angle) * 0.2D);
        }
        this.setDeltaMovement(this.movementVector);
    }

    private Vec3 findDeepWaterDirection(ServerLevel level) {
        int radius = DeadlyMonstersConfig.VALUES.topielecSearchDistance.get();
        int step = Math.max(1, radius / 16);
        BlockPos origin = this.blockPosition();
        BlockPos best = origin;
        int bestDepth = 0;

        for (int dx = -radius; dx <= radius; dx += step) {
            for (int dz = -radius; dz <= radius; dz += step) {
                int depth = 0;
                for (int y = origin.getY(); y > level.getMinY(); y--) {
                    BlockPos probe = new BlockPos(origin.getX() + dx, y, origin.getZ() + dz);
                    if (!level.getFluidState(probe).is(FluidTags.WATER)) {
                        break;
                    }
                    depth++;
                }
                if (depth > bestDepth) {
                    bestDepth = depth;
                    best = new BlockPos(origin.getX() + dx, origin.getY(), origin.getZ() + dz);
                }
            }
        }

        return new Vec3(best.getX() - origin.getX(), 0.0D, best.getZ() - origin.getZ());
    }

    private void updateOutOfWater(ServerLevel level) {
        if (this.lastWaterPosition != null) {
            this.teleportTo(this.lastWaterPosition.x, this.lastWaterPosition.y, this.lastWaterPosition.z);
        }
        this.setDeltaMovement(0.0D, -0.5D, 0.0D);
        int air = this.getAirSupply() - 1;
        this.setAirSupply(air);
        if (air <= -20) {
            this.setAirSupply(0);
            this.hurtServer(level, this.damageSources().drown(), 2.0F);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TOPIELEC_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.TOPIELEC_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TOPIELEC_DEATH.get();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }
}
