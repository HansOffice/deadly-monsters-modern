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

public final class FreezerModel extends EntityModel<FreezerRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "freezer"), "main");

    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart hips;

    public FreezerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.hips = root.getChild("hips");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        head.addOrReplaceChild(
                "upper_head",
                CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F),
                PartPose.offset(-3.0F, -13.0F, -3.0F));

        root.addOrReplaceChild(
                "neck",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(2.0F, 1.0F, 0.0F));
        root.addOrReplaceChild(
                "torso",
                CubeListBuilder.create().texOffs(33, 7).addBox(0.0F, 0.0F, 0.0F, 4.0F, 5.0F, 3.0F),
                PartPose.offset(-2.0F, 6.0F, -2.0F));
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(33, 0).addBox(0.0F, 0.0F, 0.0F, 8.0F, 4.0F, 3.0F),
                PartPose.offset(-4.0F, 2.0F, -2.0F));
        root.addOrReplaceChild(
                "hips",
                CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));

        root.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(51, 16).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 3.0F),
                PartPose.offset(-4.0F, 5.0F, 0.0F));
        root.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(38, 16).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 3.0F),
                PartPose.offset(5.0F, 5.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(FreezerRenderState state) {
        super.setupAnim(state);

        if (state.aggressive) {
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);

            float attack2 = Mth.sin(state.attackTime * (float) Math.PI);
            float attack = Mth.sin((1.0F - (1.0F - state.attackTime) * (1.0F - state.attackTime)) * (float) Math.PI);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightArm.yRot = -(0.1F - attack2 * 0.6F);
            this.leftArm.yRot = 0.1F - attack2 * 0.6F;

            float raisedAngle = -(float) Math.PI / 2.25F;
            this.rightArm.xRot = raisedAngle + attack2 * 1.2F - attack * 0.4F;
            this.leftArm.xRot = raisedAngle + attack2 * 1.2F - attack * 0.4F;
            this.rightArm.zRot += Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.leftArm.zRot -= Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.rightArm.xRot += Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
            this.leftArm.xRot -= Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
            this.hips.yRot = state.ageInTicks * 0.04F;
        } else {
            this.head.yRot = 0.0F;
            this.head.xRot = 0.0F;
            this.rightArm.xRot = 0.0F;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = 0.0F;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.hips.yRot = state.ageInTicks * -0.005F;
        }
    }
}
