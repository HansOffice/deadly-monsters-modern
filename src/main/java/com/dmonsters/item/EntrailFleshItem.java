package com.dmonsters.item;

import com.dmonsters.entity.EntrailEntity;
import com.dmonsters.registry.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class EntrailFleshItem extends Item {
    public EntrailFleshItem(Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker.level() instanceof ServerLevel level) || target instanceof EntrailEntity) {
            return;
        }

        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();
        float yaw = target.getYRot();
        target.discard();

        EntrailEntity entrail = ModEntities.ENTRAIL.get().create(level, EntitySpawnReason.TRIGGERED);
        if (entrail != null) {
            entrail.snapTo(x, y, z, yaw, 0.0F);
            level.addFreshEntity(entrail);
        }
        stack.shrink(1);
    }
}
