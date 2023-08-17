package simplexity.simpletrimabilities.ability.util;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

import java.util.UUID;

public class AttributeAbility extends Ability {

    private final Attribute attribute;
    private final AttributeModifier modifier;

    protected AttributeAbility(String id, Material armor, ArmorTrim trim, Attribute attribute,
                               double modifierPower, AttributeModifier.Operation modifierOperation, EquipmentSlot modifierEquipmentSlot) {
        super(id, armor, AbilityType.ATTRIBUTE, trim);
        this.attribute = attribute;
        // TODO: Pull UUID from config if it exists. Otherwise, generate one and put it into the config.
        //  For Issue: Reloading / Restarting the server or configuration will reset the UUID,
        //  meaning the modifier can duplicate onto the armor.
        UUID uuid = UUID.randomUUID();
        this.modifier = new AttributeModifier(uuid, "sta-" + id, modifierPower, modifierOperation, modifierEquipmentSlot);
    }

    @Override
    public boolean performAbility(Player player, ItemStack armor) {
        if (/*player == null || */armor == null || !(armor.getItemMeta() instanceof ArmorMeta)) return false;
        // TODO: Permissions Check?
        ItemMeta meta = armor.getItemMeta();
        try {
            meta.addAttributeModifier(this.attribute, this.modifier);
        } catch (IllegalArgumentException e) {
            return false;
        }
        armor.setItemMeta(meta);
        return true;
    }
}
