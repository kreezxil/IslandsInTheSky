 package com.gmail.zendarva.islands;

 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.world.WorldProviderHell;
 import net.minecraft.world.storage.WorldSavedData;

 import java.util.LinkedList;
 import java.util.List;


 public class IslandData
  extends WorldSavedData
{
  private int version = 2;

  private int startX;
  private int startZ;
  private int nextIndex;
  private static int PADDING = ConfigurationHolder.islandPadding;
  private static int ISLAND_SIZE = ConfigurationHolder.islandSize;
  public static final String key = "IslandsInTheSky.WorldData";
  public List<DeleteTarget> toDelete;

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    this.deserializeNBT(nbt.getCompoundTag("island"));
    this.toDelete= new LinkedList<>();
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    NBTTagCompound data = serializeNBT();
    compound.setTag("island",data);
    return compound;
  }

  public IslandData(String value) {
    super(value);
  }

  @Override
  public void deserializeNBT(NBTTagCompound nbt) {
    this.nextIndex= nbt.getInteger("index");
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound data = new NBTTagCompound();
    data.setInteger("index",nextIndex);
    return data;
  }

  public void init()
  {
    this.startX = (ISLAND_SIZE + PADDING);
    this.startZ = (ISLAND_SIZE + PADDING);
    toDelete = new LinkedList<>();
    this.nextIndex = 100;
  }


  public BlockPos getNewIslandCoords()
  {
    int x = this.nextIndex % 10;
    int z = this.nextIndex / 10;

    x = this.startX + x * ConfigurationHolder.islandSize + x * ConfigurationHolder.islandPadding;
    z = this.startZ + z * ConfigurationHolder.islandSize + z * ConfigurationHolder.islandPadding;

    x += ConfigurationHolder.islandSize / 2;
    z += ConfigurationHolder.islandSize / 2;
    BlockPos coords = new BlockPos(x, 64, z);
    markDirty();
    this.nextIndex += 1;
    return coords;
  }

  public IslandData(String name, int version) {
    super(name);
    this.version = version;
    this.toDelete= new LinkedList<>();
  }
}