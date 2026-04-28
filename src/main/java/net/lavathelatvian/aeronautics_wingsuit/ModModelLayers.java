package net.lavathelatvian.aeronautics_wingsuit;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ModModelLayers {

    public static final ModelLayerLocation WINGSUIT = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AeronauticsWingsuit.MODID, "wingsuit"), "main"
    );

    // register all your model layers here
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WINGSUIT, WingsuitModel::createBodyLayer);
    }
}
