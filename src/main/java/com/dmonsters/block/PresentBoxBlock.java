package com.dmonsters.block;

import java.util.List;

import com.dmonsters.registry.ModBlocks;
import com.dmonsters.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class PresentBoxBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(2.08D, 0.0D, 2.08D, 13.92D, 12.0D, 13.92D);
    private static final List<EntityType<? extends Mob>> MONSTERS = List.of(
            EntityTypes.CREEPER,
            EntityTypes.ZOMBIE,
            EntityTypes.SKELETON,
            EntityTypes.SILVERFISH,
            EntityTypes.BLAZE,
            EntityTypes.MAGMA_CUBE,
            EntityTypes.ZOMBIE_HORSE,
            EntityTypes.SKELETON_HORSE,
            EntityTypes.ZOMBIFIED_PIGLIN);

    public PresentBoxBlock(BlockBehaviour.Properties properties) {
        super(properties);
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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return openPresent(level, pos, player);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult) {
        return openPresent(level, pos, player);
    }

    private static InteractionResult openPresent(Level level, BlockPos pos, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        RandomSource random = serverLevel.getRandom();
        float roll = random.nextFloat();
        if (roll < 0.70F) {
            serverLevel.removeBlock(pos, false);
            serverLevel.explode(
                    null, null, null,
                    pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    1.0F, true, Level.ExplosionInteraction.BLOCK);
        } else if (roll < 0.80F) {
            Block.popResource(serverLevel, pos, new ItemStack(randomReward(random)));
            serverLevel.removeBlock(pos, false);
        } else if (roll < 0.95F) {
            spawnRandomMonster(serverLevel, pos, player, random);
            serverLevel.removeBlock(pos, false);
        } else {
            serverLevel.setBlockAndUpdate(
                    pos,
                    ModBlocks.DUMP.get().defaultBlockState().setValue(DumpBlock.STACKS, random.nextInt(5)));
        }
        return InteractionResult.SUCCESS;
    }

    private static Item randomReward(RandomSource random) {
        Item[] rewards = {
                Items.APPLE,
                Items.GOLD_NUGGET,
                Items.LEATHER_HELMET,
                Items.COD,
                Items.REDSTONE,
                ModItems.MOB_SPAWNER_ITEM_PRESENT.get(),
                Items.GUNPOWDER,
                Items.REDSTONE,
                Items.IRON_INGOT,
                Items.IRON_SWORD
        };
        return rewards[random.nextInt(rewards.length)];
    }

    private static void spawnRandomMonster(ServerLevel level, BlockPos pos, Player player, RandomSource random) {
        EntityType<? extends Mob> type = MONSTERS.get(random.nextInt(MONSTERS.size()));
        Mob mob = type.create(level, EntitySpawnReason.TRIGGERED);
        if (mob != null) {
            mob.snapTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, player.getYRot(), 0.0F);
            level.addFreshEntity(mob);
        }
    }
}
