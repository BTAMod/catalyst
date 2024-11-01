package sunsetsatellite.catalyst.core.util.network;

import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3i;

public interface NetworkComponentTile extends NetworkComponent {

	Vec3i getPosition();

	boolean isConnected(Direction direction);

	void networkChanged(Network network);

	void removedFromNetwork(Network network);
}
