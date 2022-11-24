package de.base2code.craftattack.discord;

import de.base2code.craftattack.CraftAttack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;

public class DiscordIntegration implements Listener {
    private JDA jda;

    public static String channelId;

    private ArrayList<String> hide = new ArrayList<>();

    public DiscordIntegration(String token, String channelId) throws LoginException, InterruptedException {
        DiscordIntegration.channelId = channelId;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, CraftAttack.getInstance());

        jda = JDABuilder.createDefault(token)
                .addEventListeners(new DiscordListener())
                .enableIntents(
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS
                )
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        jda.awaitReady();

        updateStatus();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(CraftAttack.getInstance(), this::updateStatus, 0, 20 * 60 * 5);
    }

    public TextChannel getChannel() {
        return jda.getTextChannelById(channelId);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Tod");
        eb.setColor(Color.RED);
        eb.setDescription(event.getDeathMessage());

        eb.setThumbnail("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString() + "?size=128&default=MHF_Steve&overlay");

        getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Join");
        eb.setColor(Color.GREEN);
        eb.setDescription(ChatColor.stripColor(event.getJoinMessage()));

        eb.setThumbnail("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString() + "?size=128&default=MHF_Steve&overlay");

        hide.add(event.getPlayer().getUniqueId().toString());
        Bukkit.getScheduler().runTaskLater(CraftAttack.getInstance(), () -> {
            if (event.getPlayer().isOnline()) {
                hide.remove(event.getPlayer().getUniqueId().toString());
                getChannel().sendMessageEmbeds(eb.build()).queue();
            }
        }, 5);

        updateStatus();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (hide.contains(event.getPlayer().getUniqueId().toString())) {
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Quit");
        eb.setColor(Color.GREEN);
        eb.setDescription(ChatColor.stripColor(event.getQuitMessage()));

        eb.setThumbnail("https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString() + "?size=128&default=MHF_Steve&overlay");

        getChannel().sendMessageEmbeds(eb.build()).queue();

        updateStatus();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        getChannel().sendMessage(event.getPlayer().getName() + " » " + event.getMessage()).queue();
    }

    public void updateStatus() {
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, "mit " + Bukkit.getOnlinePlayers().size() + " Spielern"));
    }

    public Guild getCurrentGuild() {
        return jda.getGuilds().get(0);
    }

    public JDA getJda() {
        return jda;
    }
}
