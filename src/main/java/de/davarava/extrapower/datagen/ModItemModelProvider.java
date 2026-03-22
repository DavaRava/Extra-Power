package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraPower.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
    }
}
