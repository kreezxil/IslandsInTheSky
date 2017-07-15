package com.darva.islandsinthesky.commands;

import com.darva.islandsinthesky.IslandsInTheSky;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 9/26/2015.
 */
public class SpawnCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/spawn - Returns you to spawn.";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] p_71515_2_) {
        if (!(sender instanceof EntityPlayer))
        {
            IslandsInTheSky.logger.error("This command may only be invoked by a player.");
            return;
        }


        EntityPlayer player = (EntityPlayer) sender;

        if (IslandCommand.isMoving(player))
        {
            player.addChatComponentMessage(new ChatComponentText("You must stop moving before you teleport."));
            return;
        }


        if (sender.getEntityWorld().isRemote)
            return;

        player.dimension = 0;
        BlockPos coords = DimensionManager.getWorld(0).getSpawnPoint();
        player.setPositionAndUpdate(coords.getX(), coords.getY(),coords.getZ());

    }
}
