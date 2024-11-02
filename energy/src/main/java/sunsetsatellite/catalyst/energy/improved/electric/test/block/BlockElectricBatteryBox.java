package sunsetsatellite.catalyst.energy.improved.electric.test.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
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
		return String.format("%SBattery Slots: %s%d\n%sMax Voltage %sIN/OUT%s: %s%dV %s(%s%s%s)\n%sMax Current %sIN%s: %s%dA\n%sMax Current %sOUT%s: %s%dA\n%sEnergy Capacity: %s%dEU",
			TextFormatting.LIGHT_GRAY, TextFormatting.WHITE, 4,
			TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, TextFormatting.LIGHT_GRAY,
			TextFormatting.LIME, tier.maxVoltage, TextFormatting.LIGHT_GRAY, tier.textColor, tier.name(), TextFormatting.LIGHT_GRAY,
			TextFormatting.LIGHT_GRAY, TextFormatting.RED, TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, 1,
			TextFormatting.LIGHT_GRAY, TextFormatting.LIME, TextFormatting.LIGHT_GRAY, TextFormatting.ORANGE, 4,
			TextFormatting.LIGHT_GRAY, TextFormatting.YELLOW, tier.maxVoltage*64
		);
	}
}
