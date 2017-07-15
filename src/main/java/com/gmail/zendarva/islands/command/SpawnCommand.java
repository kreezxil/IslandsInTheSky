package com.gmail.zendarva.islands.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;


public class SpawnCommand
  extends CommandBase
{
  public String getName()
  {
    return "spawn";
  }

  public String getUsage(ICommandSender p_71518_1_)
  {
    return "/spawn - Returns you to spawn.";
  }


  public boolean checkPermission(MinecraftServer server, ICommandSender sender)
  {
    return true;
  }

  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
  {
    if (!(sender instanceof EntityPlayer))
    {

      return;
    }


    EntityPlayer player = (EntityPlayer)sender;

    if (IslandCommand.isMoving(player))
    {
      player.sendMessage(new TextComponentString("You must stop moving before you teleport."));
      return;
    }


    if (sender.getEntityWorld().isRemote) {
      return;
    }
    player.dimension = 0;
    World world = DimensionManager.getWorld(0);
    BlockPos coords = world.getTopSolidOrLiquidBlock(world.getSpawnPoint());
    player.setPositionAndUpdate(coords.getX() + 0.5F, coords.getY(), coords.getZ() + 0.5F);
  }
}