package sh.veron.shadow.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ItemPickupGuardListener implements Listener {

    private static final long PICKUP_DELAY_MILLIS = 1000L;

    private final NamespacedKey dropOwnerKey;
    private final NamespacedKey dropTimeKey;

    public ItemPickupGuardListener(Plugin plugin) {
        this.dropOwnerKey = new NamespacedKey(plugin, "drop-owner");
        this.dropTimeKey = new NamespacedKey(plugin, "drop-time");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!player.getAllowFlight()) {
            return;
        }

        tagItem(event.getItemDrop(), player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPickupAttempt(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (isRestrictedForPlayer(event.getItem(), player)) {
            event.setCancelled(true);
        }
    }

    private void tagItem(Item item, Player owner) {
        item.getPersistentDataContainer().set(dropOwnerKey, PersistentDataType.STRING, owner.getUniqueId().toString());
        item.getPersistentDataContainer().set(dropTimeKey, PersistentDataType.LONG, System.currentTimeMillis());
    }

    private boolean isRestrictedForPlayer(Item item, Player player) {
        String ownerId = item.getPersistentDataContainer().get(dropOwnerKey, PersistentDataType.STRING);
        if (ownerId == null || !ownerId.equals(player.getUniqueId().toString())) {
            return false;
        }

        Long dropTime = item.getPersistentDataContainer().get(dropTimeKey, PersistentDataType.LONG);
        if (dropTime == null) {
            return false;
        }

        return System.currentTimeMillis() - dropTime < PICKUP_DELAY_MILLIS;
    }
}