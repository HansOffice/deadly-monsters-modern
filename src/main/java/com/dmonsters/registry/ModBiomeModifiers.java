package com.dmonsters.registry;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.world.ConfigSpawnBiomeModifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DeadlyMonsters.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConfigSpawnBiomeModifier>> CONFIG_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("config_spawns", () -> RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConfigSpawnBiomeModifier::biomes),
                    MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawner").forGetter(ConfigSpawnBiomeModifier::spawner)
            ).apply(instance, ConfigSpawnBiomeModifier::new)));

    private ModBiomeModifiers() {
    }
}
