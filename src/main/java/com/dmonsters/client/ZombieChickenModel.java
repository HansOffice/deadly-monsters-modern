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

public final class ZombieChickenModel extends EntityModel<ZombieChickenRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(DeadlyMonsters.MOD_ID, "zombie_chicken"), "main");

    private final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public ZombieChickenModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 16.0F, 0.0F));
        head.addOrReplaceChild(
                "upper_head",
                CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, -5.0F, -3.0F));
        head.addOrReplaceChild(
                "middle_head",
                CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(-1.0F, -4.0F, -3.0F));
        head.addOrReplaceChild(
                "bill",
                CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(-1.0F, -2.0F, -5.0F));

        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 5.0F, 7.0F),
                PartPose.offset(-3.0F, 16.0F, -1.0F));

        PartDefinition leftLeg = root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(1.0F, 21.0F, 2.0F));
        leftLeg.addOrReplaceChild(
                "left_foot",
                CubeListBuilder.create().texOffs(20, 24).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(-0.25F, 2.0F, -1.0F));

        PartDefinition rightLeg = root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(8, 26).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(-2.0F, 21.0F, 2.0F));
        rightLeg.addOrReplaceChild(
                "right_foot",
                CubeListBuilder.create().texOffs(20, 20).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(-0.75F, 2.0F, -1.0F));

        root.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create().texOffs(14, 12).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(3.0F, 16.0F, 0.0F, -0.7807508F, -0.0743572F, -0.2974289F));
        root.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create().texOffs(10, 19).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 4.0F),
                PartPose.offset(-3.0F, 16.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(ZombieChickenRenderState state) {
        super.setupAnim(state);

        this.head.xRot = state.xRot * (float) (Math.PI / 180.0D);
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0D);

        this.rightLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 2.4F * state.walkAnimationSpeed;
        this.leftLeg.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 2.4F * state.walkAnimationSpeed;

        this.rightWing.xRot = 0.0F;
        this.rightWing.yRot = 0.0F;
        this.rightWing.zRot = Mth.clamp(Mth.cos(state.walkAnimationPos + (float) Math.PI) * 2.4F, 0.2F, 100.0F);

        this.leftWing.xRot = -0.7807508F;
        this.leftWing.yRot = -0.0743572F;
        this.leftWing.zRot = -0.2974289F;
    }
}
