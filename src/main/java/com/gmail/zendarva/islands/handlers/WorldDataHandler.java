package com.gmail.zendarva.islands.handlers;

import com.gmail.zendarva.islands.IslandData;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;


public class WorldDataHandler
{
  @SubscribeEvent
  public void onLoad(WorldEvent.Load event)
  {
    World world = event.getWorld();
    if (world.isRemote) {
      return;
    }
    if (world.provider.getDimension() != 0) {
      return;
    }
    IslandData data = (IslandData)world.getPerWorldStorage().getOrLoadData(IslandData.class, "IslandsInTheSky.WorldData");

    if (data == null)
    {
      data = new IslandData("IslandsInTheSky.WorldData");
      world.getPerWorldStorage().setData("IslandsInTheSky.WorldData", data);
      data.init();
      data.markDirty();
      com.gmail.zendarva.islands.Islands.worldData = data;
      return;
    }
    data.toDelete = new LinkedList<>();
    com.gmail.zendarva.islands.Islands.worldData = data;
  }

  @SubscribeEvent
  public void onWorldLoad(WorldTypeEvent event) {}
}








