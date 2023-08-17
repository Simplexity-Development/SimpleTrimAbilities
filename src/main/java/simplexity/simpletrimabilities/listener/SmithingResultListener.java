package simplexity.simpletrimabilities.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import simplexity.simpletrimabilities.ability.util.Ability;

public class SmithingResultListener implements Listener {

    @EventHandler
    public void onPrepareSmithingEvent(PrepareSmithingEvent event) {
        ItemStack result = event.getResult();
        if (result == null) return;
        if (!(result.getItemMeta() instanceof ArmorMeta meta)) return;
        if (!meta.hasTrim()) return;
        // TODO: Stack Upgrades Config Check
        //  If the player should be able to keep stacking abilities as they change trims.
        //  All Trim Attributes belonging to this plugin have a name beginning with "sta-"
        Ability.getAbilities(meta.getTrim()).forEach(ability -> {
            if (ability.trim.equals(meta.getTrim())) ability.performAbility(null, result);
        });
    }

}
