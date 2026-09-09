package com.dmonsters.registry;

import com.dmonsters.DeadlyMonsters;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DeadlyMonsters.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> DUMP_MAKE = register("block.dump.make");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_SOULEYE_KILL = register("block.souleye.kill");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNLIGHTDROP_USE = register("item.sunlightdrop.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTEDCOW_TIMECHANGE = register("mob.hauntedcow.timechange");

    public static final DeferredHolder<SoundEvent, SoundEvent> TOPIELEC_HURT = register("mob.topielec.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOPIELEC_AMBIENT = register("mob.topielec.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOPIELEC_DEATH = register("mob.topielec.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTEDCOW_DEATH = register("mob.hauntedcow.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTEDCOW_HURT = register("mob.hauntedcow.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTEDCOW_AMBIENT = register("mob.hauntedcow.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTEDCOW_STEP = register("mob.hauntedcow.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> STRANGER_IMPACT = register("mob.stranger.impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> STRANGER_DEATH = register("mob.stranger.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> STRANGER_HURT = register("mob.stranger.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> STRANGER_AMBIENT = register("mob.stranger.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> STRANGER_ATTACK = register("mob.stranger.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUTANT_DEATH = register("mob.mutant.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUTANT_HURT = register("mob.mutant.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUTANT_AMBIENT = register("mob.mutant.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUTANT_ATTACK = register("mob.mutant.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> FREEZER_DEATH = register("mob.freezer.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> FREEZER_HURT = register("mob.freezer.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> FREEZER_AMBIENT = register("mob.freezer.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> FREEZER_ATTACK = register("mob.freezer.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> CLIMBER_DEATH = register("mob.climber.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CLIMBER_HURT = register("mob.climber.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CLIMBER_AMBIENT = register("mob.climber.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> CLIMBER_ATTACK = register("mob.climber.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> BABY_DEATH = register("mob.baby.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BABY_HURT = register("mob.baby.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BABY_AMBIENT = register("mob.baby.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BABY_ATTACK = register("mob.baby.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> WIDEMAN_DEATH = register("mob.wideman.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WIDEMAN_HURT = register("mob.wideman.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WIDEMAN_AMBIENT = register("mob.wideman.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> WIDEMAN_ATTACK = register("mob.wideman.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> MAIDEN_DEATH = register("mob.maiden.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAIDEN_HURT = register("mob.maiden.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAIDEN_AMBIENT = register("mob.maiden.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAIDEN_ATTACK = register("mob.maiden.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTRAIL_DEATH = register("mob.entrail.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTRAIL_HURT = register("mob.entrail.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTRAIL_AMBIENT = register("mob.entrail.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTRAIL_ATTACK = register("mob.entrail.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> PRESENT_DEATH = register("mob.present.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRESENT_HURT = register("mob.present.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRESENT_AMBIENT = register("mob.present.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRESENT_ATTACK = register("mob.present.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> ZOMBIE_CHICKEN_DEATH = register("mob.zombie_chicken.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ZOMBIE_CHICKEN_HURT = register("mob.zombie_chicken.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ZOMBIE_CHICKEN_AMBIENT = register("mob.zombie_chicken.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ZOMBIE_CHICKEN_ATTACK = register("mob.zombie_chicken.attack");

    private ModSounds() {
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
