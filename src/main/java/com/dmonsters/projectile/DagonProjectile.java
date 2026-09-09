package com.dmonsters.projectile;

import com.dmonsters.registry.ModEntities;
import com.dmonsters.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class DagonProjectile extends ThrowableItemProjectile {
    public DagonProjectile(EntityType<? extends DagonProjectile> type, Level level) {
        super(type, level);
    }

    public DagonProjectile(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.DAGON_PROJECTILE.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FLYING_DAGON.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel level) {
            this.spawnAtLocation(level, ModItems.DAGON.get());
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(
                        ParticleTypes.ENCHANTED_HIT,
                        this.getX(), this.getY(), this.getZ(),
                        (this.random.nextFloat() - 0.5D) * 0.08D,
                        (this.random.nextFloat() - 0.5D) * 0.08D,
                        (this.random.nextFloat() - 0.5D) * 0.08D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }
}
