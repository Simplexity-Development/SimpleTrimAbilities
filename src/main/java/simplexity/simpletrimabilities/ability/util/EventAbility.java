package simplexity.simpletrimabilities.ability.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;

public class EventAbility extends Ability {

    public final EventType eventType;

    protected EventAbility(String id, Material armor, ArmorTrim trim, EventType eventType) {
        super(id, armor, AbilityType.EVENT, trim);
        this.eventType = eventType;
    }

    @Override
    public boolean performAbility(Player player, ItemStack armor) {
        return false;
    }

}
