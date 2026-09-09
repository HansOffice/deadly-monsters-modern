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

public final class StrangerModel extends EntityModel<StrangerRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "stranger"), "main");

    private final ModelPart leftHead;
    private final ModelPart rightHead;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public StrangerModel(ModelPart root) {
        super(root);
        this.leftHead = root.getChild("left_head");
        this.rightHead = root.getChild("right_head");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 14.0F), PartPose.offset(-7.0F, 1.0F, -7.0F));
        root.addOrReplaceChild("stomach", CubeListBuilder.create().texOffs(0, 31)
                .addBox(0.0F, 0.0F, 0.0F, 10.0F, 9.0F, 4.0F), PartPose.offset(-5.0F, 5.0F, -11.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 45)
                .addBox(-2.0F, 0.0F, -3.0F, 5.0F, 7.0F, 5.0F), PartPose.offset(3.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 45)
                .addBox(-3.0F, 0.0F, -3.0F, 5.0F, 7.0F, 5.0F), PartPose.offset(-3.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(21, 45)
                .addBox(0.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F), PartPose.offset(7.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(21, 45)
                .addBox(-4.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F), PartPose.offset(-7.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("left_head", CubeListBuilder.create().texOffs(29, 31)
                .addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F), PartPose.offset(4.0F, 1.0F, 0.0F));
        root.addOrReplaceChild("right_head", CubeListBuilder.create().texOffs(29, 31)
                .addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F), PartPose.offset(-4.0F, 1.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(StrangerRenderState state) {
        super.setupAnim(state);
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
        this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * state.walkAnimationSpeed;
        this.leftHead.yRot = this.rightHead.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.leftHead.xRot = this.rightHead.xRot = state.xRot * (float) (Math.PI / 180.0D);
    }
}
