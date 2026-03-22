package de.davarava.extrapower.block.custom;

import com.mojang.serialization.MapCodec;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.custom.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class FluidTankBlock extends BaseEntityBlock {
    public static final MapCodec<FluidTankBlock> CODEC = simpleCodec(FluidTankBlock::new);
    public FluidTankBlock(Properties properties) {
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

    public String getNameOfBlock(){
        if(this.equals(ModBlocks.COPPER_FLUID_TANK.get())){
            return "Copper ";
        } else if(this.equals(ModBlocks.IRON_FLUID_TANK.get())){
            return "Iron ";
        } else if(this.equals(ModBlocks.GOLD_FLUID_TANK.get())){
            return "Gold ";
        } else if(this.equals(ModBlocks.DIAMOND_FLUID_TANK.get())){
            return "Diamond ";
        } else if(this.equals(ModBlocks.TITANIUM_FLUID_TANK.get())){
            return "Titanium  ";
        }
        return null;
    }

    public int getCapacity() {
        if (this.equals(ModBlocks.COPPER_FLUID_TANK.get())) {
            return 8000;
        } else if (this.equals(ModBlocks.IRON_FLUID_TANK.get())) {
            return 16000;
        } else if (this.equals(ModBlocks.GOLD_FLUID_TANK.get())) {
            return 32000;
        } else if (this.equals(ModBlocks.DIAMOND_FLUID_TANK.get())) {
            return 64000;
        } else if (this.equals(ModBlocks.TITANIUM_FLUID_TANK.get())) {
            return 128000;
        }
        return 0;
    }
    public int getFlowRate () {
        if (this.equals(ModBlocks.COPPER_FLUID_TANK.get())) {
            return 250;
        } else if (this.equals(ModBlocks.IRON_FLUID_TANK.get())) {
            return 500;
        } else if (this.equals(ModBlocks.GOLD_FLUID_TANK.get())) {
            return 750;
        } else if (this.equals(ModBlocks.DIAMOND_FLUID_TANK.get())) {
            return 1000;
        } else if (this.equals(ModBlocks.TITANIUM_FLUID_TANK.get())) {
            return 2000;
        }
        return 0;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity){
                fluidTankBlockEntity.drops();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof FluidTankBlockEntity fluidTankBlockEntity){
                player.openMenu(new SimpleMenuProvider(fluidTankBlockEntity, Component.literal(  getNameOfBlock() + "Fluid Tank")), pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTankBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) return null;

        return createTickerHelper(blockEntityType, ModBlockEntities.FLUID_TANK_BE.get(),
                ((lvl, blockPos, blockState, fluidTankBlockEntity) ->
                        fluidTankBlockEntity.tick(lvl, blockPos, blockState)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("§dCapacity: §7" + formatNumber(getCapacity()) + " §lmB"));
    }

    private String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(number);

        return formatted.replace(',', '.');
    }
}
