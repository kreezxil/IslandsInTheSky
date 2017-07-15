 package com.gmail.zendarva.islands.command;

 import com.gmail.zendarva.islands.capabilities.IslandCapability;
 import com.gmail.zendarva.islands.handlers.IslandCapabilityProvider;
 import net.minecraft.command.CommandBase;
 import net.minecraft.command.CommandException;
 import net.minecraft.command.ICommandSender;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.server.MinecraftServer;
 import net.minecraft.util.text.TextComponentString;


 public class AcceptCommand
   extends CommandBase
 {
   public String getName()
   {
     return "accept";
   }

   public boolean checkPermission(MinecraftServer server, ICommandSender sender)
   {
     return true;
   }

   public String getUsage(ICommandSender sender)
   {
     return "/accept will accept any invitations you have to join a players island.";
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
   {
     if (!(sender instanceof EntityPlayer))
     {

       return;
     }
     EntityPlayer player = (EntityPlayer)sender;
     IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);



     if (cap.getInvitedBy() == null)
     {
       player.sendMessage(new TextComponentString("You have no invitation to accept."));
       return;
     }

     player.sendMessage(new TextComponentString("You have accepted " + cap.getInvitedBy().getDisplayNameString() + "'s invitation to join their island."));

     NBTTagCompound nbt = new NBTTagCompound();
     cap.setInvitedBy(null);
   }
 }

