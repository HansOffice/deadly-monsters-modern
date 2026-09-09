package com.dmonsters.block;

import com.dmonsters.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class MeshFencePoleBlock extends FenceBlock {
    public MeshFencePoleBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean connectsTo(BlockState state, boolean faceSolid, Direction direction) {
        return state.is(ModBlocks.MESH_FENCE.get()) || state.is(ModBlocks.MESH_FENCE_POLE.get());
    }
}
