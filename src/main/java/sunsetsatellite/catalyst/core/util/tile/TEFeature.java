package sunsetsatellite.catalyst.core.util.tile;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.Catalyst;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class TEFeature {

	protected static Map<String,Class<? extends TEFeature>> AVAILABLE_FEATURES = new HashMap<>();

	public final String id;
	public World world;
	public int x;
	public int y;
	public int z;

	protected TEFeature(String id, World world, int x, int y, int z) {
		this.id = id;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	protected TEFeature(String id, World world){
		this.id = id;
		this.world = world;
	}

	public static void registerFeature(String id, Class<? extends TEFeature> feature) {
		AVAILABLE_FEATURES.put(id, feature);
	}

	public static Class<? extends TEFeature> getFeatureClass(String id){
		return AVAILABLE_FEATURES.get(id);
	}

	public static Map<String,Class<? extends TEFeature>> getFeatureClasses(){
		return Collections.unmodifiableMap(AVAILABLE_FEATURES);
	}

	public static TEFeature createFeature(String id, World world, int x, int y, int z) {
		Class<? extends TEFeature> clazz = AVAILABLE_FEATURES.get(id);
		if (clazz == null) {
			Catalyst.LOGGER.error("No tile entity feature with id '{}'!", id);
			return null;
		}
		try {
			return clazz.getConstructor(String.class, World.class,Integer.class,Integer.class,Integer.class).newInstance(id,world,x,y,z);
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to create tile entity feature: '"+id+"'!",e);
		}
	}

	public static TEFeature loadFeature(CompoundTag nbt, World world){
		String id = nbt.getString("id");
		Class<? extends TEFeature> clazz = AVAILABLE_FEATURES.get(id);
		if (clazz == null) {
			Catalyst.LOGGER.error("No tile entity feature with id '{}'!", id);
			return null;
		}
		try {
			TEFeature feature = clazz.getConstructor(String.class,World.class).newInstance(id,world);
			feature.readFromNBT(nbt);
			return feature;
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to create tile entity feature: '"+id+"'!",e);
		}
	}

	public Block getBlock(){
		return world.getBlock(x,y,z);
	}

	public int getBlockMetadata(){
		return world.getBlockMetadata(x,y,z);
	}

	public void readFromNBT(CompoundTag nbttagcompound)
	{
		x = nbttagcompound.getInteger("x");
		y = nbttagcompound.getInteger("y");
		z = nbttagcompound.getInteger("z");
	}

	public void writeToNBT(CompoundTag nbttagcompound)
	{
		nbttagcompound.putString("id",id);
		nbttagcompound.putInt("x", x);
		nbttagcompound.putInt("y", y);
		nbttagcompound.putInt("z", z);
	}

	public abstract void tick();

}
