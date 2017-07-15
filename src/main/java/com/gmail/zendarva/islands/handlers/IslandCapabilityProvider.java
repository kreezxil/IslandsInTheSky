package com.gmail.zendarva.islands.handlers;

import com.gmail.zendarva.islands.capabilities.IIslandCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public class IslandCapabilityProvider
  implements ICapabilitySerializable<NBTBase>
{
  @CapabilityInject(IIslandCapability.class)
  public static final Capability<IIslandCapability> islandCap = null;

  private IIslandCapability instance = (IIslandCapability)islandCap.getDefaultInstance();

  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
  {
    return capability == islandCap;
  }

  @Nullable
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
  {
    return (T)(capability == islandCap ? islandCap.cast(this.instance) : null);
  }

  public NBTBase serializeNBT()
  {
    return islandCap.getStorage().writeNBT(islandCap, this.instance, null);
  }

  public void deserializeNBT(NBTBase nbt)
  {
    islandCap.getStorage().readNBT(islandCap, this.instance, null, nbt);
  }
}
