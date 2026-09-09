package com.dmonsters.block;

import java.util.List;

import com.dmonsters.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;

public final class SoulEyeBlock extends Block {
    public static final EnumProperty<Mode> MODE = EnumProperty.create("mode", Mode.class);
    private static final List<Item> SOUL_DROPS = List.of(
            Items.EMERALD,
            Items.GOLD_NUGGET,
            Items.GUNPOWDER,
            Items.REDSTONE,
            Items.IRON_INGOT,
            Items.QUARTZ);

    public SoulEyeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MODE, Mode.SLEEP));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Mode mode = state.getValue(MODE);
        int light = level.getRawBrightness(pos, 0);

        if (light <= 12) {
            if (mode == Mode.SLEEP) {
                level.setBlockAndUpdate(pos, state.setValue(MODE, Mode.AWAKING));
            } else if (mode == Mode.AWAKING) {
                level.setBlockAndUpdate(pos, state.setValue(MODE, Mode.AWAKE));
            } else {
                consumeNearbyMobs(level, pos, random);
            }
        } else if (mode == Mode.AWAKING) {
            level.setBlockAndUpdate(pos, state.setValue(MODE, Mode.SLEEP));
        } else if (mode == Mode.AWAKE) {
            level.setBlockAndUpdate(pos, state.setValue(MODE, Mode.AWAKING));
        }
    }

    private static void consumeNearbyMobs(ServerLevel level, BlockPos pos, RandomSource random) {
        AABB area = new AABB(
                pos.getX() - 4.0D, pos.getY(), pos.getZ() - 4.0D,
                pos.getX() + 4.0D, pos.getY() + 4.0D, pos.getZ() + 4.0D);
        boolean consumed = false;
        for (Mob mob : level.getEntitiesOfClass(Mob.class, area)) {
            if (random.nextFloat() <= 0.5F) {
                mob.spawnAtLocation(level, SOUL_DROPS.get(random.nextInt(SOUL_DROPS.size())));
            }
            level.sendParticles(
                    ParticleTypes.SMOKE,
                    mob.getX(), mob.getY(0.5D), mob.getZ(),
                    12, 0.25D, 0.35D, 0.25D, 0.015D);
            mob.discard();
            consumed = true;
        }
        if (consumed) {
            level.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    ModSounds.BLOCK_SOULEYE_KILL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.sendParticles(ParticleTypes.FLAME,
                    pos.getX() + 0.5D, pos.getY() + 0.75D, pos.getZ() + 0.5D,
                    15, 0.45D, 0.5D, 0.45D, 0.025D);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(MODE) != Mode.AWAKE) {
            return;
        }
        for (int x = -4; x < 4; x++) {
            for (int z = -4; z < 4; z++) {
                level.addParticle(
                        ParticleTypes.SMOKE,
                        pos.getX() + x + 0.5D + random.nextDouble(),
                        pos.getY() + random.nextDouble(),
                        pos.getZ() + z + 0.5D + random.nextDouble(),
                        random.nextGaussian() * 0.001D,
                        Math.abs(random.nextGaussian() * 0.02D),
                        random.nextGaussian() * 0.001D);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

    public enum Mode implements StringRepresentable {
        SLEEP("sleep"),
        AWAKING("awaking"),
        AWAKE("awake");

        private final String serializedName;

        Mode(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return this.serializedName;
        }
    }
}
