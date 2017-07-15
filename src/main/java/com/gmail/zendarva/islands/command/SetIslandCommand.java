package com.gmail.zendarva.islands.command;

import com.gmail.zendarva.islands.capabilities.IslandCapability;
import com.gmail.zendarva.islands.handlers.IslandCapabilityProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.DimensionManager;



public class SetIslandCommand
  extends CommandBase
{
  public String getName()
  {
    return "setisland";
  }


  public String getUsage(ICommandSender sender)
  {
    StringBuilder response = new StringBuilder();

    if (((sender instanceof EntityPlayerMP)) &&
      (((EntityPlayerMP)sender.getCommandSenderEntity()).canUseCommand(4, null))) {
      response.append("setisland <player> sets their /island point to their current location.");
    }
    response.append("setisland Sets your /island to your current location, if you are on your island.");
    return response.toString();
  }



  public boolean checkPermission(MinecraftServer server, ICommandSender sender)
  {
    return true;
  }

  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    throws CommandException
  {
    if ((args.length == 0) || (args[0] == ""))
    {

      EntityPlayer player = (EntityPlayer)sender;
      if (player == null)
        return;
      IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);

      if (!cap.isOnIsland(player)) {
        player.sendMessage(new TextComponentString("You cannot set your island location here."));
        return;
      }
      if ((player.isAirBorne) || (player.isElytraFlying()) || (!player.isCollidedVertically)) {
        player.sendMessage(new TextComponentString("That seems like a bad idea."));
        return;
      }

      cap.setIslandLoc(player.getPosition());
      player.sendMessage(new TextComponentString("Your island location has been set to your current location."));
      return;
    }

    if (!((EntityPlayerMP)sender.getCommandSenderEntity()).canUseCommand(4, null)) {
      return;
    }

    EntityPlayer player = DimensionManager.getWorld(0).getPlayerEntityByName(args[0]);
    if (player == null)
      return;
    IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    cap.setIslandLoc(player.getPosition());

    player.sendMessage(new TextComponentString("Your island has been set to your current location."));
    sender.sendMessage(new TextComponentString("Thier island has been set to their current location"));
  }
}
