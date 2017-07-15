package com.gmail.zendarva.islands.handlers;

import com.gmail.zendarva.islands.capabilities.IIslandCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;







public class CapabilityHandler
{
  public static final ResourceLocation islandCap = new ResourceLocation("islandsinthesky", "islandLoc");

  @SubscribeEvent
  public void AttachEntity(AttachCapabilitiesEvent<Entity> event) {
    if ((event.getObject() instanceof EntityPlayer)) {
      event.addCapability(islandCap, new IslandCapabilityProvider());
    }
  }


  @SubscribeEvent
  public void OnPlayerClone(PlayerEvent.Clone event)
  {
    EntityPlayer player = event.getEntityPlayer();
    IIslandCapability cap = (IIslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    IIslandCapability oldCap = (IIslandCapability)event.getOriginal().getCapability(IslandCapabilityProvider.islandCap, null);
    cap.set(oldCap);
  }
}


