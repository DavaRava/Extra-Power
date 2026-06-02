package de.davarava.extrapower.block.custom;

import com.mojang.serialization.MapCodec;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.custom.FluidTankBlockEntity;
import de.davarava.extrapower.block.entity.custom.SolarPanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class SolarPanelBlock extends BaseEntityBlock {
    public static final MapCodec<SolarPanelBlock> CODEC = simpleCodec(SolarPanelBlock::new);
    private static final VoxelShape SHAPE = Block.box(0,0,0,16,5,16);

    public SolarPanelBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    //

    public String getNameOfBlock(){
        if(this.equals(ModBlocks.BASIC_SOLAR_PANEL.get())){
            return "Basic Solar Panel";
        }
        return null;
    }
    public int getCapacity() {
        if (this.equals(ModBlocks.BASIC_SOLAR_PANEL.get())) {
            return 1000;
        }
        return 0;
    }
    public int getMaxTransfer() {
        if (this.equals(ModBlocks.BASIC_SOLAR_PANEL.get())) {
            return 20;
        }
        return 0;
    }
    public int getProductionRate() {
        if (this.equals(ModBlocks.BASIC_SOLAR_PANEL.get())) {
            return 8;
        }
        return 0;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;

        return createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_PANE_BE.get(),
                ((lvl, blockPos, blockState, solarPanelBlockEntity) ->
                        solarPanelBlockEntity.tick(lvl, blockPos, blockState)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("§dCapacity: §7" + formatNumber(getCapacity()) + " §oFE"));
        tooltipComponents.add(Component.literal("§dProduces: §7" + formatNumber(getProductionRate()) + " §oFE/t"));
        tooltipComponents.add(Component.literal("§dTransfers: §7" + formatNumber(getMaxTransfer()) + " §oFE/t"));
    }

    private String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(number);

        return formatted.replace(',', '.');
    }
}
