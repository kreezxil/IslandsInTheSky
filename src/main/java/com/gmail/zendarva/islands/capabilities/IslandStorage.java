package com.gmail.zendarva.islands.capabilities;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


public class IslandStorage
  implements IStorage<IIslandCapability>
{
  @Nullable
  public NBTBase writeNBT(Capability<IIslandCapability> capability, IIslandCapability instance, EnumFacing side)
  {
    NBTTagCompound tag = new NBTTagCompound();
    writeBlockPos(tag, "pos", instance.getIslandLoc());
    writeBlockPos(tag, "corner", instance.getIslandCorner());
    tag.setBoolean("owner", instance.getOwner());
    return tag;
  }

  public void readNBT(Capability<IIslandCapability> capability, IIslandCapability instance, EnumFacing side, NBTBase nbt)
  {
    instance.setIslandLoc(getBlockPos((NBTTagCompound)nbt, "pos"));
    instance.setIslandCorner(getBlockPos((NBTTagCompound)nbt, "corner"));
    instance.setOwner(((NBTTagCompound)nbt).getBoolean("owner"));
  }

  private void writeBlockPos(NBTTagCompound tag, String name, BlockPos pos) {
    if (pos == null)
      return;
    NBTTagCompound posTag = new NBTTagCompound();
    posTag.setInteger("x", pos.getX());
    posTag.setInteger("y", pos.getY());
    posTag.setInteger("z", pos.getZ());
    tag.setTag(name, posTag);
  }

  private BlockPos getBlockPos(NBTTagCompound tag, String name) {
    NBTTagCompound posTag = tag.getCompoundTag(name);
    if ((posTag == null) || (posTag.hasNoTags()))
      return null;
    BlockPos pos = new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z"));
    return pos;
  }
}


