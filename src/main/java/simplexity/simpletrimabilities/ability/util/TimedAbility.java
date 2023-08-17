package simplexity.simpletrimabilities.ability.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;

public class TimedAbility extends Ability {

    protected TimedAbility(String id, Material armor, ArmorTrim trim) {
        super(id, armor, AbilityType.TIMED, trim);
    }

    @Override
    public boolean performAbility(Player player, ItemStack armor) {
        return false;
    }

}
