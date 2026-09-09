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

public final class TopielecModel extends EntityModel<TopielecRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "topielec"), "main");
    private final ModelPart head;
    private final ModelPart leftUpperLeg;
    private final ModelPart rightUpperLeg;
    private final ModelPart leftUpperArm;
    private final ModelPart rightUpperArm;

    public TopielecModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leftUpperLeg = root.getChild("left_upper_leg");
        this.rightUpperLeg = root.getChild("right_upper_leg");
        this.leftUpperArm = root.getChild("left_upper_arm");
        this.rightUpperArm = root.getChild("right_upper_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -5.0F, 6.0F, 8.0F, 6.0F), PartPose.offset(1.0F, 5.0F, 6.0F));
        head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(41, 48)
                .addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, -6.0F, -6.0F));
        head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(41, 51)
                .addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-3.0F, -6.0F, -6.0F));
        head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(46, 48)
                .addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F), PartPose.offset(-2.5F, -3.0F, -6.0F));
        root.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 15)
                .addBox(-5.0F, 0.0F, 0.0F, 10.0F, 8.0F, 9.0F), PartPose.offset(0.0F, 8.0F, -1.0F));
        root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(27, 0)
                .addBox(-2.0F, 0.0F, -1.0F, 8.0F, 3.0F, 7.0F), PartPose.offset(-2.0F, 5.0F, 1.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("left_upper_leg", CubeListBuilder.create().texOffs(0, 33)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 4.0F), PartPose.offset(2.0F, 16.0F, 4.0F));
        leftLeg.addOrReplaceChild("left_lower_leg", CubeListBuilder.create().texOffs(0, 42)
                .addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F), PartPose.offset(0.0F, 4.0F, 0.0F));
        leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 48)
                .addBox(-2.0F, 0.0F, -1.0F, 3.0F, 1.0F, 6.0F), PartPose.offset(2.0F, 7.0F, -3.0F));
        PartDefinition rightLeg = root.addOrReplaceChild("right_upper_leg", CubeListBuilder.create().texOffs(14, 33)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 4.0F), PartPose.offset(-4.0F, 16.0F, 3.0F));
        rightLeg.addOrReplaceChild("right_lower_leg", CubeListBuilder.create().texOffs(7, 42)
                .addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F), PartPose.offset(1.0F, 4.0F, 0.0F));
        rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(21, 48)
                .addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 6.0F), PartPose.offset(1.0F, 7.0F, -3.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_upper_arm", CubeListBuilder.create().texOffs(39, 15)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 3.0F), PartPose.offset(5.0F, 9.0F, 4.0F));
        leftArm.addOrReplaceChild("left_lower_front", CubeListBuilder.create().texOffs(39, 27)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 1.0F, 0.5235988F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("left_lower_back", CubeListBuilder.create().texOffs(47, 27)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 7.5F, -1.0F, -0.5235988F, 0.0F, 0.0F));
        PartDefinition rightArm = root.addOrReplaceChild("right_upper_arm", CubeListBuilder.create().texOffs(50, 15)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 3.0F), PartPose.offset(-5.0F, 9.0F, 4.0F));
        rightArm.addOrReplaceChild("right_lower_back", CubeListBuilder.create().texOffs(39, 35)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, 8.0F, 1.0F, 0.5235988F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("right_lower_front", CubeListBuilder.create().texOffs(47, 35)
                .addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, 7.5F, -1.0F, -0.5235988F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(TopielecRenderState state) {
        super.setupAnim(state);
        this.rightUpperLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leftUpperLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);
        float f = Mth.sin(state.attackTime * (float) Math.PI);
        float f1 = Mth.sin((1.0F - (1.0F - state.attackTime) * (1.0F - state.attackTime)) * (float) Math.PI);
        this.rightUpperArm.zRot = 0.0F;
        this.leftUpperArm.zRot = 0.0F;
        this.rightUpperArm.yRot = -(0.1F - f * 0.6F);
        this.leftUpperArm.yRot = 0.1F - f * 0.6F;
        float base = -(float) Math.PI / 2.25F;
        this.rightUpperArm.xRot = base + f * 1.2F - f1 * 0.4F;
        this.leftUpperArm.xRot = base + f * 1.2F - f1 * 0.4F;
        this.rightUpperArm.zRot += Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftUpperArm.zRot -= Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightUpperArm.xRot += Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
        this.leftUpperArm.xRot -= Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
    }
}
