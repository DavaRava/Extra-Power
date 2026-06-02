package de.davarava.extrapower.item;

import de.davarava.extrapower.ExtraPower;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExtraPower.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
