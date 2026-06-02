package de.davarava.extrapower.item;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtraPower.MODID);

    public static final Supplier<CreativeModeTab> EXTRAPOWER_TAB =
            CREATIVE_MODE_TABS.register("extrapower_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("tab.extrapower"))
                    .icon(() -> new ItemStack(ModBlocks.BASIC_ENERGY_CELL))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.BASIC_FLUID_TANK);
                        pOutput.accept(ModBlocks.BASIC_ENERGY_CELL);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}