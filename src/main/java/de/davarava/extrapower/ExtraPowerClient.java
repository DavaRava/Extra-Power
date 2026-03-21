package de.davarava.extrapower;

import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.renderer.FluidTankBlockEntityRenderer;
import de.davarava.extrapower.screen.ModMenuTypes;
import de.davarava.extrapower.screen.custom.FluidTankScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ExtraPower.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ExtraPower.MODID, value = Dist.CLIENT)
public class ExtraPowerClient {
    public ExtraPowerClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_FLUID_TANK.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_FLUID_TANK.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_FLUID_TANK.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIAMOND_FLUID_TANK.get(), RenderType.TRANSLUCENT);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenuTypes.FLUID_TANK_MENU.get(), FluidTankScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.FLUID_TANK_BE.get(), FluidTankBlockEntityRenderer::new);
    }
}
