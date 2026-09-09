package com.dmonsters.item;

import com.dmonsters.registry.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;

public final class SunlightDropItem extends Item {
    public SunlightDropItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel playerLevel)) {
            return InteractionResult.SUCCESS;
        }

        ServerLevel overworld = playerLevel.getServer().overworld();
        long time = overworld.getOverworldClockTime() % 24000L;
        if (time < 13000L) {
            return InteractionResult.FAIL;
        }

        if (overworld.getGameRules().get(GameRules.ADVANCE_TIME)) {
            Registry<WorldClock> clocks = overworld.registryAccess().lookupOrThrow(Registries.WORLD_CLOCK);
            Holder<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);
            overworld.clockManager().moveToTimeMarker(overworldClock, ClockTimeMarkers.DAY);
        }

        playerLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.SUNLIGHTDROP_USE.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        playerLevel.sendParticles(ParticleTypes.ENCHANT,
                player.getX(), player.getY() + 1.5D, player.getZ(),
                32, 1.0D, 1.0D, 1.0D, 0.02D);

        ItemStack stack = player.getItemInHand(hand);
        stack.shrink(1);
        return InteractionResult.SUCCESS;
    }
}
