package sunsetsatellite.catalyst.energy.improved.electric.test.data;

import sunsetsatellite.catalyst.core.util.DataInitializer;
import sunsetsatellite.catalyst.energy.improved.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.improved.electric.test.item.ItemBattery;
import turniplabs.halplibe.helper.ItemBuilder;

import static sunsetsatellite.catalyst.CatalystEnergy.*;

public class ElectricItems extends DataInitializer {

	public static ItemBattery battery;

	public void init() {
		if (initialized) return;
		LOGGER.info("Initializing items...");

		battery = new ItemBuilder(MOD_ID)
			.setIcon("catalyst-energy:item/battery0")
			.build(new ItemBattery("battery",ElectricConfig.getItemId("battery"), VoltageTier.LV));

		setInitialized(true);
	}
}
