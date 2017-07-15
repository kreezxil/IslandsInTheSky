package com.darva.islandsinthesky.commands;

import com.darva.islandsinthesky.IslandsInTheSky;
import com.darva.islandsinthesky.handlers.PlayerExtender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 3/5/2016.
 */
public class InviteCommand extends CommandBase {
    private ArrayList<String> aliases;

    public InviteCommand()
    {
        aliases = new ArrayList<String>();
        aliases.add("inv");
    }
    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public String getCommandName() {
        return "invite";
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/invite <playername> to invite them to join your island permenantly.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer))
        {
            IslandsInTheSky.logger.error("This command may only be invoked by a player.");
            return;
        }
        if (args.length == 0)
        {
            sender.addChatMessage(new ChatComponentText("Invite Who?"));
            return;
        }
        EntityPlayer victim = sender.getEntityWorld().getPlayerEntityByName(args[0]);

        if (victim ==null)
        {
            sender.addChatMessage(new ChatComponentText("They're not here."));
            return;
        }

        if (victim == sender)
        {
            sender.addChatMessage(new ChatComponentText("You invite yourself to your island.  You accept.  How droll."));
            return;
        }

        if (PlayerExtender.get((EntityPlayer) sender).islandX ==0)
        {
            ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("You have no island to invite them to!"));
            return;
        }
        if (PlayerExtender.get(victim).invitedBy== (EntityPlayer) sender)
        {
            ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("You've already invited them once.  Please don't spam."));
            return;
        }

        victim.addChatComponentMessage(new ChatComponentText("You've been invted to " + ((EntityPlayer) sender).getDisplayNameString() +"'s island"));
        victim.addChatComponentMessage(new ChatComponentText("use /accept to accept, ignore to decline."));
        ChatComponentText text =new ChatComponentText("You will lose access to your current island if you accept.");
        text.getChatStyle().setColor(EnumChatFormatting.BLUE);
        victim.addChatComponentMessage(text);
        ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("You have invited them to your island."));

        PlayerExtender.get(victim).invitedBy= (EntityPlayer) sender;
    }
}
