package sunsetsatellite.catalyst;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.catalyst.core.util.MpGuiEntry;
import sunsetsatellite.catalyst.core.util.tile.TEFeature;
import sunsetsatellite.catalyst.core.util.tile.feature.ItemContainerFeature;
import sunsetsatellite.catalyst.energy.improved.electric.test.block.color.BlockColorWire;
import sunsetsatellite.catalyst.energy.improved.electric.test.container.ContainerSimpleElectricBatteryBox;
import sunsetsatellite.catalyst.energy.improved.electric.test.data.ElectricBlocks;
import sunsetsatellite.catalyst.energy.improved.electric.test.data.ElectricConfig;
import sunsetsatellite.catalyst.energy.improved.electric.test.data.ElectricItems;
import sunsetsatellite.catalyst.energy.improved.electric.test.data.WireMaterials;
import sunsetsatellite.catalyst.energy.improved.electric.test.gui.GuiSimpleElectricBatteryBox;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntityCable;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricBatteryBox;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricGenerator;
import sunsetsatellite.catalyst.energy.improved.electric.test.tile.TileEntitySimpleElectricMachine;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntityBatteryBox;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntitySimpleGenerator;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntitySimpleMachine;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntityWire;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static sunsetsatellite.catalyst.energy.improved.electric.test.data.ElectricBlocks.*;


public class CatalystEnergy implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "catalyst-energy";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TomlConfigHandler config;

	public static final Tag<Block> ENERGY_CONDUITS_CONNECT = Tag.of("energy_conduits_connect");
	public static final Tag<Block> WIRES_CONNECT = Tag.of("wires_connect");

	public static final String ITEM_CONTAINER_FEATURE = "catalyst-energy:feature/item_container";

	/*public static final BlockWire wire = new BlockBuilder(MOD_ID)
		.setTextures("catalyst-energy:block/wire")
		.build(new BlockWire("wire",1550));
	public static final BlockBatteryBox box = new BlockBuilder(MOD_ID)
		.setTextures("catalyst-energy:block/battery_box")
		.build(new BlockBatteryBox("box",1551));
	public static final BlockSimpleGenerator generator = new BlockBuilder(MOD_ID)
		.setTextures("catalyst-energy:block/machine_side")
		.setSouthTexture("catalyst-energy:block/generator")
		.build(new BlockSimpleGenerator("generator",1552));
	public static final BlockSimpleMachine machine = new BlockBuilder(MOD_ID)
		.setTextures("catalyst-energy:block/machine_side")
		.setSouthTexture("catalyst-energy:block/machine")
		.build(new BlockSimpleMachine("machine",1553));*/

	static {
		Toml configToml = new Toml("Catalyst: Energy configuration file.");
		configToml.addEntry("energyName","Energy");
		configToml.addEntry("energySuffix","E");
		config = new TomlConfigHandler(MOD_ID,configToml);
	}
	public static final String ENERGY_NAME = config.getString("energyName");
	public static final String ENERGY_SUFFIX = config.getString("energySuffix");

	public static double map(double valueCoord1,
							 double startCoord1, double endCoord1,
							 double startCoord2, double endCoord2) {

		final double EPSILON = 1e-12;
		if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
			throw new ArithmeticException("Division by 0");
		}

		double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
		return ratio * (valueCoord1 - startCoord1) + startCoord2;
	}
    @Override
    public void onInitialize() {
		EntityHelper.createTileEntity(TileEntityBatteryBox.class,"BatteryBox");
		EntityHelper.createTileEntity(TileEntitySimpleGenerator.class,"SimpleGenerator");
		EntityHelper.createTileEntity(TileEntitySimpleMachine.class,"SimpleMachine");
		EntityHelper.createTileEntity(TileEntityWire.class,"Wire");

		ElectricConfig.class.getClass();
		new WireMaterials().init();
		new ElectricBlocks().init();
		new ElectricItems().init();

		TEFeature.registerFeature(ITEM_CONTAINER_FEATURE, ItemContainerFeature.class);

		EntityHelper.createTileEntity(TileEntitySimpleElectricBatteryBox.class,"ElBatteryBox");
		EntityHelper.createTileEntity(TileEntitySimpleElectricGenerator.class,"ElSimpleGenerator");
		EntityHelper.createTileEntity(TileEntitySimpleElectricMachine.class,"ElSimpleMachine");
		EntityHelper.createTileEntity(TileEntityCable.class,"ElCable");

		Catalyst.GUIS.register("ElBatteryBox",new MpGuiEntry(TileEntitySimpleElectricBatteryBox.class, GuiSimpleElectricBatteryBox.class, ContainerSimpleElectricBatteryBox.class));

        LOGGER.info("Catalyst: Energy initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		BlockColorDispatcher.getInstance().addDispatch(testWireUlv1x,new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireLv1x, new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireMv1x, new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireHv1x, new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireEv1x, new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireUv1x, new BlockColorWire());
		BlockColorDispatcher.getInstance().addDispatch(testWireOv1x, new BlockColorWire());
	}
}
