package com.dmonsters.registry;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.item.BloodyMaidenHeartItem;
import com.dmonsters.item.DagonItem;
import com.dmonsters.item.EntrailFleshItem;
import com.dmonsters.item.FallenLeaderSpineItem;
import com.dmonsters.item.HarpoonItem;
import com.dmonsters.item.LuckyEggItem;
import com.dmonsters.item.PoopooPillItem;
import com.dmonsters.item.RebarItem;
import com.dmonsters.item.SunlightDropItem;
import com.dmonsters.item.UnbornBabyEyeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DeadlyMonsters.MOD_ID);

    public static final DeferredItem<BlockItem> STRENGTHENED_STONE = ITEMS.registerSimpleBlockItem("strengthened_stone", ModBlocks.STRENGTHENED_STONE);
    public static final DeferredItem<BlockItem> STRENGTHENED_COBBLESTONE = ITEMS.registerSimpleBlockItem("strengthened_cobblestone", ModBlocks.STRENGTHENED_COBBLESTONE);
    public static final DeferredItem<BlockItem> BARBED_WIRE = ITEMS.registerSimpleBlockItem("barbed_wire", ModBlocks.BARBED_WIRE);
    public static final DeferredItem<BlockItem> MESH_FENCE = ITEMS.registerSimpleBlockItem("mesh_fence", ModBlocks.MESH_FENCE);
    public static final DeferredItem<BlockItem> MESH_FENCE_POLE = ITEMS.registerSimpleBlockItem("mesh_fence_pole", ModBlocks.MESH_FENCE_POLE);
    public static final DeferredItem<BlockItem> DUMP = ITEMS.registerSimpleBlockItem("dump", ModBlocks.DUMP);
    public static final DeferredItem<BlockItem> SOUL_EYE = ITEMS.registerSimpleBlockItem("soul_eye", ModBlocks.SOUL_EYE);
    public static final DeferredItem<BlockItem> PRESENT_BLOCK = ITEMS.registerSimpleBlockItem("present_block", ModBlocks.PRESENT_BLOCK);
    public static final DeferredItem<BlockItem> CHRISTMAS_TREE = ITEMS.registerSimpleBlockItem("christmas_tree", ModBlocks.CHRISTMAS_TREE);
    public static final DeferredItem<BlockItem> PRESENT_BOX = ITEMS.registerSimpleBlockItem("present_box", ModBlocks.PRESENT_BOX);

    public static final DeferredItem<RebarItem> REBAR = ITEMS.registerItem("rebar", RebarItem::new);
    public static final DeferredItem<LuckyEggItem> LUCKY_EGG = ITEMS.registerItem("lucky_egg", LuckyEggItem::new);
    public static final DeferredItem<UnbornBabyEyeItem> UNBORN_BABY_EYE = ITEMS.registerItem("unborn_baby_eye", UnbornBabyEyeItem::new);
    public static final DeferredItem<BloodyMaidenHeartItem> BLOODY_MAIDEN_HEART = ITEMS.registerItem(
            "bloody_maiden_heart", properties -> new BloodyMaidenHeartItem(properties.durability(11)));
    public static final DeferredItem<FallenLeaderSpineItem> FALLEN_LEADER_SPINE = ITEMS.registerItem(
            "fallen_leader_spine", properties -> new FallenLeaderSpineItem(properties.stacksTo(1)));
    public static final DeferredItem<EntrailFleshItem> ENTRAIL_FLESH = ITEMS.registerItem("entrail_flesh", EntrailFleshItem::new);
    public static final DeferredItem<PoopooPillItem> POOPOO_PILL = ITEMS.registerItem("poopoo_pill", PoopooPillItem::new);
    public static final DeferredItem<DagonItem> DAGON = ITEMS.registerItem("dagon", DagonItem::new);
    public static final DeferredItem<Item> FLYING_DAGON = simple("flying_dagon");
    public static final DeferredItem<SunlightDropItem> SUNLIGHT_DROP = ITEMS.registerItem("sunlight_drop", SunlightDropItem::new);
    public static final DeferredItem<Item> MOD_ITEM = simple("mod_item");

    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_UNBORN_BABY = spawnEgg("mob_spawner_item_unborn_baby", ModEntities.UNBORN_BABY);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_CLIMBER = spawnEgg("mob_spawner_item_climber", ModEntities.CLIMBER);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_ENTRAIL = spawnEgg("mob_spawner_item_entrail", ModEntities.ENTRAIL);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_FREEZER = spawnEgg("mob_spawner_item_freezer", ModEntities.FREEZER);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_MUTANT_STEVE = spawnEgg("mob_spawner_item_mutant_steve", ModEntities.MUTANT_STEVE);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_FALLEN_LEADER = spawnEgg("mob_spawner_item_fallen_leader", ModEntities.FALLEN_LEADER);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_BLOODY_MAIDEN = spawnEgg("mob_spawner_item_bloody_maiden", ModEntities.BLOODY_MAIDEN);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_ZOMBIE_CHICKEN = spawnEgg("mob_spawner_item_zombie_chicken", ModEntities.ZOMBIE_CHICKEN);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_PRESENT = spawnEgg("mob_spawner_item_present", ModEntities.PRESENT);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_STRANGER = spawnEgg("mob_spawner_item_stranger", ModEntities.STRANGER);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_HAUNTED_COW = spawnEgg("mob_spawner_item_haunted_cow", ModEntities.HAUNTED_COW);
    public static final DeferredItem<SpawnEggItem> MOB_SPAWNER_ITEM_TOPIELEC = spawnEgg("mob_spawner_item_topielec", ModEntities.TOPIELEC);

    public static final DeferredItem<HarpoonItem> HARPOON_STONE = ITEMS.registerItem(
            "harpoon_stone", properties -> new HarpoonItem(properties.durability(10), 3.0F));
    public static final DeferredItem<HarpoonItem> HARPOON_IRON = ITEMS.registerItem(
            "harpoon_iron", properties -> new HarpoonItem(properties.durability(40), 6.0F));
    public static final DeferredItem<HarpoonItem> HARPOON_DIAMOND = ITEMS.registerItem(
            "harpoon_diamond", properties -> new HarpoonItem(properties.durability(160), 10.0F));
    public static final DeferredItem<HarpoonItem> HARPOON_OBSIDIAN = ITEMS.registerItem(
            "harpoon_obsidian", properties -> new HarpoonItem(properties.durability(80), 6.0F));

    private ModItems() {}

    private static DeferredItem<Item> simple(String name) {
        return ITEMS.registerSimpleItem(name, properties -> properties);
    }

    private static <T extends net.minecraft.world.entity.Mob> DeferredItem<SpawnEggItem> spawnEgg(
            String name,
            net.neoforged.neoforge.registries.DeferredHolder<net.minecraft.world.entity.EntityType<?>, net.minecraft.world.entity.EntityType<T>> type) {
        return ITEMS.registerItem(name, properties -> new SpawnEggItem(properties.spawnEgg(type.get())));
    }
}
