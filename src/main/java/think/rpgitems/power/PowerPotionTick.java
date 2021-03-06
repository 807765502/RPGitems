package think.rpgitems.power;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.data.Locale;
import think.rpgitems.power.types.PowerTick;

public class PowerPotionTick extends Power implements PowerTick {

    public int amplifier = 2;
    public PotionEffectType effect = PotionEffectType.SPEED;

    @Override
    public void tick(Player player) {
        if (item.getHasPermission() == true && player.hasPermission(item.getPermission()) == false) {
            player.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.permission")));
        } else {
            boolean hasEffect = false;
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                if (potionEffect.getType().equals(effect)) {
                    hasEffect = true;
                    if (potionEffect.getDuration() <= 5 || potionEffect.getAmplifier() < amplifier)
                        player.addPotionEffect(new PotionEffect(effect, 60, amplifier, true), true);
                    break;
                }
            }
            if (!hasEffect) {
                player.addPotionEffect(new PotionEffect(effect, 60, amplifier, true), true);
            }
        }

    }

    @Override
    public void init(ConfigurationSection s) {
        amplifier = s.getInt("amplifier");
        effect = PotionEffectType.getByName(s.getString("effect", "heal"));

    }

    @Override
    public void save(ConfigurationSection s) {
        s.set("amplifier", amplifier);
        s.set("effect", effect.getName());

    }

    @Override
    public String getName() {
        return "potiontick";
    }

    @Override
    public String displayText() {
        return ChatColor.GREEN + String.format(Locale.get("power.potiontick"), effect.getName().toLowerCase().replaceAll("_", " "), amplifier + 1);
    }
}
