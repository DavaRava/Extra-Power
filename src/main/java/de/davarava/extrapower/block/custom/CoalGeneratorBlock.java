package de.davarava.extrapower.block.custom;

import com.mojang.serialization.MapCodec;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.custom.CoalGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class CoalGeneratorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final MapCodec<CoalGeneratorBlock> CODEC = simpleCodec(CoalGeneratorBlock::new);

    public CoalGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    // FACING

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    //

    public String getNameOfBlock(){
        if(this.equals(ModBlocks.BASIC_COAL_GENERATOR.get())){
            return "Basic Coal Generator";
        }
        return null;
    }
    public int getCapacity() {
        if (this.equals(ModBlocks.BASIC_COAL_GENERATOR.get())) {
            return 8000;
        }
        return 0;
    }
    public int getMaxTransfer() {
        if (this.equals(ModBlocks.BASIC_COAL_GENERATOR.get())) {
            return 80;
        }
        return 0;
    }
    public int getProductionRate() {
        if (this.equals(ModBlocks.BASIC_COAL_GENERATOR.get())) {
            return 40;
        }
        return 0;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoalGeneratorBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;

        return createTickerHelper(blockEntityType, ModBlockEntities.COAL_GENERATOR_BE.get(),
                ((lvl, blockPos, blockState, coalGeneratorBlockEntity) ->
                        coalGeneratorBlockEntity.tick(lvl, blockPos, blockState)));
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
