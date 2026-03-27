package de.davarava.extrapower.block.custom;

import com.mojang.serialization.MapCodec;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.custom.BatteryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class BatteryBlock extends BaseEntityBlock {
    public static final MapCodec<BatteryBlock> CODEC = simpleCodec(BatteryBlock::new);

    public BatteryBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return  RenderShape.MODEL;
    }

    public String getNameOfBlock(){
        if(this.equals(ModBlocks.COPPER_BATTERY.get())){
            return "Copper ";
        } else if(this.equals(ModBlocks.IRON_BATTERY.get())){
            return "Iron ";
        } else if(this.equals(ModBlocks.GOLD_BATTERY.get())){
            return "Gold ";
        } else if(this.equals(ModBlocks.DIAMOND_BATTERY.get())){
            return "Diamond ";
        } else if(this.equals(ModBlocks.TITANIUM_BATTERY.get())){
            return "Titanium  ";
        }
        return null;
    }

    public int getCapacity() {
        if (this.equals(ModBlocks.COPPER_BATTERY.get())) {
            return 8000;
        } else if (this.equals(ModBlocks.IRON_BATTERY.get())) {
            return 32000;
        } else if (this.equals(ModBlocks.GOLD_BATTERY.get())) {
            return 128000;
        } else if (this.equals(ModBlocks.DIAMOND_BATTERY.get())) {
            return 512000;
        } else if (this.equals(ModBlocks.TITANIUM_BATTERY.get())) {
            return 2048000;
        }
        return 0;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("§dCapacity: §7" + formatNumber(getCapacity()) + " §lFE"));
    }

    private String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(number);

        return formatted.replace(',', '.');
    }
}
