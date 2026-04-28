package net.lavathelatvian.aeronautics_wingsuit;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WingsuitModel extends HumanoidModel<LivingEntity> {

    public final ModelPart body;
    public final ModelPart leftWing;
    public final ModelPart rightWing;

    public WingsuitModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, int color) {
        poseStack.pushPose();
        this.body.translateAndRotate(poseStack);
        this.leftWing.render(poseStack, buffer, packedLight, packedOverlay);
        this.rightWing.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.isFallFlying()) {
            this.leftWing.zRot = (float) Math.toRadians(-15);
            this.rightWing.zRot = (float) Math.toRadians(15);

            this.leftWing.y = 0;
            this.rightWing.y = 0;

            this.leftWing.x = 2;
            this.rightWing.x = -2;
        } else {
            this.leftWing.zRot = (float) Math.toRadians(5);
            this.rightWing.zRot = (float) Math.toRadians(-5);

            this.leftWing.y = -2;
            this.rightWing.y = -2;

            this.leftWing.x = 0;
            this.rightWing.x = 0;
        }
        this.leftWing.yRot = (float) Math.toRadians(-0.1);
        this.rightWing.yRot = (float) Math.toRadians(0.1);

        this.leftWing.xRot = (float) Math.toRadians(5);
        this.rightWing.xRot = (float) Math.toRadians(5);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.getChild("body");

        body.addOrReplaceChild("left_wing",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-7.5F, 0.0F, 3.0F, 15.0F, 20.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        body.addOrReplaceChild("right_wing",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-7.5F, 0.0F, 3.0F, 15.0F, 20.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}
