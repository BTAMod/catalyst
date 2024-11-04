package sunsetsatellite.catalyst.energy.electric.example.api.impl.btwaila.tooltip;

import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricBase;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricGenerator;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricStorage;
import sunsetsatellite.catalyst.energy.electric.example.tile.TileEntitySimpleElectricBatteryBox;
import sunsetsatellite.catalyst.energy.electric.example.tile.TileEntitySimpleElectricGenerator;
import sunsetsatellite.catalyst.energy.electric.example.tile.TileEntitySimpleElectricMachine;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.ProgressBarOptions;

public class ElectricTooltip extends TileTooltip<TileEntityElectricBase> {
	@Override
	public void initTooltip() {
		addClass(TileEntitySimpleElectricBatteryBox.class);
		addClass(TileEntitySimpleElectricGenerator.class);
		addClass(TileEntitySimpleElectricMachine.class);
	}

	@Override
	public void drawAdvancedTooltip(TileEntityElectricBase tile, AdvancedInfoComponent c) {
		//Amps
		if(tile instanceof TileEntityElectricGenerator){
			/*ProgressBarOptions progressBarOptions = new ProgressBarOptions(160, "Amps OUT: ", true, false);
			progressBarOptions.fgOptions.setColor(0xFFAA00);
			progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
			progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
			c.drawProgressBarTextureWithText((int) tile.getAmpsCurrentlyUsed(),(int) tile.getMaxOutputAmperage(),progressBarOptions,0);*/
		} else if(tile instanceof TileEntityElectricStorage) {
			ProgressBarOptions progressBarOptions = new ProgressBarOptions(160, "Current Draw (mA): ", true, false);
			progressBarOptions.fgOptions.setColor(0xFFAA00);
			progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
			progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
			c.drawProgressBarTextureWithText((int) (tile.getAverageAmpLoad()*1000), (int) (tile.getMaxInputAmperage()*1000), progressBarOptions,0);
		} else {
			ProgressBarOptions progressBarOptions = new ProgressBarOptions(160, "Current Draw (mA): ", true, false);
			progressBarOptions.fgOptions.setColor(0xFFAA00);
			progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
			progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
			c.drawProgressBarTextureWithText((int) (tile.getAverageAmpLoad()*1000), (int) (tile.getMaxInputAmperage()*1000), progressBarOptions,0);
		}
		//Energy
		ProgressBarOptions progressBarOptions = new ProgressBarOptions(160, "Energy: ", true, true);
		progressBarOptions.fgOptions.setColor(0xFF2020);
		progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
		progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
		c.drawProgressBarTextureWithText((int) tile.getEnergy(),(int) tile.getCapacity(),progressBarOptions,0);

		c.drawStringJustified("Energy Net Change: "+ TextFormatting.YELLOW+tile.getAverageEnergyTransfer()+TextFormatting.RESET,0,160,0xFFFFFFFF);
	}
}
