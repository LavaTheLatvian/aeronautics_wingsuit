package net.lavathelatvian.aeronautics_wingsuit;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.eriksonn.aeronautics.Aeronautics;
import dev.eriksonn.aeronautics.content.items.AviatorsGogglesItem;
import dev.eriksonn.aeronautics.index.AeroTags;
import dev.simulated_team.simulated.registrate.SimulatedRegistrate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {

    private static final SimulatedRegistrate REGISTRATE = Aeronautics.getRegistrate();


    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AeronauticsWingsuit.MODID);


    public static final DeferredItem<WingsuitItem> WINGSUIT = ITEMS.register("wingsuit",
            () -> new WingsuitItem.Layered(ArmorMaterials.LEATHER, new Item.Properties().rarity(Rarity.RARE), ResourceLocation.fromNamespaceAndPath(AeronauticsWingsuit.MODID, "wingsuit_jacket")));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}