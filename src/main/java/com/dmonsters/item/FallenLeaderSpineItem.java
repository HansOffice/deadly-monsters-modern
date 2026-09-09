package com.dmonsters.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class FallenLeaderSpineItem extends Item {
    public FallenLeaderSpineItem(Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();
        double length = Math.sqrt(dx * dx + dz * dz);
        if (length > 1.0E-4D) {
            target.push(dx / length * 4.0D, 0.4D, dz / length * 4.0D);
        }
    }
}
