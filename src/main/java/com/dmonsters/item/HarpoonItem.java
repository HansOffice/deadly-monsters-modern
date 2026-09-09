package com.dmonsters.item;

import java.util.function.Consumer;

import com.dmonsters.config.DeadlyMonstersConfig;
import com.dmonsters.entity.TopielecEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;

public final class HarpoonItem extends Item {
    private final float topielecDamage;

    public HarpoonItem(Properties properties, float topielecDamage) {
        super(properties);
        this.topielecDamage = topielecDamage;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        if (!context.getLevel().mayInteract(player, context.getClickedPos())) {
            return InteractionResult.FAIL;
        }
        if (!(context.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }

        if (player.isInWater()) {
            context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            if (level.getRandom().nextFloat() < 0.25F) {
                player.spawnAtLocation(level, Items.COD);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            float damage = target instanceof TopielecEntity ? this.topielecDamage : 1.0F;
            target.hurtServer(level, target.damageSources().generic(), damage);
        }
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag) {
        tooltip.accept(Component.translatable("item.dmonsters.add_information.harpoon_1").withStyle(ChatFormatting.GRAY));
        if (DeadlyMonstersConfig.VALUES.topielecHarpoonOnly.get()) {
            tooltip.accept(Component.translatable("item.dmonsters.add_information.harpoon_2").withStyle(ChatFormatting.GRAY));
        }
    }
}
