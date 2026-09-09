package com.dmonsters.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class UnbornBabyEyeItem extends Item {
    public UnbornBabyEyeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (context.getPlayer() != null && !level.mayInteract(context.getPlayer(), pos)) {
            return InteractionResult.FAIL;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            return InteractionResult.FAIL;
        }

        Block block = state.getBlock();
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (block.asItem() != net.minecraft.world.item.Items.AIR) {
            Block.popResource(level, pos, new ItemStack(block.asItem()));
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.SUCCESS;
    }
}
