package sunsetsatellite.catalyst.energy.electric.example.item.model;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.energy.electric.example.item.ItemBattery;

public class ItemModelBattery extends ItemModelStandard {

	private static final String key = "catalyst-energy-electric-example:item/";
	public static final IconCoordinate level0 = TextureRegistry.getTexture(key+"battery_0");
	public static final IconCoordinate level1 = TextureRegistry.getTexture(key+"battery_1");
	public static final IconCoordinate level2 = TextureRegistry.getTexture(key+"battery_2");
	public static final IconCoordinate level3 = TextureRegistry.getTexture(key+"battery_3");
	public static final IconCoordinate level4 = TextureRegistry.getTexture(key+"battery_4");
	public static final IconCoordinate level5 = TextureRegistry.getTexture(key+"battery_5");
	public static final IconCoordinate level6 = TextureRegistry.getTexture(key+"battery_6");

	public ItemModelBattery(Item item, String namespace) {
		super(item, namespace);
	}

	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		ItemBattery batt = (ItemBattery) item;
		long energy = batt.getEnergy(itemStack);
		long capacity = batt.getCapacity(itemStack);

		if(energy <= 0){
			return level0;
		} else if(energy < capacity * (1 / 6f)) {
			return level1;
		} else if (energy < capacity * (2 / 6f)) {
			return level2;
		} else if (energy < capacity * (3 / 6f)) {
			return level3;
		} else if (energy < capacity * (4 / 6f)) {
			return level4;
		} else if (energy < capacity * (5 / 6f)) {
			return level5;
		} else {
			return level6;
		}
	}
}
