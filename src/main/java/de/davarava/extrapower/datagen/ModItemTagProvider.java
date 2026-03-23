package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, ExtraPower.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
       ResourceLocation IngotsResLoc = ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, "ingots");
       this.tag(TagKey.create(ResourceKey.create(ResourceKey.createRegistryKey(IngotsResLoc), IngotsResLoc), IngotsResLoc))
               .add(ModItems.TITANIUM_INGOT.get())
               .add(Items.COPPER_INGOT)
               .add(Items.IRON_INGOT)
               .add(Items.GOLD_INGOT)
               .add(Items.NETHERITE_INGOT);
    }
}
