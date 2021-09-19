package ru.afek.travelerblock;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class TravelerBlockTask extends BukkitRunnable {

    List<String> worlds;
    double damage = 2.0;

    public TravelerBlockTask(List<String> worlds) {
        this.worlds = worlds;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (worlds.isEmpty() || !worlds.contains(player.getWorld().getName()))
                return;

            if (player.getLocation().getBlockY() >= 128)
                player.damage(damage);
        }
    }
}