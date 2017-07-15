package com.gmail.zendarva.islands.proxy;

import com.gmail.zendarva.islands.capabilities.IIslandCapability;
import com.gmail.zendarva.islands.capabilities.IslandCapability;
import com.gmail.zendarva.islands.capabilities.IslandStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;


public class CommonProxy
{
  public void init()
  {
    CapabilityManager.INSTANCE.register(IIslandCapability.class, new IslandStorage(), IslandCapability.class);
  }
}
