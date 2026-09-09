package com.dmonsters.block;

import com.dmonsters.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class StrengthenedBlock extends Block {
    private final BlockState revertedState;

    public StrengthenedBlock(BlockBehaviour.Properties properties, BlockState revertedState) {
        super(properties);
        this.revertedState = revertedState;
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) {
            return this.revert(level, pos);
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack itemStack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) {
            return this.revert(level, pos);
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    private InteractionResult revert(Level level, BlockPos pos) {
        if (!level.isClientSide()) {
            Block.popResource(level, pos, new ItemStack(ModItems.REBAR.get()));
            level.setBlockAndUpdate(pos, this.revertedState);
        }
        return InteractionResult.SUCCESS;
    }
}
