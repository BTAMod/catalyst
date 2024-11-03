package sunsetsatellite.catalyst.core.util;

import sunsetsatellite.catalyst.core.util.tile.TEFeature;

public interface IHasFeatures {
	boolean hasFeature(String id);

	TEFeature getFeature(String id);

	TEFeature createAndAddFeature(String featureId);
}
