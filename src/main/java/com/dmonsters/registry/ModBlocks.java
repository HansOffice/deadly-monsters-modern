package com.dmonsters.registry;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.block.BarbedWireBlock;
import com.dmonsters.block.ChristmasTreeBlock;
import com.dmonsters.block.DumpBlock;
import com.dmonsters.block.MeshFenceBlock;
import com.dmonsters.block.MeshFencePoleBlock;
import com.dmonsters.block.PresentBlock;
import com.dmonsters.block.PresentBoxBlock;
import com.dmonsters.block.SoulEyeBlock;
import com.dmonsters.block.StrengthenedBlock;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DeadlyMonsters.MOD_ID);

    public static final DeferredBlock<StrengthenedBlock> STRENGTHENED_STONE = BLOCKS.registerBlock(
            "strengthened_stone",
            properties -> new StrengthenedBlock(properties.strength(10.0F, 25.0F), Blocks.STONE.defaultBlockState()));

    public static final DeferredBlock<StrengthenedBlock> STRENGTHENED_COBBLESTONE = BLOCKS.registerBlock(
            "strengthened_cobblestone",
            properties -> new StrengthenedBlock(properties.strength(10.0F, 25.0F), Blocks.COBBLESTONE.defaultBlockState()));

    public static final DeferredBlock<BarbedWireBlock> BARBED_WIRE = BLOCKS.registerBlock(
            "barbed_wire",
            properties -> new BarbedWireBlock(properties.strength(1.0F, 1.0F).noOcclusion()));

    public static final DeferredBlock<MeshFenceBlock> MESH_FENCE = BLOCKS.registerBlock(
            "mesh_fence",
            properties -> new MeshFenceBlock(properties.strength(5.0F, 5.0F).noOcclusion()));

    public static final DeferredBlock<MeshFencePoleBlock> MESH_FENCE_POLE = BLOCKS.registerBlock(
            "mesh_fence_pole",
            properties -> new MeshFencePoleBlock(properties.strength(5.0F, 5.0F).noOcclusion()));

    public static final DeferredBlock<DumpBlock> DUMP = BLOCKS.registerBlock(
            "dump",
            properties -> new DumpBlock(properties.strength(1.0F, 1.0F).noOcclusion()));

    public static final DeferredBlock<SoulEyeBlock> SOUL_EYE = BLOCKS.registerBlock(
            "soul_eye",
            properties -> new SoulEyeBlock(properties.strength(3.0F, 3.0F).randomTicks().noOcclusion()));

    public static final DeferredBlock<PresentBlock> PRESENT_BLOCK = BLOCKS.registerBlock(
            "present_block",
            properties -> new PresentBlock(properties.strength(3.0F, 50.0F).randomTicks()));

    public static final DeferredBlock<ChristmasTreeBlock> CHRISTMAS_TREE = BLOCKS.registerBlock(
            "christmas_tree",
            properties -> new ChristmasTreeBlock(properties.strength(2.0F, 50.0F).randomTicks().noOcclusion()));

    public static final DeferredBlock<PresentBoxBlock> PRESENT_BOX = BLOCKS.registerBlock(
            "present_box",
            properties -> new PresentBoxBlock(properties.strength(1.0F, 50.0F).noOcclusion()));

    private ModBlocks() {}
}
