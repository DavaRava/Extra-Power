package de.davarava.extrapower.screen.renderer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/1.19.2/LICENSE
 *
 *  Slightly Modified Version by: Kaupenjoe
 */
public class EnergyDisplayTooltipArea {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final IEnergyStorage energy;
    private final NumberFormat nf = NumberFormat.getIntegerInstance();

    public EnergyDisplayTooltipArea(int xMin, int yMin, IEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, IEnergyStorage energy, int width, int height)  {
        xPos = xMin;
        yPos = yMin;
        this.width = width;
        this.height = height;
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        List<Component> tooltip = new ArrayList<>();

        long amount = energy.getEnergyStored();
        long capacity = energy.getMaxEnergyStored();

        MutableComponent amountString = Component.translatable("extrapower.tooltip.energy.amount.with.capacity", nf.format(amount), nf.format(capacity));
        tooltip.add(amountString.withStyle(ChatFormatting.GRAY));

        return tooltip;
    }

    public void render(GuiGraphics guiGraphics) {
        int stored = (int)(height * (energy.getEnergyStored() / (float)energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(xPos,yPos + (height - stored),xPos + width,
                yPos + height,0xffcc66ff, 0xff3a1a4d);
    }
}