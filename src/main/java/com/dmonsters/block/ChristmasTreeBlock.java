package com.dmonsters.block;

import java.util.ArrayList;
import java.util.List;

import com.dmonsters.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ChristmasTreeBlock extends Block {
    private static final VoxelShape OUTLINE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public ChristmasTreeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() >= 0.99F) {
            return;
        }

        List<BlockPos> free = new ArrayList<>(4);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos candidate = pos.relative(direction);
            if (level.getBlockState(candidate).isAir()) {
                free.add(candidate);
            }
        }
        if (!free.isEmpty()) {
            level.setBlockAndUpdate(free.get(random.nextInt(free.size())), ModBlocks.PRESENT_BOX.get().defaultBlockState());
        }
    }
}
