package de.base2code.craftattack;

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

    private TeamManager teamManager;
    private HomeManager homeManager;
    private SpawnManager spawnManager;
    private Registration registration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();

        new ElytraSpawn(this.getConfig().getInt("elytra_radius"));

        this.getCommand("tps").setExecutor(new TPSCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerCommandPreProcessListener(), this);

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

        new NightSkip();
        new TablistDeaths();

        this.getDataFolder().mkdirs();

        if (this.getConfig().getBoolean("discord_enabled")) {
            new DiscordIntegration(this.getConfig().getString("discord_token"), this.getConfig().getString("discord_channelid"));
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
}
