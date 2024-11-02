package sunsetsatellite.catalyst.energy.improved.electric.api.impl.btwaila.tooltip;

import net.minecraft.client.render.stitcher.TextureRegistry;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricBase;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricGenerator;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricBatteryBox;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricGenerator;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricMachine;
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
		ProgressBarOptions progressBarOptions = new ProgressBarOptions(160, "Amps: ", true, false);
		progressBarOptions.fgOptions.setColor(0xFFAA00);
		progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
		progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
		if(tile instanceof TileEntityElectricGenerator){
			c.drawProgressBarTextureWithText((int) tile.getAmpsCurrentlyUsed(),(int) tile.getMaxOutputAmperage(),progressBarOptions,0);

		} else {
			c.drawProgressBarTextureWithText((int) tile.getAmpsCurrentlyUsed(),(int) tile.getMaxInputAmperage(), progressBarOptions,0);
		}
		progressBarOptions = new ProgressBarOptions(160, "Energy: ", true, true);
		progressBarOptions.fgOptions.setColor(0xFF2020);
		progressBarOptions.fgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/sand"));
		progressBarOptions.bgOptions.setCoordinate(TextureRegistry.getTexture("minecraft:block/obsidian"));
		c.drawProgressBarTextureWithText((int) tile.getEnergy(),(int) tile.getCapacity(),progressBarOptions,0);
	}
}
