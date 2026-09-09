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

public final class UnbornBabyModel extends EntityModel<UnbornBabyRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "unborn_baby"), "main");

    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart leg;

    public UnbornBabyModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.leg = root.getChild("leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 12.0F, 0.0F));
        head.addOrReplaceChild("right_eye",
                CubeListBuilder.create().texOffs(39, 17).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(-3.0F, -4.0F, -5.0F));
        head.addOrReplaceChild("left_eye",
                CubeListBuilder.create().texOffs(39, 12).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(1.0F, -4.0F, -5.0F));
        head.addOrReplaceChild("mouth",
                CubeListBuilder.create().texOffs(48, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(-1.0F, -1.0F, -5.0F));

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F),
                PartPose.offset(-1.5F, 12.0F, -1.0F));
        root.addOrReplaceChild("body_big",
                CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 0.0F, 0.0F, 8.0F, 4.0F, 11.0F),
                PartPose.offset(-6.0F, 20.0F, -3.0F));
        root.addOrReplaceChild("body_middle",
                CubeListBuilder.create().texOffs(13, 20).addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 5.0F),
                PartPose.offset(-4.0F, 18.0F, -2.0F));
        root.addOrReplaceChild("body_left",
                CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, 0.0F, 0.0F, 4.0F, 5.0F, 4.0F),
                PartPose.offset(2.0F, 19.0F, -1.0F));
        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(17, 46).addBox(-2.0F, -9.0F, -1.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offset(-6.0F, 21.0F, 1.533333F));
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(0, 56).addBox(0.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offset(6.0F, 21.0F, 2.0F));
        root.addOrReplaceChild("leg",
                CubeListBuilder.create().texOffs(26, 46).addBox(-1.0F, -9.0F, 0.0F, 3.0F, 10.0F, 3.0F),
                PartPose.offset(-1.0F, 22.0F, 8.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(UnbornBabyRenderState state) {
        super.setupAnim(state);
        this.leg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);

        float attack2 = Mth.sin(state.attackTime * (float) Math.PI);
        float attack = Mth.sin((1.0F - (1.0F - state.attackTime) * (1.0F - state.attackTime)) * (float) Math.PI);
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.yRot = -(0.1F - attack2 * 0.6F);
        this.leftArm.yRot = 0.1F - attack2 * 0.6F;
        float baseArmAngle = -(float) Math.PI / 2.25F;
        this.rightArm.xRot = baseArmAngle + attack2 * 1.2F - attack * 0.4F + 90.0F;
        this.leftArm.xRot = baseArmAngle + attack2 * 1.2F - attack * 0.4F - 80.0F;
        this.rightArm.zRot += Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.zRot -= Mth.cos(state.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.xRot += Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
        this.leftArm.xRot -= Mth.sin(state.ageInTicks * 0.067F) * 0.05F;
    }
}
