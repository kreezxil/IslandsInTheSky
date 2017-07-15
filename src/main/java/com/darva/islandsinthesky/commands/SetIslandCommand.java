package com.darva.islandsinthesky.commands;

import com.darva.islandsinthesky.handlers.PlayerExtender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 9/30/2015.
 */
public class SetIslandCommand extends CommandBase{
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public String getCommandName() {
        return "setisland";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "setisland <player> sets their /island point to their current location.";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] args) {
        if (args.length == 0 || args[0] == "")
        {
            return;
        }
        EntityPlayer player = DimensionManager.getWorld(0).getPlayerEntityByName(args[0]);
        if (player == null)
                return;
        PlayerExtender ext = PlayerExtender.get(player);
        ext.islandZ = (int) player.posZ;
        ext.islandX = (int) player.posX;

        player.addChatComponentMessage(new ChatComponentText("Your island has been set to your current location."));
        p_71515_1_.addChatMessage(new ChatComponentText("Thier island has been set to their current location"));
    }
}
