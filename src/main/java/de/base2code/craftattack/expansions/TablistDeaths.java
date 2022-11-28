package de.base2code.craftattack.expansions;

import de.base2code.craftattack.CraftAttack;
import de.base2code.craftattack.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;

public class TablistDeaths implements Listener {

    public TablistDeaths() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(this, CraftAttack.getInstance());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        updateTablist(event.getEntity());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateTablist(event.getPlayer());
    }

    public static void updateTablist(Player player) {
        String teamName = " ";
        if (CraftAttack.getInstance().getTeamManager().getTeam(player) != null) {
            teamName = CraftAttack.getInstance().getTeamManager().getTeam(player);
        }
        player.setPlayerListName(player.getName() + " [" + Utils.stripColorCodes(teamName) + "] §c" + player.getStatistic(Statistic.DEATHS));
    }
}
