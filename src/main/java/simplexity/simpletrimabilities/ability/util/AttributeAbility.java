package simplexity.simpletrimabilities.ability.util;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

public class AttributeAbility extends Ability {

    private final Attribute attribute;
    private final String modifierName;
    private final double modifierPower;
    private final AttributeModifier.Operation modifierOperation;

    protected AttributeAbility(String id, Material armor, ArmorTrim trim, Attribute attribute,
                               String modifierName, double modifierPower, AttributeModifier.Operation modifierOperation) {
        super(id, armor, AbilityType.ATTRIBUTE, trim);
        this.attribute = attribute;
        this.modifierName = modifierName;
        this.modifierPower = modifierPower;
        this.modifierOperation = modifierOperation;
    }

    @Override
    public boolean performAbility(Player player, ItemStack armor) {
        if (player == null || armor == null) return false;
        // TODO: Permissions Check?
        ItemMeta meta = armor.getItemMeta();
        meta.addAttributeModifier(this.attribute, new AttributeModifier(this.modifierName, modifierPower, modifierOperation));
        armor.setItemMeta(meta);
        return true;
    }
}
