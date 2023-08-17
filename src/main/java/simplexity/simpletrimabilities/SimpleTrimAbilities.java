package simplexity.simpletrimabilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simpletrimabilities.ability.util.Ability;
import simplexity.simpletrimabilities.listener.SmithingResultListener;

public final class SimpleTrimAbilities extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new SmithingResultListener(), this);
        Ability.populateAbilities();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
