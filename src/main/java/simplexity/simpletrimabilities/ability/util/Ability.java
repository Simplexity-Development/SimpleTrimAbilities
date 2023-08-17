package simplexity.simpletrimabilities.ability.util;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Ability {

    private static final HashMap<String, Ability> ABILITY_BY_ID = new HashMap<>();
    private static final HashMap<Material, Set<Ability>> ABILITIES_BY_ARMOR = new HashMap<>();
    private static final HashMap<AbilityType, Set<Ability>> ABILITIES_BY_TYPE = new HashMap<>();
    private static final HashMap<ArmorTrim, Set<Ability>> ABILITIES_BY_TRIM = new HashMap<>();

    public final String id;
    public final AbilityType type;
    public final ArmorTrim trim;
    public final Material armor;

    protected Ability(String id, Material armor, AbilityType type, ArmorTrim trim) {
        this.id = id;
        this.type = type;
        this.trim = trim;
        this.armor = armor;
        ABILITY_BY_ID.put(this.id, this);
        if (!ABILITIES_BY_ARMOR.containsKey(this.armor)) ABILITIES_BY_ARMOR.put(this.armor, new HashSet<>());
        ABILITIES_BY_ARMOR.get(this.armor).add(this);
        if (!ABILITIES_BY_TYPE.containsKey(this.type)) ABILITIES_BY_TYPE.put(this.type, new HashSet<>());
        ABILITIES_BY_TYPE.get(this.type).add(this);
        if (!ABILITIES_BY_TRIM.containsKey(this.trim)) ABILITIES_BY_TRIM.put(this.trim, new HashSet<>());
        ABILITIES_BY_TRIM.get(this.trim).add(this);
    }

    /**
     * Resets and reloads the abilities from the abilities' configuration.
     */
    public static void populateAbilities() {
        ABILITY_BY_ID.clear();
        ABILITIES_BY_ARMOR.clear();
        ABILITIES_BY_TYPE.clear();
        ABILITIES_BY_TRIM.clear();
        // TODO: Populate from configuration.
        new AttributeAbility("test-attribute", Material.DIAMOND_CHESTPLATE, new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.SENTRY),
                Attribute.GENERIC_MAX_HEALTH, 1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.CHEST);
        new EventAbility("test-event", Material.DIAMOND_CHESTPLATE, new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.SENTRY));
        new TimedAbility("test-timed", Material.DIAMOND_CHESTPLATE, new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SENTRY));
    }

    /**
     * Returns the ability associated with this ID.
     * @param id Ability ID
     * @return Ability, null if no Ability with that ID exists.
     */
    public static Ability getAbility(String id) { return ABILITY_BY_ID.getOrDefault(id, null); }

    /**
     * Returns a list of abilities based on the Armor Item.
     * List is null-safe and returns empty if no abilities are associated to the Armor Item.
     * @param armor Armor Item Material Type
     * @return Unmodifiable list of abilities.
     */
    public static Set<Ability> getAbilities(Material armor) { return Collections.unmodifiableSet(ABILITIES_BY_ARMOR.getOrDefault(armor, Collections.emptySet())); }

    /**
     * Returns a list of abilities based on the Ability Type.
     * List is null-safe and returns empty if no abilities are associated to the Ability Type.
     * @param type Type of Ability
     * @return Unmodifiable list of abilities.
     */
    public static Set<Ability> getAbilities(AbilityType type) { return Collections.unmodifiableSet(ABILITIES_BY_TYPE.getOrDefault(type, Collections.emptySet())); }


    /**
     * Returns a list of abilities based on the Armor Trim.
     * List is null-safe and returns empty if no abilities are associated to the Armor Trim.
     * @param trim Armor Trim
     * @return Unmodifiable list of abilities.
     */
    public static Set<Ability> getAbilities(ArmorTrim trim) { return Collections.unmodifiableSet(ABILITIES_BY_TRIM.getOrDefault(trim, Collections.emptySet())); }

    /**
     * Performs the ability.
     * @return True if successful, false otherwise.
     */
    public abstract boolean performAbility(Player player, ItemStack armor);

}
