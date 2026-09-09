package com.dmonsters.item;

import com.dmonsters.registry.ModBlocks;
import com.dmonsters.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public final class PoopooPillItem extends Item {
    public PoopooPillItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.canEat(true)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player) || !(level instanceof ServerLevel serverLevel)) {
            return stack;
        }

        if (player.getFoodData().getFoodLevel() < 20) {
            player.sendSystemMessage(Component.translatable("msg.dmonsters.poopoo_pill.error").withStyle(ChatFormatting.DARK_RED));
            if (player.getHealth() > 1.0F) {
                player.setHealth(1.0F);
            } else {
                player.hurtServer(serverLevel, player.damageSources().generic(), 999.0F);
            }
            return stack;
        }

        stack.shrink(1);
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100));
        player.getFoodData().setFoodLevel(2);
        player.getFoodData().setSaturation(0.0F);
        serverLevel.setBlockAndUpdate(player.blockPosition(), ModBlocks.DUMP.get().defaultBlockState());
        serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.DUMP_MAKE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        serverLevel.sendParticles(
                ParticleTypes.POOF,
                player.getX(), player.getY() + 0.5D, player.getZ(),
                24, 0.4D, 0.5D, 0.4D, 0.03D);
        return stack;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 32;
    }
}
