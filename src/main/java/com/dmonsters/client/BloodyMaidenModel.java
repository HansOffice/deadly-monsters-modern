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
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class BloodyMaidenModel extends EntityModel<BloodyMaidenRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "bloody_maiden"), "main");

    private final ModelPart head;
    private final ModelPart upperLeftLeg;
    private final ModelPart upperRightLeg;
    private final ModelPart upperLeftArm;
    private final ModelPart upperRightArm;

    public BloodyMaidenModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.upperLeftLeg = root.getChild("upper_left_leg");
        this.upperRightLeg = root.getChild("upper_right_leg");
        this.upperLeftArm = root.getChild("upper_left_arm");
        this.upperRightArm = root.getChild("upper_right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-3.466667F, 0.0F, -7.0F, 7.0F, 7.0F, 7.0F), PartPose.offset(0.0F, 14.0F, -7.0F));
        root.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 15)
                .addBox(0.0F, 0.0F, 0.0F, 8.0F, 4.0F, 7.0F), PartPose.offsetAndRotation(-4.0F, 13.0F, -7.0F, 0.1115358F, 0.0F, 0.0F));
        root.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 27)
                .addBox(0.0F, 0.0F, 0.0F, 6.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(-3.0F, 13.0F, 0.0F, -0.1115358F, 0.0F, 0.0F));
        root.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(0, 35)
                .addBox(0.0F, 0.0F, 0.0F, 8.0F, 4.0F, 5.0F), PartPose.offsetAndRotation(-4.0F, 13.0F, 4.0F, -0.2230717F, 0.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("upper_left_leg", CubeListBuilder.create().texOffs(39, 14)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F), PartPose.offsetAndRotation(3.0F, 16.0F, 8.0F, 0.3490659F, 0.7853982F, 0.0F));
        leftLeg.addOrReplaceChild("lower_left_leg", CubeListBuilder.create().texOffs(38, 27)
                .addBox(0.0F, 0.0F, 0.0F, 3.0F, 8.0F, 3.0F), PartPose.offset(-2.0F, 0.0F, 7.0F));
        PartDefinition rightLeg = root.addOrReplaceChild("upper_right_leg", CubeListBuilder.create().texOffs(39, 1)
                .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F), PartPose.offsetAndRotation(-3.0F, 16.0F, 8.0F, 0.3490659F, -0.7853982F, 0.0F));
        rightLeg.addOrReplaceChild("lower_right_leg", CubeListBuilder.create().texOffs(51, 27)
                .addBox(0.0F, 0.0F, 0.0F, 3.0F, 8.0F, 3.0F), PartPose.offset(-1.0F, 0.0F, 7.0F));

        PartDefinition rightArm = root.addOrReplaceChild("upper_right_arm", CubeListBuilder.create().texOffs(0, 45)
                .addBox(-3.0F, -2.0F, -7.0F, 3.0F, 3.0F, 7.0F), PartPose.offsetAndRotation(-3.0F, 15.0F, -6.0F, -0.3490659F, 0.7853982F, 0.0F));
        rightArm.addOrReplaceChild("lower_right_arm", CubeListBuilder.create().texOffs(51, 45)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F), PartPose.offset(-2.0F, 0.0F, -8.0F));
        PartDefinition leftArm = root.addOrReplaceChild("upper_left_arm", CubeListBuilder.create().texOffs(21, 45)
                .addBox(0.0F, -2.0F, -7.0F, 3.0F, 3.0F, 7.0F), PartPose.offsetAndRotation(3.0F, 15.0F, -6.0F, -0.3490659F, -0.7853982F, 0.0F));
        leftArm.addOrReplaceChild("lower_left_arm", CubeListBuilder.create().texOffs(42, 45)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F), PartPose.offset(0.0F, 0.0F, -8.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(BloodyMaidenRenderState state) {
        super.setupAnim(state);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.upperLeftArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.upperRightArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.upperLeftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.upperRightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
    }
}
