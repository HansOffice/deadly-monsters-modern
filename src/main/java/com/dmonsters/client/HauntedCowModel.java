package com.dmonsters.client;

import com.dmonsters.DeadlyMonsters;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class HauntedCowModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "haunted_cow"), "main");

    private final ModelPart head;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public HauntedCowModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leg1 = root.getChild("leg_1");
        this.leg2 = root.getChild("leg_2");
        this.leg3 = root.getChild("leg_3");
        this.leg4 = root.getChild("leg_4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F), PartPose.offset(0.0F, 7.0F, -8.0F));
        head.addOrReplaceChild("horn_1", CubeListBuilder.create().texOffs(31, 0)
                .addBox(-3.0F, -10.0F, 4.0F, 1.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 3.0F, -7.0F));
        head.addOrReplaceChild("horn_2", CubeListBuilder.create().texOffs(31, 0)
                .addBox(2.0F, -10.0F, 4.0F, 1.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 3.0F, -7.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14)
                .addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.570796F, 0.0F, 0.0F));
        root.addOrReplaceChild("leg_1", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-3.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("leg_2", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(3.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("leg_3", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-3.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-3.0F, 12.0F, -5.0F));
        root.addOrReplaceChild("leg_4", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-1.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(3.0F, 12.0F, -5.0F));
        root.addOrReplaceChild("udders", CubeListBuilder.create().texOffs(52, 0)
                .addBox(-2.0F, -3.0F, 0.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 14.0F, 6.0F));
        root.addOrReplaceChild("body_inside", CubeListBuilder.create().texOffs(18, 42)
                .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 14.0F), PartPose.offset(0.0F, 8.0F, -6.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        this.head.zRot = state.xRot * 0.002F;
        this.leg1.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leg2.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.leg3.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.leg4.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
    }
}
