package com.gmail.zendarva.islands.capabilities;

import com.gmail.zendarva.islands.ConfigurationHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;






public class IslandCapability
  implements IIslandCapability
{
  EntityPlayer invitedBy;
  BlockPos islandLoc;
  BlockPos islandCorner;
  boolean isOwner = false;


  public EntityPlayer getInvitedBy()
  {
    return this.invitedBy;
  }

  public void setInvitedBy(EntityPlayer entity)
  {
    this.invitedBy = entity;
  }


  public BlockPos getIslandLoc()
  {
    return this.islandLoc;
  }

  public void setIslandLoc(BlockPos pos)
  {
    this.islandLoc = pos;
  }

  public BlockPos getIslandCorner()
  {
    return this.islandCorner;
  }

  public void setIslandCorner(BlockPos pos)
  {
    this.islandCorner = pos;
  }

  public boolean getOwner()
  {
    return this.isOwner;
  }

  public void setOwner(boolean value)
  {
    this.isOwner = value;
  }

  public void set(IIslandCapability cap)
  {
    this.invitedBy = cap.getInvitedBy();
    this.islandCorner = cap.getIslandCorner();
    this.islandLoc = cap.getIslandLoc();
  }

  public boolean isOnIsland(EntityPlayer entity)
  {
    BlockPos pos = getIslandCorner();
    BlockPos entityPos = entity.getPosition();
    if ((entityPos.getX() <= pos.getX() + ConfigurationHolder.islandSize) && (entityPos.getX() >= pos.getX()) &&
      (entityPos.getZ() <= pos.getZ() + ConfigurationHolder.islandSize) && (entityPos.getZ() >= pos.getZ()))
      return true;
    return false;
  }
}


