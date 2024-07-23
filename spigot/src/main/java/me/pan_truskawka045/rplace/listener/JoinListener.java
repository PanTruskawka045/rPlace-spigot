package me.pan_truskawka045.rplace.listener;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.rplace.RPlacePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final RPlacePlugin rPlacePlugin;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setAllowFlight(true);
        event.getPlayer().setFlying(true);
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 20, 0, 0f, 90f)); // Teleport to the center of the board and look down
        rPlacePlugin.getInventoryManager().giveItems(event.getPlayer());
    }

}
