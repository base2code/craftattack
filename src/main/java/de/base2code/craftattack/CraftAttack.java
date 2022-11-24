package de.base2code.craftattack;

import de.base2code.craftattack.commands.Maintenance;
import de.base2code.craftattack.commands.TPSCommand;
import de.base2code.craftattack.discord.DiscordIntegration;
import de.base2code.craftattack.expansions.ElytraSpawn;
import de.base2code.craftattack.expansions.NightSkip;
import de.base2code.craftattack.expansions.Registration;
import de.base2code.craftattack.expansions.TablistDeaths;
import de.base2code.craftattack.expansions.home.HomeManager;
import de.base2code.craftattack.expansions.spawn.SpawnManager;
import de.base2code.craftattack.expansions.teams.TeamManager;
import de.base2code.craftattack.listener.PlayerCommandPreProcessListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public final class CraftAttack extends JavaPlugin {
    /*
    -/ Elytra
    -/ Deaths in Tablist (Neben den Namen)
    -/ Spawn
    -/ /team
    -/ /tps für Spieler
    -/ 30% Bed
    - Spawn Protection
    -/ Home System
    -/ Discord sync
     */

    private static CraftAttack instance;

    private boolean maintenance = false;

    private TeamManager teamManager;
    private HomeManager homeManager;
    private SpawnManager spawnManager;
    private Registration registration;
    private DiscordIntegration discordIntegration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();

        this.getCommand("tps").setExecutor(new TPSCommand());
        this.getCommand("maintenance").setExecutor(new Maintenance());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerCommandPreProcessListener(), this);
        pm.registerEvents(new Maintenance(), this);

        registration = new Registration(new File(getDataFolder(), "registration.json"));
        try {
            registration.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        teamManager = new TeamManager();

        homeManager = new HomeManager();
        homeManager.initialize();

        spawnManager = new SpawnManager();
        spawnManager.initialize();

        new ElytraSpawn(this.getConfig().getInt("elytra_radius"));

        new NightSkip();
        new TablistDeaths();

        this.getDataFolder().mkdirs();

        if (new File(getDataFolder(), "maintenance").exists()) {
            maintenance = true;
        }

        if (this.getConfig().getBoolean("discord_enabled")) {
            try {
                discordIntegration = new DiscordIntegration(this.getConfig().getString("discord_token"), this.getConfig().getString("discord_channelid"));
            } catch (LoginException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (!maintenance) {
            new File(getDataFolder(), "maintenance").delete();
        } else {
            try {
                new File(getDataFolder(), "maintenance").createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            registration.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CraftAttack getInstance() {
        return instance;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public Registration getRegistration() {
        return registration;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public DiscordIntegration getDiscordIntegration() {
        return discordIntegration;
    }
}
