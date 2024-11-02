package sunsetsatellite.catalyst.core.util.tile;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;

import java.util.HashMap;
import java.util.Map;

public class ExtendableTileEntity extends TileEntity implements ITileEntityInit {

	protected Map<String,TEFeature> features = new HashMap<>();
	private CompoundTag loadData;

	@Override
	public void init(Block block) {
		loadFeatures();
	}

	@Override
	public void tick() {
		super.tick();
		features.forEach((S,F)->F.tick());
	}

	@Override
	public void writeToNBT(CompoundTag nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		CompoundTag featuresTag = new CompoundTag();

		features.forEach((S,F)->{
			CompoundTag featureTag = new CompoundTag();
			F.writeToNBT(featureTag);
			featuresTag.putCompound(S,featureTag);
		});
		nbttagcompound.put("Features",featuresTag);
	}

	public void loadFeatures(){
		CompoundTag featuresTag = loadData.getCompound("Features");
		for (Tag<?> tag : featuresTag.getValues()) {
			if(tag instanceof CompoundTag) {
				CompoundTag featureTag = (CompoundTag) tag;
				TEFeature feature = TEFeature.loadFeature(featureTag, worldObj);
				if(feature != null) {
					features.put(feature.id,feature);
				}
			}
		}
		loadData = null;
	}

	@Override
	public void readFromNBT(CompoundTag nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		loadData = nbttagcompound;
	}

	public boolean hasFeature(String id){
		return features.get(id) != null;
	}

	public TEFeature getFeature(String id){
		return features.get(id);
	}
}
