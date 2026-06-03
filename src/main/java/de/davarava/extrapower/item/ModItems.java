package de.davarava.extrapower.item;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.custom.FluidTankBlock;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExtraPower.MODID);

    public static final DeferredItem<Item> SOLAR_PANEL = ITEMS.registerItem("solar_panel",
            Item::new, new Item.Properties().stacksTo(16));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
