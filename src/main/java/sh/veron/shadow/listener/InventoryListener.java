package sh.veron.shadow.listener;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryListener implements Listener {
    private static final int RESTRICTED_MODEL_DATA = 10000;
    private static final String RESTRICTED_TITLE = "§fꈁꀀꈂꍅꈂꀁ§r§0ꈃꄖ";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            InventoryView view = event.getView();
            String title = view.getTitle();
            if (title.contains("§fꈁꀀꈂꍅꈂꀁ§r§0ꈃꄖ")) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event) {
        InventoryView view = event.getView();
        String title = view.getTitle();
        if (title.contains("§fꈁꀀꈂꍅꈂꀁ§r§0ꈃꄖ")) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(InventoryMoveItemEvent event) {
        if (this.isRestrictedItem(event.getItem())) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        ItemStack mainHand = event.getMainHandItem();
        ItemStack offHand = event.getOffHandItem();
        if (this.isRestrictedItem(mainHand) || this.isRestrictedItem(offHand)) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        if (this.isRestrictedItem(dropped)) {
            event.setCancelled(true);
        }

    }

    private boolean isRestrictedItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return false;
            } else if (meta.hasCustomModelData() && meta.getCustomModelData() == 10000) {
                return true;
            } else {
                return meta.hasDisplayName() && meta.getDisplayName().contains("§fꈁꀀꈂꍅꈂꀁ§r§0ꈃꄖ");
            }
        } else {
            return false;
        }
    }
}
