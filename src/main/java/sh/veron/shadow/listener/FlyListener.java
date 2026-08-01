package sh.veron.shadow.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;

public class FlyListener implements Listener {
    private final List<String> restrictedWorlds = Arrays.asList("duel1", "duel2", "duel3", "duel4");

    public FlyListener() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onFlyCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName().toLowerCase();
        String command = event.getMessage().toLowerCase();
        if (this.restrictedWorlds.contains(worldName) && (command.equals("/fly") || command.startsWith("/fly "))) {
            event.setCancelled(true);
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName().toLowerCase();
        if (this.restrictedWorlds.contains(worldName) && player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName().toLowerCase();
        if (this.restrictedWorlds.contains(worldName) && player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }

    }
}
