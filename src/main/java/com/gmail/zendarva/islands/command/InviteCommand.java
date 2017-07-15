package com.gmail.zendarva.islands.command;

import com.gmail.zendarva.islands.capabilities.IslandCapability;
import com.gmail.zendarva.islands.handlers.IslandCapabilityProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class InviteCommand
  extends CommandBase
{
  private ArrayList<String> aliases;

  public InviteCommand()
  {
    this.aliases = new ArrayList();
    this.aliases.add("inv");
  }

  public List getAliases() {
    return this.aliases;
  }

  public String getName()
  {
    return "invite";
  }

  public boolean checkPermission(MinecraftServer server, ICommandSender sender)
  {
    return true;
  }

  public String getUsage(ICommandSender sender)
  {
    return "/invite <playername> to invite them to join your island permenantly.";
  }

  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
  {
    if (!(sender instanceof EntityPlayer))
    {

      return;
    }
    if (args.length == 0)
    {
      sender.sendMessage(new TextComponentString("Invite Who?"));
      return;
    }
    EntityPlayer victim = sender.getEntityWorld().getPlayerEntityByName(args[0]);

    if (victim == null)
    {
      sender.sendMessage(new TextComponentString("They're not here."));
      return;
    }

    if (victim == sender)
    {
      sender.sendMessage(new TextComponentString("You invite yourself to your island.  You accept.  How droll."));
      return;
    }
    EntityPlayer player = (EntityPlayer)sender;
    IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);

    if (!cap.getOwner()) {
      sender.sendMessage(new TextComponentString("You'll have to get the islands owner to do that."));
      return;
    }

    if (cap.getIslandLoc() == null)
    {
      ((EntityPlayer)sender).sendMessage(new TextComponentString("You have no island to invite them to!"));
      return;
    }
    if (cap.getInvitedBy() == (EntityPlayer)sender)
    {
      ((EntityPlayer)sender).sendMessage(new TextComponentString("You've already invited them once.  Please don't spam."));
      return;
    }

    victim.sendMessage(new TextComponentString("You've been invted to " + ((EntityPlayer)sender).getDisplayNameString() + "'s island"));
    victim.sendMessage(new TextComponentString("use /accept to accept, ignore to decline."));
    TextComponentString text = new TextComponentString("You will lose access to your current island if you accept.");
    text.getStyle().setColor(TextFormatting.BLUE);
    victim.sendMessage(text);
    ((EntityPlayer)sender).sendMessage(new TextComponentString("You have invited them to your island."));

    IslandCapability victimCap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    victimCap.setInvitedBy(player);
  }
}


