package com.dmonsters.client;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.entity.ZombieChickenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public final class ZombieChickenRenderer extends MobRenderer<ZombieChickenEntity, ZombieChickenRenderState, ZombieChickenModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            DeadlyMonsters.MOD_ID, "textures/entity/zombie_chicken.png");

    public ZombieChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieChickenModel(context.bakeLayer(ZombieChickenModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public Identifier getTextureLocation(ZombieChickenRenderState state) {
        return TEXTURE;
    }

    @Override
    public ZombieChickenRenderState createRenderState() {
        return new ZombieChickenRenderState();
    }
}
