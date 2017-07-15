package com.gmail.zendarva.islands.handlers;

import com.gmail.zendarva.islands.capabilities.IslandCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ProtectionHandler
{
  @SubscribeEvent
  public void breakEvent(BlockEvent.BreakEvent event)
  {
    EntityPlayer player = event.getPlayer();
    if ((player == null) || ((player instanceof FakePlayer)))
      return;
    if (player.dimension != 0)
      return;
    if (player.getEntityWorld().isRemote)
      return;
    IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    if ((!cap.isOnIsland(player)) && (!player.isCreative())) {
      TextComponentString errorMsg = new TextComponentString("You may only break blocks on your own island.");
      errorMsg.setStyle(new Style().setColor(TextFormatting.RED));
      player.sendMessage(errorMsg);
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void placeEvent(BlockEvent.PlaceEvent event)
  {
    EntityPlayer player = event.getPlayer();
    if ((player == null) || ((player instanceof FakePlayer)))
      return;
    if (player.dimension != 0)
      return;
    if (player.getEntityWorld().isRemote)
      return;
    IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    if ((!cap.isOnIsland(player)) && (!player.isCreative())) {
      TextComponentString errorMsg = new TextComponentString("You may only place blocks on your own island.");
      errorMsg.setStyle(new Style().setColor(TextFormatting.RED));
      player.sendMessage(errorMsg);
      event.setCanceled(true);
    }
  }
}


