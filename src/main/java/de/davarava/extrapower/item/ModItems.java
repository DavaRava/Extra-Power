package de.davarava.extrapower.item;

import de.davarava.extrapower.ExtraPower;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
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
