package de.davarava.extrapower.block.custom;

import com.mojang.serialization.MapCodec;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.custom.EnergyCellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class EnergyCellBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<EnergyCellBlock> CODEC = simpleCodec(EnergyCellBlock::new);

    public EnergyCellBlock(Properties properties) {
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
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //

    public String getNameOfBlock(){
        if(this.equals(ModBlocks.BASIC_ENERGY_CELL.get())){
            return "Basic Energy Cell";
        }
        return null;
    }
    public int getCapacity() {
        if(this.equals(ModBlocks.BASIC_ENERGY_CELL.get())){
            return 32000;
        }
        return 0;
    }
    public int getMaxTransfer() {
        if (this.equals(ModBlocks.BASIC_ENERGY_CELL.get())) {
            return 1000;
        }
        return 0;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof EnergyCellBlockEntity batteryBlockEntity){
                player.openMenu(new SimpleMenuProvider(batteryBlockEntity, Component.literal(getNameOfBlock())), pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyCellBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("§dCapacity: §7" + formatNumber(getCapacity()) + " §oFE"));
    }

    private String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(number);

        return formatted.replace(',', '.');
    }
}
