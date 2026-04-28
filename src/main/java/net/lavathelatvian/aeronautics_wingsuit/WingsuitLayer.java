package net.lavathelatvian.aeronautics_wingsuit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WingsuitLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation WINGSUIT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AeronauticsWingsuit.MODID, "textures/entity/wingsuit.png");

    private final WingsuitModel model;

    public WingsuitLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new WingsuitModel(modelSet.bakeLayer(ModModelLayers.WINGSUIT));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WINGSUIT)) return;

        ((HumanoidModel<LivingEntity>)(HumanoidModel<?>) this.getParentModel()).copyPropertiesTo(this.model);
        this.model.setupAnim((LivingEntity) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(WINGSUIT_TEXTURE));

        poseStack.pushPose();
        this.model.body.translateAndRotate(poseStack);
        this.model.leftWing.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        this.model.rightWing.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}

