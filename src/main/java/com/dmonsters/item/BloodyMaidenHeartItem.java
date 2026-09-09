package com.dmonsters.item;

import com.dmonsters.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public final class BloodyMaidenHeartItem extends Item {
    public BloodyMaidenHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) {
            return InteractionResult.FAIL;
        }
        var pos = context.getClickedPos();
        if (!context.getLevel().mayInteract(context.getPlayer(), pos)) {
            return InteractionResult.FAIL;
        }
        if (!(context.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }

        level.setBlockAndUpdate(
                pos,
                context.getPlayer().isShiftKeyDown()
                        ? Blocks.WATER.defaultBlockState()
                        : Blocks.LAVA.defaultBlockState());
        level.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                ModSounds.MAIDEN_ATTACK.get(), SoundSource.BLOCKS, 0.25F, 1.0F);
        level.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                12, 0.35D, 0.35D, 0.35D, 0.02D);
        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand().asEquipmentSlot());
        return InteractionResult.SUCCESS;
    }
}
