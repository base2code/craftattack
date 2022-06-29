package de.base2code.craftattack.expansions.teams;

import de.base2code.craftattack.CraftAttack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamListener implements Listener {
    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            Player player1 = (Player) event.getEntity();
            Player player2 = (Player) event.getDamager();
            if (CraftAttack.getInstance().getTeamManager().isInSameTeam(player1, player2)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (CraftAttack.getInstance().getTeamManager().getTeam(event.getPlayer()) != null) {
            event.setMessage("[" + CraftAttack.getInstance().getTeamManager().getTeam(event.getPlayer()).replace("&", "§") + "] " + event.getMessage());
        }
    }
}
