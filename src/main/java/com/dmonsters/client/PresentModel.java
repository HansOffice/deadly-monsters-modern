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

public final class PresentModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "present"), "main");
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public PresentModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, -10.0F, -5.0F, 12.0F, 12.0F, 12.0F), PartPose.offset(-1.0F, 13.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 25)
                .addBox(-4.5F, -15.0F, -16.0F, 13.0F, 2.0F, 13.0F), PartPose.offsetAndRotation(-1.0F, 4.0F, 5.0F, -0.4363323F, 0.0F, 0.0F));
        head.addOrReplaceChild("inside_hat", CubeListBuilder.create().texOffs(0, 41)
                .addBox(-4.0F, -16.5F, -13.0F, 10.0F, 5.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, -0.3490659F, 0.0F, 0.0F));
        head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(49, 0)
                .addBox(0.0F, -14.0F, -6.0F, 2.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-3.0F, 2.0F, -3.0F, -0.3490659F, 0.0F, 0.0F));
        head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(49, 5)
                .addBox(0.0F, -14.0F, -6.0F, 2.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(3.0F, 2.0F, -3.0F, -0.3490659F, 0.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 56)
                .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(41, 41)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(-2.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(50, 41)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(2.0F, 17.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);
    }
}
