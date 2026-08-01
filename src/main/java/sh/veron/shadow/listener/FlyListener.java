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

    private static final List<String> RESTRICTED_WORLDS = Arrays.asList("duel1", "duel2", "duel3", "duel4");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFlyCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        if (isRestrictedWorld(player) && isFlyCommand(command)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        revokeFlightIfRestricted(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        revokeFlightIfRestricted(event.getPlayer());
    }

    private void revokeFlightIfRestricted(Player player) {
        if (isRestrictedWorld(player) && player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    private boolean isRestrictedWorld(Player player) {
        return RESTRICTED_WORLDS.contains(player.getWorld().getName().toLowerCase());
    }

    private boolean isFlyCommand(String command) {
        return command.equals("/fly") || command.startsWith("/fly ");
    }
}