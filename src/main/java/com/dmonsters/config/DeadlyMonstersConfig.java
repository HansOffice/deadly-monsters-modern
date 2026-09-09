package com.dmonsters.config;

import java.util.List;

import com.dmonsters.entity.HauntedCowEntity;
import com.dmonsters.entity.TopielecEntity;
import com.dmonsters.item.HarpoonItem;
import com.dmonsters.registry.ModEntities;
import com.dmonsters.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.apache.commons.lang3.tuple.Pair;

public final class DeadlyMonstersConfig {
    public static final Settings VALUES;
    public static final ModConfigSpec SPEC;

    static {
        Pair<Settings, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Settings::new);
        VALUES = pair.getLeft();
        SPEC = pair.getRight();
    }

    private DeadlyMonstersConfig() {}

    public static boolean naturalSpawnsEnabled(EntityType<?> type) {
        MonsterSettings settings = settingsFor(type);
        return settings == null || !settings.disabled.get();
    }

    public static int spawnRate(EntityType<?> type) {
        MonsterSettings settings = settingsFor(type);
        return settings == null ? 0 : settings.spawnRate.get();
    }

    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        MonsterScale scale = scaleFor(living.getType());
        if (scale == null) {
            return;
        }

        float previousHealth = living.getHealth();
        float previousMaxHealth = living.getMaxHealth();
        boolean previouslyFull = Math.abs(previousHealth - previousMaxHealth) < 0.001F;

        double speed = scale.baseSpeed * VALUES.globalSpeedMultiplier.get() * scale.settings.speedMultiplier.get();
        double strength = scale.baseStrength * VALUES.globalStrengthMultiplier.get() * scale.settings.strengthMultiplier.get();
        double health = scale.baseHealth * VALUES.globalHealthMultiplier.get() * scale.settings.healthMultiplier.get();

        setBaseValue(living, Attributes.MOVEMENT_SPEED, speed);
        setBaseValue(living, Attributes.ATTACK_DAMAGE, strength);
        setBaseValue(living, Attributes.MAX_HEALTH, health);

        if (!event.loadedFromDisk() && previouslyFull) {
            living.setHealth(living.getMaxHealth());
        } else {
            living.setHealth(Math.min(previousHealth, living.getMaxHealth()));
        }
    }

    private static void setBaseValue(LivingEntity living, Holder<Attribute> attribute, double value) {
        AttributeInstance instance = living.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }

    public static void onPlayerAttack(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        if (event.getTarget() instanceof TopielecEntity
                && !VALUES.topielec.disabled.get()
                && VALUES.topielecHarpoonOnly.get()) {
            if (!(event.getEntity().getMainHandItem().getItem() instanceof HarpoonItem)) {
                event.setCanceled(true);
                return;
            }
        }

        if (!(event.getTarget() instanceof HauntedCowEntity)
                || VALUES.hauntedCow.disabled.get()
                || VALUES.hauntedCowDisableTimeChange.get()) {
            return;
        }

        ItemStack held = event.getEntity().getMainHandItem();
        if (held.is(ItemTags.SWORDS) || held.is(Items.BOW) || isConfiguredHauntedCowWeapon(held.getItem())) {
            return;
        }

        if (!(event.getEntity().level() instanceof ServerLevel playerLevel)) {
            return;
        }
        ServerLevel overworld = playerLevel.getServer().overworld();
        long time = overworld.getOverworldClockTime() % 24000L;
        if (time >= 13000L) {
            return;
        }

        if (overworld.getGameRules().get(GameRules.ADVANCE_TIME)) {
            Registry<WorldClock> clocks = overworld.registryAccess().lookupOrThrow(Registries.WORLD_CLOCK);
            Holder<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);
            overworld.clockManager().moveToTimeMarker(overworldClock, ClockTimeMarkers.NIGHT);
        }
        event.getEntity().playSound(ModSounds.HAUNTEDCOW_TIMECHANGE.get(), 1.0F, 1.0F);
        event.getEntity().sendSystemMessage(
                Component.translatable("msg.dmonsters.haunted_cow").withStyle(ChatFormatting.DARK_RED));
    }

    private static boolean isConfiguredHauntedCowWeapon(Item item) {
        String id = BuiltInRegistries.ITEM.getKey(item).toString();
        return VALUES.hauntedCowValidWeapons.get().contains(id);
    }

    private static MonsterSettings settingsFor(EntityType<?> type) {
        MonsterScale scale = scaleFor(type);
        return scale == null ? null : scale.settings;
    }

    private static MonsterScale scaleFor(EntityType<?> type) {
        if (type == ModEntities.MUTANT_STEVE.get()) return new MonsterScale(0.13D, 16.0D, 40.0D, VALUES.mutantSteve);
        if (type == ModEntities.FREEZER.get()) return new MonsterScale(0.13D, 16.0D, 45.0D, VALUES.freezer);
        if (type == ModEntities.CLIMBER.get()) return new MonsterScale(0.10D, 12.0D, 24.0D, VALUES.climber);
        if (type == ModEntities.ENTRAIL.get()) return new MonsterScale(0.30D, 10.0D, 30.0D, VALUES.entrail);
        if (type == ModEntities.UNBORN_BABY.get()) return new MonsterScale(0.25D, 12.0D, 40.0D, VALUES.unbornBaby);
        if (type == ModEntities.FALLEN_LEADER.get()) return new MonsterScale(0.10D, 20.0D, 60.0D, VALUES.fallenLeader);
        if (type == ModEntities.BLOODY_MAIDEN.get()) return new MonsterScale(0.20D, 4.0D, 20.0D, VALUES.bloodyMaiden);
        if (type == ModEntities.ZOMBIE_CHICKEN.get()) return new MonsterScale(0.26D, 8.0D, 16.0D, VALUES.zombieChicken);
        if (type == ModEntities.PRESENT.get()) return new MonsterScale(0.30D, 1.0D, 26.0D, VALUES.present);
        if (type == ModEntities.STRANGER.get()) return new MonsterScale(0.15D, 4.0D, 40.0D, VALUES.stranger);
        if (type == ModEntities.HAUNTED_COW.get()) return new MonsterScale(0.30D, 12.0D, 24.0D, VALUES.hauntedCow);
        if (type == ModEntities.TOPIELEC.get()) return new MonsterScale(0.40D, 1.0D, 20.0D, VALUES.topielec);
        return null;
    }

    private record MonsterScale(double baseSpeed, double baseStrength, double baseHealth, MonsterSettings settings) {}

    public static final class Settings {
        public final ModConfigSpec.DoubleValue globalHealthMultiplier;
        public final ModConfigSpec.DoubleValue globalStrengthMultiplier;
        public final ModConfigSpec.DoubleValue globalSpeedMultiplier;

        public final MonsterSettings mutantSteve;
        public final MonsterSettings freezer;
        public final MonsterSettings climber;
        public final MonsterSettings entrail;
        public final MonsterSettings unbornBaby;
        public final MonsterSettings fallenLeader;
        public final MonsterSettings bloodyMaiden;
        public final MonsterSettings zombieChicken;
        public final MonsterSettings present;
        public final MonsterSettings stranger;
        public final MonsterSettings hauntedCow;
        public final MonsterSettings topielec;

        public final ModConfigSpec.BooleanValue mutantSteveBreakBlocks;
        public final ModConfigSpec.BooleanValue babyBlindness;
        public final ModConfigSpec.IntValue topielecSearchDistance;
        public final ModConfigSpec.BooleanValue topielecHarpoonOnly;
        public final ModConfigSpec.ConfigValue<List<? extends String>> hauntedCowValidWeapons;
        public final ModConfigSpec.BooleanValue hauntedCowDisableTimeChange;

        private Settings(ModConfigSpec.Builder builder) {
            builder.push("general");
            globalHealthMultiplier = builder.comment("全模组怪物生命全局倍率 (取值范围 0.01 ~ 100.0，默认 1.0)")
                    .defineInRange("globalHealthMultiplier", 1.0D, 0.01D, 100.0D);
            globalStrengthMultiplier = builder.comment("全模组怪物攻击伤害全局倍率 (取值范围 0.01 ~ 100.0，默认 1.0)")
                    .defineInRange("globalStrengthMultiplier", 1.0D, 0.01D, 100.0D);
            globalSpeedMultiplier = builder.comment("全模组怪物移动速度全局倍率 (取值范围 0.01 ~ 10.0，默认 1.0)")
                    .defineInRange("globalSpeedMultiplier", 1.0D, 0.01D, 10.0D);
            builder.pop();

            mutantSteve = new MonsterSettings(builder, "mutant_steve", 8);
            freezer = new MonsterSettings(builder, "freezer", 8);
            climber = new MonsterSettings(builder, "climber", 8);
            entrail = new MonsterSettings(builder, "entrail", 12);
            unbornBaby = new MonsterSettings(builder, "unborn_baby", 12);
            fallenLeader = new MonsterSettings(builder, "fallen_leader", 12);
            bloodyMaiden = new MonsterSettings(builder, "bloody_maiden", 12);
            zombieChicken = new MonsterSettings(builder, "zombie_chicken", 12);
            present = new MonsterSettings(builder, "present", 12);
            stranger = new MonsterSettings(builder, "stranger", 12);
            hauntedCow = new MonsterSettings(builder, "haunted_cow", 8);
            topielec = new MonsterSettings(builder, "topielec", 8);

            builder.push("mutant_steve");
            mutantSteveBreakBlocks = builder.comment("突变史蒂夫特殊攻击是否可破坏周围方块")
                    .define("breakBlocks", true);
            builder.pop();

            builder.push("unborn_baby");
            babyBlindness = builder.comment("腹中胎儿锁定玩家时是否附加周期性失明光环")
                    .define("blindness", false);
            builder.pop();

            builder.push("topielec");
            topielecSearchDistance = builder.comment("异形水鬼寻找深水域的索敌采样半径 (单位：方块，范围 1 ~ 128)")
                    .defineInRange("searchDistance", 16, 1, 128);
            topielecHarpoonOnly = builder.comment("是否限制异形水鬼仅能被鱼叉造成直接攻击伤害")
                    .define("harpoonOnly", false);
            builder.pop();

            builder.push("haunted_cow");
            hauntedCowValidWeapons = builder.comment("攻击闹鬼牛不受世界转夜惩罚的有效武器物品 ID 列表")
                    .defineList(
                            "validWeapons",
                            List.of("thaumicaugmentation:morphic_tool"),
                            value -> value instanceof String);
            hauntedCowDisableTimeChange = builder.comment("是否关闭闹鬼牛受到无效武器攻击时强制将世界时间切至夜晚的惩罚机制")
                    .define("disableTimeChange", false);
            builder.pop();
        }
    }

    public static final class MonsterSettings {
        public final ModConfigSpec.DoubleValue healthMultiplier;
        public final ModConfigSpec.DoubleValue strengthMultiplier;
        public final ModConfigSpec.DoubleValue speedMultiplier;
        public final ModConfigSpec.IntValue spawnRate;
        public final ModConfigSpec.BooleanValue disabled;

        private MonsterSettings(ModConfigSpec.Builder builder, String name, int defaultSpawnRate) {
            builder.push(name);
            healthMultiplier = builder.comment("独立生命倍率，最终生命 = 基础生命 * 全局倍率 * 独立倍率")
                    .defineInRange("healthMultiplier", 1.0D, 0.01D, 100.0D);
            strengthMultiplier = builder.comment("独立攻击倍率，最终伤害 = 基础伤害 * 全局倍率 * 独立倍率")
                    .defineInRange("strengthMultiplier", 1.0D, 0.01D, 100.0D);
            speedMultiplier = builder.comment("独立移速倍率，最终移速 = 基础移速 * 全局倍率 * 独立倍率")
                    .defineInRange("speedMultiplier", 1.0D, 0.01D, 10.0D);
            spawnRate = builder.comment("自然生成权重，数值越大生成越频繁，修改后需重进世界或重启服务端生效")
                    .defineInRange("spawnRate", defaultSpawnRate, 0, 1000);
            disabled = builder.comment("是否彻底禁用该怪物的自然生成，设为 true 时该怪物将不会在世界中自然生成")
                    .define("disabled", false);
            builder.pop();
        }
    }
}
