package com.dmonsters.entity;

import com.dmonsters.block.PresentBlock;
import com.dmonsters.registry.ModBlocks;
import com.dmonsters.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class PresentEntity extends Monster {
    private int cageCooldown;

    public PresentEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public void aiStep() {
        if (this.cageCooldown > 0) {
            this.cageCooldown--;
        }
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        this.playSound(ModSounds.PRESENT_ATTACK.get(), 1.0F, 1.0F);
        if (target instanceof ServerPlayer player && this.cageCooldown == 0) {
            this.makeCage(level, player);
            this.cageCooldown = 400;
        }
        return true;
    }

    private void makeCage(ServerLevel level, ServerPlayer player) {
        BlockPos origin = this.blockPosition();
        int radius = 3;
        int baseY = origin.getY() + 3;
        int cageHeight = 7;
        BlockState green = ModBlocks.PRESENT_BLOCK.get().defaultBlockState()
                .setValue(PresentBlock.COLOR, PresentBlock.Color.GREEN);
        BlockState yellow = green.setValue(PresentBlock.COLOR, PresentBlock.Color.YELLOW);

        for (int y = 0; y < cageHeight; y++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    boolean wall = Math.abs(dx) == radius || Math.abs(dz) == radius;
                    boolean floorOrCeiling = y == 0 || y == cageHeight - 1;
                    if (!wall && !floorOrCeiling) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(origin.getX() + dx, baseY + y, origin.getZ() + dz);
                    if (level.getBlockState(pos).isAir()) {
                        level.setBlockAndUpdate(pos, dx == 0 || dz == 0 ? yellow : green);
                    }
                }
            }
        }

        BlockPos lightPos = new BlockPos(origin.getX(), baseY + 1, origin.getZ());
        if (level.getBlockState(lightPos).isAir()) {
            level.setBlockAndUpdate(lightPos, Blocks.TORCH.defaultBlockState());
        }

        EntityType<?> creeperType = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.withDefaultNamespace("creeper"));
        if (creeperType != null) {
            for (int i = 0; i < 2; i++) {
                Entity spawned = creeperType.create(level, EntitySpawnReason.TRIGGERED);
                if (spawned instanceof Creeper creeper) {
                    creeper.snapTo(lightPos.getX() + 0.5D, lightPos.getY(), lightPos.getZ() + 0.5D, 0.0F, 0.0F);
                    level.addFreshEntity(creeper);
                }
            }
        }
        player.teleportTo(origin.getX() + 0.5D, baseY + 1.0D, origin.getZ() + 0.5D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PRESENT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.PRESENT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PRESENT_DEATH.get();
    }
}
