package sunsetsatellite.catalyst.core.util;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.WorldSource;

public class Vec3i {
    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

	public Vec3i(){
        this.x = this.y = this.z = 0;
    }

    public Vec3i(int size){
        this.x = this.y = this.z = size;
    }

    public Vec3i(CompoundTag tag){
        readFromNBT(tag);
    }


    @Override
    public String toString() {
        return "Vec3i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double distanceTo(Vec3f vec3f) {
        double d = vec3f.x - this.x;
        double d1 = vec3f.y - this.y;
        double d2 = vec3f.z - this.z;
        return MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
    }

	public double distanceTo(Vec3i vec3i) {
		double d = vec3i.x - this.x;
		double d1 = vec3i.y - this.y;
		double d2 = vec3i.z - this.z;
		return MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
	}

	public void set(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

    public Vec3i add(int value){
        this.x += value;
        this.y += value;
        this.z += value;
        return this;
    }

    public Vec3i subtract(int value){
        this.x -= value;
        this.y -= value;
        this.z -= value;
        return this;
    }

    public Vec3i divide(int value){
        this.x /= value;
        this.y /= value;
        this.z /= value;
        return this;
    }

    public Vec3i multiply(int value){
        this.x *= value;
        this.y *= value;
        this.z *= value;
        return this;
    }

    public Vec3i add(Vec3i value){
        this.x += value.x;
        this.y += value.y;
        this.z += value.z;
        return this;
    }

    public Vec3i subtract(Vec3i value){
        this.x -= value.x;
        this.y -= value.y;
        this.z -= value.z;
        return this;
    }

    public Vec3i divide(Vec3i value){
        this.x /= value.x;
        this.y /= value.y;
        this.z /= value.z;
        return this;
    }

    public Vec3i multiply(Vec3i value){
        this.x *= value.x;
        this.y *= value.y;
        this.z *= value.z;
        return this;
    }

    public Vec3i rotate(Vec3i origin, Direction direction){
        Vec3i pos = this;
        switch (direction){
            case Z_POS:
                pos = new Vec3i(this.z + origin.x, this.y + origin.y, this.x + origin.z);
                break;
            case Z_NEG:
                pos = new Vec3i(-this.z + origin.x, this.y + origin.y, -this.x + origin.z);
                break;
            case X_NEG:
                pos = new Vec3i(-this.x + origin.x, this.y + origin.y, -this.z + origin.z);
                break;
			case X_POS:
                pos = new Vec3i(this.x + origin.x, this.y + origin.y, this.z + origin.z);
                break;
        }
        return pos;
    }

    public Vec3i rotate(Direction direction){
        Vec3i pos = this;
        switch (direction){
			case Z_POS:
                pos = new Vec3i(this.z, this.y, this.x);
                break;
            case Z_NEG:
                pos = new Vec3i(-this.z, this.y, -this.x);
                break;
			case X_NEG:
                pos = new Vec3i(-this.x, this.y, -this.z);
                break;
			case X_POS:
                pos = new Vec3i(this.x, this.y, this.z);
                break;
        }
        return pos;
    }

	public Vec3i rotateX(double angle){
		float cosine = MathHelper.cos((float) angle);
		float sine = MathHelper.sin((float) angle);
		y = (int) Math.round(y * (double)cosine + z * (double)sine);
		z = (int) Math.round(z * (double)cosine - y * (double)sine);
		return this;
	}

	public Vec3i rotateY(double angle){
		float cosine = MathHelper.cos((float) angle);
		float sine = MathHelper.sin((float) angle);
		x = (int) Math.round(x * (double)cosine + z * (double)sine);
		z = (int) Math.round(z * (double)cosine - x * (double)sine);
		return this;
	}

	public Vec3i rotateX(Vec3i origin, double angle){
		this.add(origin);
		float cosine = MathHelper.cos((float) angle);
		float sine = MathHelper.sin((float) angle);
		y = (int) Math.round(y * (double)cosine + z * (double)sine);
		z = (int) Math.round(z * (double)cosine - y * (double)sine);
		return this;
	}

	public Vec3i rotateY(Vec3i origin, double angle){
		float cosine = MathHelper.cos((float) angle);
		float sine = MathHelper.sin((float) angle);
		x = (int) Math.round(x * (double)cosine + z * (double)sine);
		z = (int) Math.round(z * (double)cosine - x * (double)sine);
		this.add(origin);
		return this;
	}

	public Vec3i set(Axis axis, int value){
		switch (axis) {
			case X:
				this.x = value;
				return this;
			case Y:
				this.y = value;
				return this;
			case Z:
				this.z = value;
				return this;
			default:
				return this;
		}
	}

	public int get(Axis axis){
		switch (axis) {
			case X:
				return x;
			case Y:
				return y;
			case Z:
				return z;
			default:
				return 0;
		}
	}

	public void writeToNBT(CompoundTag tag){
        tag.putInt("x",this.x);
        tag.putInt("y",this.y);
        tag.putInt("z",this.z);
    }

    public void readFromNBT(CompoundTag tag){
        this.x = tag.getInteger("x");
        this.y = tag.getInteger("y");
        this.z = tag.getInteger("z");
    }

    public Vec3i copy(){
        return new Vec3i(this.x,this.y,this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3i vec3I = (Vec3i) o;

        if (x != vec3I.x) return false;
        if (y != vec3I.y) return false;
        return z == vec3I.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

	public TileEntity getTileEntity(WorldSource worldSource){
		return worldSource.getBlockTileEntity(this.x, this.y, this.z);
	}

	public Block getBlock(WorldSource worldSource){
		return worldSource.getBlock(this.x, this.y, this.z);
	}

	public int getBlockMetadata(WorldSource worldSource){
		return worldSource.getBlockMetadata(this.x, this.y, this.z);
	}
}
