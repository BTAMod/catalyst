package sunsetsatellite.catalyst.energy.improved.electric.test.gui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.inventory.PlayerInventoryElement;
import deus.guilib.element.elements.inventory.SlotGridElement;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.catalyst.energy.improved.electric.test.container.ContainerSimpleElectricBatteryBox;

public class GuiSimpleElectricBatteryBox extends PageGui {

	private static class Screen extends Page {

		private final PlayerInventoryElement playerInventory = (PlayerInventoryElement) new PlayerInventoryElement(40).setPosition(Placement.CENTER).setSid("INV");

		public Screen(Router router) {
			super(router);
			config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));

			SlotGridElement grid = (SlotGridElement) new SlotGridElement(2,2)
				.setPosition(Placement.CENTER);

			/*IElement background = new FreeElement(
				new Texture("assets/catalyst-energy/textures/gui/blank.png", 176, 166))
				.config((c) -> c
					.setCentered(true)
					.setChildrenPlacement(Placement.CHILD_DECIDE)
				)
				.addChildren(
					playerInventory,
					el
				)
				.setSid("BACKGROUND")
				.setPosition(Placement.CENTER);*/

			addContent(playerInventory);
			addContent(grid);
		}

		@Override
		public void update() {
			playerInventory.update();
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
