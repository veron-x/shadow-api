package sh.veron.shadow;

import jdk.internal.platform.Metrics;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import sh.veron.shadow.listener.FlyListener;
import sh.veron.shadow.listener.InventoryListener;
import sh.veron.shadow.listener.WorldListener;

@Getter
@Setter
public final class ShadowApi extends JavaPlugin {


    private static ShadowApi instance;
    public void onEnable() {
        instance = this;
        this.initListener();
        this.disableAdvancementsInAllWorlds();
    }

    public void onDisable() {

    }

    public void initListener() {
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new FlyListener(), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    private void disableAdvancementsInAllWorlds() {
        for(World world : this.getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }

    }


}
