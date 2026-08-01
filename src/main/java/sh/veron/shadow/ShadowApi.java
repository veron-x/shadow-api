package sh.veron.shadow;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import sh.veron.shadow.listener.FlyListener;
import sh.veron.shadow.listener.InventoryListener;
import sh.veron.shadow.listener.ItemPickupGuardListener;
import sh.veron.shadow.listener.WorldListener;

@Getter
@Setter
public final class ShadowApi extends JavaPlugin {

    private static ShadowApi instance;

    @Override
    public void onEnable() {
        instance = this;
        initListeners();
        disableAdvancementsInAllWorlds();
    }

    @Override
    public void onDisable() {
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new FlyListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new ItemPickupGuardListener(this), this);
    }

    private void disableAdvancementsInAllWorlds() {
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }
    }
}