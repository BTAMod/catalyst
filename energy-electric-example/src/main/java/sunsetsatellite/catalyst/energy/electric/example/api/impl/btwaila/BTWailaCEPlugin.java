package sunsetsatellite.catalyst.energy.electric.example.api.impl.btwaila;

import org.slf4j.Logger;
import sunsetsatellite.catalyst.CatalystEnergy;
import sunsetsatellite.catalyst.energy.electric.example.api.impl.btwaila.tooltip.ElectricTooltip;
import sunsetsatellite.catalyst.energy.electric.example.api.impl.btwaila.tooltip.ElectricWireTooltip;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

public class BTWailaCEPlugin implements BTWailaCustomTooltipPlugin {
    @Override
    public void initializePlugin(TooltipRegistry tooltipRegistry, Logger logger) {
        logger.info("Loading tooltips from "+ CatalystEnergy.MOD_ID+"..");
		tooltipRegistry.register(new ElectricTooltip());
		tooltipRegistry.register(new ElectricWireTooltip());
//        tooltipRegistry.register(new FluidTooltip());
//        tooltipRegistry.register(new MachineTooltip());
//        tooltipRegistry.register(new BoosterTooltip());
//        tooltipRegistry.register(new StabilizerTooltip());
//        tooltipRegistry.register(new ItemConduitTooltip());
//        tooltipRegistry.register(new StorageContainerTooltip());
//        tooltipRegistry.register(new MultiConduitTooltip());
    }
}
