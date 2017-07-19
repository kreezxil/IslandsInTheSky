package com.gmail.zendarva.islands.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public abstract interface IIslandCapability
{
  public abstract EntityPlayer getInvitedBy();
  
  public abstract void setInvitedBy(EntityPlayer paramEntityPlayer);
  
  public abstract BlockPos getIslandLoc();
  
  public abstract void setIslandLoc(BlockPos paramBlockPos);
  
  public abstract BlockPos getIslandCorner();
  
  public abstract void setIslandCorner(BlockPos paramBlockPos);
  
  public abstract boolean getOwner();
  
  public abstract void setOwner(boolean paramBoolean);
  
  public abstract void set(IIslandCapability paramIIslandCapability);
  
  public abstract boolean isOnIsland(EntityPlayer paramEntityPlayer);

  public abstract void setAbandoning(boolean value);

  public abstract boolean isAbandoning();
}


