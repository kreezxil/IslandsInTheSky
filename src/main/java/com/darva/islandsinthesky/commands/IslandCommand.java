package com.darva.islandsinthesky.commands;

import com.darva.islandsinthesky.ConfigurationHolder;
import com.darva.islandsinthesky.IslandsInTheSky;
import com.darva.islandsinthesky.generation.VoidWorldChunkManager;
import com.darva.islandsinthesky.handlers.PlayerExtender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by James on 9/25/2015.
 */
public class IslandCommand extends CommandBase {
    private ArrayList<String> aliases;

    public IslandCommand()
    {
        aliases = new ArrayList<String>();
        aliases.add("is");
    }
    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public String getCommandName() {
        return "island";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/island will return you to your island. Creating it if necessary.";
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


        if (sender.getEntityWorld().isRemote)
            return;

        if (player.dimension != 0)
        {
            ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("You cannot do that here."));
            return;
        }
        if (isMoving(player))
        {
            player.addChatComponentMessage(new ChatComponentText("You must stop moving before you teleport."));
            return;
        }
        PlayerExtender data = PlayerExtender.get(player);
        if (data.islandX == 0 && data.islandZ == 0)
        {
            BlockPos coords = IslandsInTheSky.worldData.getNewIslandCoords();
            //Player has no island.  Let's assign one.
            data.islandX = coords.getX();
            data.islandZ = coords.getZ();

            //We'll need to generate the island here....
            VoidWorldChunkManager.GenerateIsland(player.getEntityWorld(), data.islandX - 2, data.islandZ - 2);
            int y = player.worldObj.getTopSolidOrLiquidBlock(coords).getY();

            player.setPositionAndUpdate(coords.getX(), y, coords.getZ());

            data.islandY=y;

            for(ItemStack stack : ConfigurationHolder.getInstance().getStartingInventory())
            {
                player.inventory.addItemStackToInventory(stack.copy());
            }
            player.addChatComponentMessage(new ChatComponentText("Creating your island, and sending you there."));

            return;
        }
        player.addChatComponentMessage(new ChatComponentText("Taking you back to your island."));
        //int y = player.worldObj.getTopSolidOrLiquidBlock(data.getPos()).getY();
        player.setPositionAndUpdate(data.islandX, data.islandY, data.islandZ);

    }

    public static boolean isMoving(EntityPlayer player)
    {

        if (player.isAirBorne)
        {
            return true;
        }
        System.out.println(player.motionY);
        if (player.motionY <-.1)
            return true;
        return false;
    }
}
