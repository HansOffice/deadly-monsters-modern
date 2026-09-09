package com.dmonsters.item;

import com.dmonsters.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class RebarItem extends Item {
    public RebarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        BlockState replacement;
        if (state.is(Blocks.STONE)) {
            replacement = ModBlocks.STRENGTHENED_STONE.get().defaultBlockState();
        } else if (state.is(Blocks.COBBLESTONE)) {
            replacement = ModBlocks.STRENGTHENED_COBBLESTONE.get().defaultBlockState();
        } else {
            return InteractionResult.PASS;
        }

        Player player = context.getPlayer();
        if (player != null && !player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand())) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, replacement);
            context.getItemInHand().consume(1, player);
        }

        level.playSound(player, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F,
                level.getRandom().nextFloat() * 0.4F + 0.8F);
        return InteractionResult.SUCCESS;
    }
}
