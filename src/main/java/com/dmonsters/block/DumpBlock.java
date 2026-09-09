package com.dmonsters.block;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class DumpBlock extends Block {
    public static final IntegerProperty STACKS = IntegerProperty.create("stacks", 0, 15);
    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.4D, 12.0D);

    public DumpBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STACKS, 0));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void neighborChanged(
            BlockState state,
            Level level,
            BlockPos pos,
            Block changedBlock,
            @Nullable Orientation orientation,
            boolean movedByPiston) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos saplingPos = pos.relative(direction);
            BlockState saplingState = serverLevel.getBlockState(saplingPos);
            if (saplingState.getBlock() instanceof SaplingBlock sapling) {
                consumeLayer(serverLevel, pos, state);
                forceGrowSapling(serverLevel, saplingPos, sapling, saplingState);
                return;
            }
        }
    }

    private static void consumeLayer(ServerLevel level, BlockPos pos, BlockState state) {
        int next = state.getValue(STACKS) + 1;
        if (next < 4) {
            level.setBlockAndUpdate(pos, state.setValue(STACKS, next));
        } else {
            level.removeBlock(pos, false);
        }
    }

    private static void forceGrowSapling(ServerLevel level, BlockPos pos, SaplingBlock sapling, BlockState state) {
        RandomSource random = level.getRandom();
        sapling.advanceTree(level, pos, state, random);
        BlockState advanced = level.getBlockState(pos);
        if (advanced.getBlock() == sapling) {
            sapling.advanceTree(level, pos, advanced, random);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        level.addParticle(
                ParticleTypes.POOF,
                pos.getX() + 0.25D + random.nextDouble() * 0.5D,
                pos.getY() + random.nextDouble() * 0.4D,
                pos.getZ() + 0.25D + random.nextDouble() * 0.5D,
                0.0D,
                Math.abs(random.nextGaussian() * 0.02D),
                0.0D);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STACKS);
    }
}
