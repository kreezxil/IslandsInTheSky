package com.gmail.zendarva.islands.command;

import com.gmail.zendarva.islands.DeleteTarget;
import com.gmail.zendarva.islands.Islands;
import com.gmail.zendarva.islands.capabilities.IIslandCapability;
import com.gmail.zendarva.islands.capabilities.IslandCapability;
import com.gmail.zendarva.islands.handlers.IslandCapabilityProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 7/15/2017.
 */
public class AbandonCommand extends CommandBase {
    @Override
    public String getName() {
        return "abandon";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        StringBuilder builder = new StringBuilder();
        builder.append("/abandon        Abandons your island, requires confirmation.\n");
        builder.append("/abandon yes    Confirms abandoning your island.");
        return builder.toString();

    }
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) sender;
        IslandCapability cap = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
        if (cap.isAbandoning() == false)
        {
            cap.setAbandoning(true);
            player.sendMessage(new TextComponentString("Send /abandon yes to abandon your island.  This is permenant!"));
            return;
        }

        if (cap.isAbandoning() == true) {
            if (args.length == 0)
            {
                player.sendMessage(new TextComponentString("Abandoning Canceled."));
                cap.setAbandoning(false);
                return;
            }
            if (args[0].equals("yes") && args.length ==1){
                if (cap.getOwner()) {
                    DeleteTarget target = new DeleteTarget(cap);
                    Islands.worldData.toDelete.add(target);
                }

                player.dimension = 0;
                World world = DimensionManager.getWorld(0);
                BlockPos coords = world.getTopSolidOrLiquidBlock(world.getSpawnPoint());
                player.setPositionAndUpdate(coords.getX() + 0.5F, coords.getY(), coords.getZ() + 0.5F);

                player.sendMessage(new TextComponentString("You have abandoned your island."));
                cap.setIslandLoc(null);
                cap.setAbandoning(false);
                cap.setOwner(false);
                cap.setIslandCorner(null);
                clearInventory(player);
                return;
            }
            player.sendMessage(new TextComponentString("To abandon your island type '/abandon yes', to cancel abandoning type /abandon"));
            return;

        }
    }
    private void clearInventory(EntityPlayer player) {
        player.inventory.armorInventory.clear();
        player.inventory.clear();
        player.getInventoryEnderChest().clear();

    }
}
