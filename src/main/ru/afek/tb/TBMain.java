package ru.afek.tb;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TBMain extends JavaPlugin implements Listener {

	private TBRun run;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
		this.run = new TBRun();
		this.run.setList(this.getConfig().getStringList("worlds"));
		this.run.setDamage(this.getConfig().getDouble("damage"));
		this.run.runTaskTimer(this, 1, 1);
		getCommand("travelerblocker").setExecutor(this);
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		List<String> worlds = getConfig().getStringList("worlds");
		if (worlds.isEmpty()) {
			return;
		}

		if (!worlds.contains(block.getWorld().getName())) {
			return;
		}

		if (block.getLocation().getBlockY() < 128) {
			return;
		}

		if (block.getType() == Material.PISTON_BASE
				|| block.getType() == Material.PISTON_EXTENSION
				|| block.getType() == Material.PISTON_MOVING_PIECE
				|| block.getType() == Material.PISTON_STICKY_BASE) {
			event.setCancelled(true);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("reload")
				&& sender.hasPermission("travelerblocker.reload")) {
			this.saveDefaultConfig();
			this.reloadConfig();
			this.run.setList(this.getConfig().getStringList("worlds"));
			this.run.setDamage(this.getConfig().getDouble("damage"));
			sender.sendMessage("§aReloaded");
		}
		return true;
	}
}
