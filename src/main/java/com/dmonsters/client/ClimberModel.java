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

public final class ClimberModel extends EntityModel<ClimberRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "climber"), "main");

    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public ClimberModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -6.0F, 6.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, -1.0F, -3.0F));

        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(13, 17).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 0.0872665F, 0.0F, 0.0F));
        body.addOrReplaceChild(
                "hips",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        body.addOrReplaceChild(
                "upper_body",
                CubeListBuilder.create().texOffs(29, 0).addBox(-4.0F, -5.0F, -1.5F, 8.0F, 8.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.2094395F, 0.0F, 0.0F));

        root.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 16.0F, 2.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(31, 16).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 16.0F, 2.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(0, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(-2.0F, 11.0F, 1.0F, -0.1745329F, 0.0F, 0.0F));
        rightLeg.addOrReplaceChild(
                "lower_right_leg",
                CubeListBuilder.create().texOffs(11, 46).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 6.0F, 1.0F, 0.5235988F, 0.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(2.0F, 11.0F, 1.0F, -0.1745329F, 0.0F, 0.0F));
        leftLeg.addOrReplaceChild(
                "lower_left_leg",
                CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 6.0F, 1.0F, 0.1745329F, 0.0F, 0.0F));

        root.addOrReplaceChild(
                "spike_1",
                CubeListBuilder.create().texOffs(30, 36).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(1.0F, -2.0F, 3.0F, 0.2094395F, 0.0F, 0.0F));
        root.addOrReplaceChild(
                "spike_2",
                CubeListBuilder.create().texOffs(30, 42).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-2.0F, -2.0F, 3.0F, 0.2094395F, 0.0F, 0.0F));
        root.addOrReplaceChild(
                "spike_3",
                CubeListBuilder.create().texOffs(30, 48).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(1.0F, 1.0F, 3.0F, 0.2094395F, 0.0F, 0.0F));
        root.addOrReplaceChild(
                "spike_4",
                CubeListBuilder.create().texOffs(30, 54).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(-2.0F, 2.0F, 3.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(ClimberRenderState state) {
        super.setupAnim(state);

        this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;

        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);

        float attack2 = Mth.sin(state.attackTime * (float) Math.PI);
        float attack = Mth.sin((1.0F - (1.0F - state.attackTime) * (1.0F - state.attackTime)) * (float) Math.PI);
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.yRot = -(0.1F - attack2 * 0.6F);
        this.leftArm.yRot = 0.1F - attack2 * 0.6F;

        float baseArmAngle = -(float) Math.PI / 2.25F;
        this.rightArm.xRot = baseArmAngle + attack2 * 1.2F - attack * 0.4F;
        this.leftArm.xRot = baseArmAngle + attack2 * 1.2F - attack * 0.4F;
        this.rightArm.zRot += Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.zRot -= Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.xRot += Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
        this.leftArm.xRot -= Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
    }
}
