package simplexity.simpletrimabilities.listener;

import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import simplexity.simpletrimabilities.ability.util.Ability;
import simplexity.simpletrimabilities.ability.util.AbilityType;
import simplexity.simpletrimabilities.ability.util.EventAbility;
import simplexity.simpletrimabilities.ability.util.EventType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerEventListeners implements Listener {

    // TODO: Create multiple valid types of Event Listeners,
    //  each with a check at the very top that immediately returns if no Abilities utilize the Event Type.

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerKillEntity(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        if (player == null) return;
        List<ItemStack> playerEquipment = getTrimmedArmor(player);
        if (playerEquipment.size() == 0) return;
        Set<Ability> abilities = new HashSet<>(Ability.getAbilities(AbilityType.EVENT));
        abilities.removeIf(ability -> {
                    EventType type = ((EventAbility) ability).eventType;
                    return !(type == EventType.KILL_MOB || type == EventType.KILL_MONSTER || type == EventType.KILL_ANIMAL);
                });
        if (abilities.size() == 0) return;
        if (entity instanceof Monster) abilities.removeIf(ability -> ((EventAbility) ability).eventType == EventType.KILL_ANIMAL);
        if (entity instanceof Animals) abilities.removeIf(ability -> ((EventAbility) ability).eventType == EventType.KILL_MONSTER);

        abilities.forEach(ability -> playerEquipment.forEach(armor -> ability.performAbility(player, armor)));
    }

    public List<ItemStack> getTrimmedArmor(Player player) {
        ItemStack[] armor = player.getEquipment().getArmorContents();
        List<ItemStack> trimmedArmor = new ArrayList<>();
        for (ItemStack armorPiece : armor) {
            ItemMeta meta = armorPiece.getItemMeta();
            if (meta instanceof ArmorMeta armorMeta && armorMeta.hasTrim()) trimmedArmor.add(armorPiece);
        }
        return trimmedArmor;
    }

}
