package ru.afek.tb;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TBRun extends BukkitRunnable {

	private List<String> worlds = null;
	private double damage = 2.0;

	public void setList(List<String> list) {
		this.worlds = list;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (worlds.isEmpty()) {
				return;
			}

			if (!worlds.contains(player.getWorld().getName())) {
				return;
			}

			if (player.getLocation().getBlockY() >= 128) {
				player.damage(damage);
			}
		}
	}
}