package com.dmonsters.projectile;

import java.util.List;

import com.dmonsters.entity.ZombieChickenEntity;
import com.dmonsters.registry.ModEntities;
import com.dmonsters.registry.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class LuckyEggProjectile extends ThrowableItemProjectile {
    private static final List<Item> TIER_0 = List.of(
            Items.WOODEN_SWORD, Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS);
    private static final List<Item> TIER_1 = List.of(
            Items.IRON_SWORD, Items.STONE_SWORD, Items.IRON_BOOTS, Items.IRON_CHESTPLATE, Items.IRON_HELMET, Items.IRON_LEGGINGS);
    private static final List<Item> TIER_2 = List.of(
            Items.SHIELD, Items.BOW, Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_LEGGINGS);
    private static final List<Item> TIER_3 = List.of(
            Items.GOLDEN_SWORD, Items.GOLDEN_BOOTS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET, Items.GOLDEN_LEGGINGS);
    private static final List<Item> TIER_4 = List.of(
            Items.DIAMOND_SWORD, Items.DIAMOND_BOOTS, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_HELMET, Items.DIAMOND_LEGGINGS);

    public LuckyEggProjectile(EntityType<? extends LuckyEggProjectile> type, Level level) {
        super(type, level);
    }

    public LuckyEggProjectile(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.LUCKY_EGG_PROJECTILE.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.LUCKY_EGG.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }

        if (this.random.nextInt(10) == 0) {
            this.spawnReward(level);
        } else if (this.random.nextInt(10) == 1) {
            this.spawnTnt(level);
        } else if (this.random.nextInt(10) <= 4) {
            this.spawnChicken(level);
        } else {
            this.spawnZombieChicken(level);
        }

        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }

    private void spawnReward(ServerLevel level) {
        ItemStack stack = new ItemStack(this.randomReward());
        int maxDamage = stack.getMaxDamage();
        if (maxDamage > 0) {
            int bonusRepair = (int) (maxDamage * 0.2F);
            int damage = this.random.nextInt(maxDamage) - bonusRepair;
            if (damage > 0) {
                stack.setDamageValue(damage);
            }
        }
        this.spawnAtLocation(level, stack);
    }

    private Item randomReward() {
        float tier = this.random.nextFloat();
        List<Item> list = tier <= 0.30F ? TIER_0
                : tier <= 0.55F ? TIER_1
                : tier <= 0.75F ? TIER_2
                : tier <= 0.90F ? TIER_3
                : TIER_4;
        return list.get(this.random.nextInt(list.size()));
    }

    private void spawnTnt(ServerLevel level) {
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        PrimedTnt tnt = new PrimedTnt(level, this.getX(), this.getY(), this.getZ(), owner);
        int fuse = tnt.getFuse();
        tnt.setFuse((short) (this.random.nextInt(Math.max(1, fuse / 4)) + Math.max(1, fuse / 8)));
        level.addFreshEntity(tnt);
    }

    private void spawnChicken(ServerLevel level) {
        Chicken chicken = EntityTypes.CHICKEN.create(level, EntitySpawnReason.TRIGGERED);
        if (chicken != null) {
            chicken.setAge(-24000);
            chicken.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            level.addFreshEntity(chicken);
        }
    }

    private void spawnZombieChicken(ServerLevel level) {
        ZombieChickenEntity chicken = ModEntities.ZOMBIE_CHICKEN.get().create(level, EntitySpawnReason.TRIGGERED);
        if (chicken != null) {
            chicken.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            level.addFreshEntity(chicken);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemParticleOption eggParticle = new ItemParticleOption(ParticleTypes.ITEM, Items.EGG);
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(
                        eggParticle,
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
