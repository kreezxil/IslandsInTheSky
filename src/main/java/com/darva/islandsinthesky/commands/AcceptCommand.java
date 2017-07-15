package com.darva.islandsinthesky.commands;

import com.darva.islandsinthesky.IslandsInTheSky;
import com.darva.islandsinthesky.handlers.PlayerExtender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

/**
 * Created by James on 3/5/2016.
 */
public class AcceptCommand extends CommandBase{
    @Override
    public String getCommandName() {
        return "accept";
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }


    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/accept will accept any invitations you have to join a players island.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (!(sender instanceof EntityPlayer))
        {
                IslandsInTheSky.logger.error("This command may only be invoked by a player.");
                return;
        }
        EntityPlayer player = (EntityPlayer) sender;

        if (PlayerExtender.get(player).invitedBy == null)
        {
            player.addChatComponentMessage(new ChatComponentText("You have no invitation to accept."));
            return;
        }

        player.addChatComponentMessage(new ChatComponentText("You have accepted " + PlayerExtender.get(player).invitedBy.getDisplayNameString() + "'s invitation to join their island."));

        EntityPlayer victim = PlayerExtender.get(player).invitedBy;
        NBTTagCompound nbt = new NBTTagCompound();
        PlayerExtender.get(victim).saveNBTData(nbt);
        PlayerExtender.get(player).loadNBTData(nbt);
        PlayerExtender.get(player).invitedBy=null;
    }
}
