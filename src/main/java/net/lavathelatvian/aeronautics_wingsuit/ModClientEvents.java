package net.lavathelatvian.aeronautics_wingsuit;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = AeronauticsWingsuit.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WINGSUIT, WingsuitModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onAddRenderLayers(EntityRenderersEvent.AddLayers event) {
        // players
        for (PlayerSkin.Model skin : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skin);
            if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                //noinspection unchecked
                ((LivingEntityRenderer<AbstractClientPlayer, HumanoidModel<AbstractClientPlayer>>) livingRenderer)
                        .addLayer(new WingsuitLayer<>(
                                (LivingEntityRenderer<AbstractClientPlayer, HumanoidModel<AbstractClientPlayer>>) livingRenderer,
                                event.getEntityModels()
                        ));
            }
        }

        // armor stands
        EntityRenderer<? extends ArmorStand> armorStandRenderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (armorStandRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            //noinspection unchecked
            ((LivingEntityRenderer<ArmorStand, HumanoidModel<ArmorStand>>) livingRenderer)
                    .addLayer(new WingsuitLayer<>(
                            (LivingEntityRenderer<ArmorStand, HumanoidModel<ArmorStand>>) livingRenderer,
                            event.getEntityModels()
                    ));
        }
    }



}

