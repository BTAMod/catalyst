package sunsetsatellite.catalyst.energy.electric.example.gui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.inventory.PlayerInventoryElement;
import deus.guilib.element.elements.inventory.SlotGridElement;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.catalyst.energy.electric.example.container.ContainerSimpleElectricBatteryBox;

public class GuiSimpleElectricBatteryBox extends PageGui {

	private static class Screen extends Page {

		private final PlayerInventoryElement playerInventory = (PlayerInventoryElement) new PlayerInventoryElement(40).setPosition(Placement.CENTER).setSid("INV");

		public Screen(Router router) {
			super(router);
			config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));

			SlotGridElement grid = (SlotGridElement) new SlotGridElement(2,2)
				.setPosition(Placement.CENTER);

			addContent(playerInventory);
			addContent(grid);
		}

		@Override
		public void update() {
			super.update();
		}
	}

	private static final Page page = new Screen(router);

	static {
		router.registerRoute("0_home", page);
		router.navigateTo("0_home");
	}

	public GuiSimpleElectricBatteryBox(IInventory playerInventory, IInventory inventory) {
		super(new ContainerSimpleElectricBatteryBox(page, playerInventory, inventory));

		config(
			c -> c.setUseWindowSizeAsSize(true)
		);
	}
}
