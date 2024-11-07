package sunsetsatellite.catalyst.core.util.network;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Global singleton that manages saving/loading network data, removing/adding blocks from/to networks, merging similar networks together,
 * and splitting disconnected parts of a network.
 */
public class NetworkManager implements Signal.Listener<BlockChangeInfo> {

	private static final Map<Integer, Set<Network>> NETS = new HashMap<>();
	private static final AtomicInteger ID_PROVIDER = new AtomicInteger(0);
	private static final NetworkManager INSTANCE = new NetworkManager();

	private NetworkManager() {}

	public static int getNetID(World world, int x, int y, int z) {
		Network net = getNet(world, x, y, z);
		return net == null ? -1 : net.hashCode();
	}

	public static Signal.Listener<BlockChangeInfo> getInstance() {
		return INSTANCE;
	}

	@Override
	public void signalEmitted(Signal<BlockChangeInfo> signal, BlockChangeInfo blockChanged) {
		if(signal != Catalyst.TILE_ENTITY_BLOCK_CHANGED_SIGNAL) return;

		if(blockChanged.id == 0){
			removeBlock(blockChanged);
		} else {
			addBlock(blockChanged);
		}

	}

	public static void addBlock(BlockChangeInfo blockChanged) {
		int x = blockChanged.pos.x;
		int y = blockChanged.pos.y;
		int z = blockChanged.pos.z;
		World world = blockChanged.world;

		if(!canBeNet(Block.blocksList[blockChanged.id])) {
			return;
		}

		NetworkComponent component = (NetworkComponent) Block.blocksList[blockChanged.id];

		Set<Network> nets = NETS.computeIfAbsent(world.dimension.id, i -> new HashSet<>());

		Set<Network> sideNets = new HashSet<>();
		for (Network net: nets) {
			for (Vec3i offset: Direction.getVecs()) {
				int px = x + offset.x;
				int py = y + offset.y;
				int pz = z + offset.z;
				if (net.existsOnPos(px, py, pz)) {
					sideNets.add(net);
				}
			}
		}

		Network net = null;
		int size = sideNets.size();
		//no nets around, create one
		if (size == 0) {
			net = new Network(world,component.getType());
			net.addBlock(x, y, z);
			for (Vec3i offset: Direction.getVecs()) {
				int px = x + offset.x;
				int py = y + offset.y;
				int pz = z + offset.z;
				if (canBeNet(world, px, py, pz)) {
					net.addBlock(px, py, pz);
				}
			}
			if (net.getSize() > 1) {
				nets.add(net);
			}
		}
		else if (size == 1) {
			Network potentialNet = sideNets.stream().findAny().get();
			if(potentialNet.isOfSameType(component)){
				potentialNet.addBlock(x, y, z);
				net = potentialNet;
			}
		}
		else { //multiple nets around
			Network[] netsArray = sideNets.toArray(new Network[size]);
			Network main = null;
			for (Network network : netsArray) {
				if(network.isOfSameType(component)){
					main = network;
					main.addBlock(x, y, z);
					for (Network otherNet : netsArray) {
						if(otherNet == main){
							continue;
						}
						if(otherNet.isOfSameType(main)){
							main.mergeNetwork(otherNet);
							nets.remove(otherNet);
						}
					}
					net = main;
					break;
				}
			}
		}

		if(net == null && getNet(world, x, y, z) == null) {
			net = new Network(world,component.getType());
			net.addBlock(x, y, z);
			for (Vec3i offset: Direction.getVecs()) {
				int px = x + offset.x;
				int py = y + offset.y;
				int pz = z + offset.z;
				if (canBeNet(world, px, py, pz)) {
					net.addBlock(px, py, pz);
				}
			}
			if (net.getSize() > 1) {
				nets.add(net);
			}
		}

		//add surrounding blocks to net if type matches
		for (Vec3i offset : Direction.getVecs()) {
			int px = x + offset.x;
			int py = y + offset.y;
			int pz = z + offset.z;
			if (canBeNet(world, px, py, pz) && getNet(world, px, py, pz) == null && net != null) {
				NetworkComponent sideComponent = (NetworkComponent) world.getBlock(px,py,pz);
				if(net.isOfSameType(sideComponent)){
					net.addBlock(px, py, pz);
				}
			}
		}
	}

	public static void removeBlock(BlockChangeInfo blockChanged) {
		int x = blockChanged.pos.x;
		int y = blockChanged.pos.y;
		int z = blockChanged.pos.z;
		World world = blockChanged.world;
		Set<Network> nets = NETS.get(world.dimension.id);

		if (nets == null) {
			return;
		}

		Network target = null;
		for (Network net: nets) {
			if (net.existsOnPos(x, y, z)) {
				target = net;
				break;
			}
		}

		if (target != null) {
			List<? extends Network> sideNets = target.removeBlock(x, y, z);
			if (sideNets != null) {
				nets.remove(target);
				nets.addAll(sideNets);
			}
			else if (target.getSize() < 2) {
				nets.remove(target);
			}
		}
	}

	public static int getUID() {
		return ID_PROVIDER.getAndIncrement();
	}

	public static void netsToTag(World world, CompoundTag root) {
		Set<Network> nets = NETS.get(world.dimension.id);
		CompoundTag dimTag = new CompoundTag();
		root.put("dim" + world.dimension.id, dimTag);

		if (nets == null) {
			return;
		}

		ListTag netsList = new ListTag();
		dimTag.put("nets", netsList);
		nets.forEach(network -> {
			netsList.addTag(network.toTag());
		});
	}

	public static void netsFromTag(World world, CompoundTag root) {
		Set<Network> nets = new HashSet<>();
		NETS.put(world.dimension.id, nets);

		CompoundTag dimTag = root.getCompound("dim" + world.dimension.id);
		if (dimTag == null) {
			return;
		}

		ListTag netsList = dimTag.getList("nets");
		final int size = netsList.tagCount();
		for (int i = 0; i < size; i++) {
			Network net = Network.fromTag(world, (CompoundTag) netsList.tagAt(i));
			if (net.getSize() > 1) {
				nets.add(net);
			}
		}
	}

	public static boolean canBeNet(WorldSource world, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		return canBeNet(block);
	}

	public static boolean canBeNet(Block block) {
		return block instanceof NetworkComponent;
	}

	private static Network getNet(World world, int x, int y, int z) {
		Set<Network> nets = NETS.get(world.dimension.id);
		if (nets != null) {
			for (Network net: nets) {
				if (net.existsOnPos(x, y, z)) {
					return net;
				}
			}
		}
		return null;
	}

	public static void updateAllNets(){
		NETS.forEach((dimId, nets)->{
			for (Network net : nets) {
				net.update();
			}
		});
	}
}
