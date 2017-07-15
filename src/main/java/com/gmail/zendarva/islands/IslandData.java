 package com.gmail.zendarva.islands;

 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.world.storage.WorldSavedData;




public class IslandData
  extends WorldSavedData
{
  private int version = 2;

  private int startX;
  private int startZ;
  private int nextIndex;
  private static int PADDING = 32;
  private static int ISLAND_SIZE = 160;
  public static final String key = "IslandsInTheSky.WorldData";

  public IslandData(String value) {
    super(value);
  }



  public void readFromNBT(NBTTagCompound nbt) {}


  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    return null;
  }

  public void init()
  {
    this.startX = (160 + PADDING);
    this.startZ = (160 + PADDING);
    this.nextIndex = 0;
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
}