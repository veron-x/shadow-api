package sh.veron.shadow.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.Iterator;

public class WorldListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.hideAdvancements(player);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        this.hideAdvancements(player);
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        event.message((Component)null);
    }

    private void hideAdvancements(Player player) {
        Iterator<Advancement> iterator = player.getServer().advancementIterator();

        while(iterator.hasNext()) {
            Advancement advancement = (Advancement)iterator.next();
            advancement.getCriteria().forEach((criterion) -> player.getAdvancementProgress(advancement).revokeCriteria(criterion));
        }

    }
}
