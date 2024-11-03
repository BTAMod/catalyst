package sunsetsatellite.catalyst.energy.improved.electric.test.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.NumberUtil;
import sunsetsatellite.catalyst.energy.improved.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricBatteryBox;

public class BlockElectricBatteryBox extends BlockElectric {
	public BlockElectricBatteryBox(String key, int id, VoltageTier tier) {
		super(key, id, tier);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntitySimpleElectricBatteryBox();
	}

	@Override
	public String getDescription(ItemStack stack) {
		return "";
	}

	@Override
	public String getPersistentDescription(ItemStack stack) {
		return String.format("%SBattery Slots: %s%d\n%sMax Voltage %sIN/OUT%s: %s%dV %s(%s%s%s)\n%sMax Current %sIN%s: %s%dA\n%sMax Current %sOUT%s: %s%dA\n%sEnergy Capacity: %s%sJ",
			TextFormatting.LIGHT_GRAY, TextFormatting.WHITE, 4,
			TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, TextFormatting.LIGHT_GRAY,
			TextFormatting.LIME, tier.maxVoltage, TextFormatting.LIGHT_GRAY, tier.textColor, tier.name(), TextFormatting.LIGHT_GRAY,
			TextFormatting.LIGHT_GRAY, TextFormatting.RED, TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, 1,
			TextFormatting.LIGHT_GRAY, TextFormatting.LIME, TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, 4,
			TextFormatting.LIGHT_GRAY, TextFormatting.YELLOW, NumberUtil.formatMetric(128000*4)
		);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
		TileEntity tile = world.getBlockTileEntity(x,y,z);
		if(tile instanceof TileEntitySimpleElectricBatteryBox){
			Catalyst.displayGui(player,tile,"ElBatteryBox");
			return true;
		}
		return false;
	}
}
