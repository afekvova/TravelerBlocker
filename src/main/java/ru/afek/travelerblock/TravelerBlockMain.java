package ru.afek.travelerblock;

import java.util.List;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TravelerBlockMain extends JavaPlugin implements Listener {

    @Getter
    TravelerBlockTask task;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.task = new TravelerBlockTask(this.getConfig().getStringList("worlds"));
        this.task.setDamage(this.getConfig().getDouble("damage"));
        this.task.runTaskTimer(this, 1, 1);
        this.getCommand("travelerblocker").setExecutor(this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        List<String> worlds = getConfig().getStringList("worlds");
        if (worlds.isEmpty() || !worlds.contains(block.getWorld().getName()) || block.getLocation().getBlockY() < 128)
            return;

        if (block.getType() == Material.PISTON || block.getType() == Material.MOVING_PISTON)
            event.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")
                && sender.hasPermission("travelerblocker.reload")) {
            this.saveDefaultConfig();
            this.task.setWorlds(this.getConfig().getStringList("worlds"));
            this.task.setDamage(this.getConfig().getDouble("damage"));
            sender.sendMessage("§aReloaded");
        }

        return true;
    }
}
