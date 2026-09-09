package com.dmonsters.world;

import com.dmonsters.config.DeadlyMonstersConfig;
import com.dmonsters.registry.ModBiomeModifiers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record ConfigSpawnBiomeModifier(
        HolderSet<Biome> biomes,
        MobSpawnSettings.SpawnerData spawner) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !this.biomes.contains(biome)) {
            return;
        }

        EntityType<?> type = this.spawner.type();
        if (!DeadlyMonstersConfig.naturalSpawnsEnabled(type)) {
            return;
        }

        int weight = DeadlyMonstersConfig.spawnRate(type);
        if (weight <= 0) {
            return;
        }

        MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
        spawns.addSpawn(type.getCategory(), weight, this.spawner);
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return ModBiomeModifiers.CONFIG_SPAWNS.get();
    }
}
