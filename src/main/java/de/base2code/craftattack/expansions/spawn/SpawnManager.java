package de.base2code.craftattack.expansions.spawn;

import de.base2code.craftattack.CraftAttack;
import de.base2code.craftattack.expansions.home.HomeCommand;
import de.base2code.craftattack.expansions.home.SetHomeCommand;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SpawnManager {
    private final File spawnFile = new File(CraftAttack.getInstance().getDataFolder(), "spawn.yml");
    private final YamlConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);

    public void initialize() {
        CraftAttack.getInstance().getCommand("spawn").setExecutor(new SpawnCommand());
        CraftAttack.getInstance().getCommand("setspawn").setExecutor(new SetSpawnCommand());
    }

    public void setSpawn(Location location) {
        spawnConfig.set("spawn", location);
        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getSpawn() {
        return spawnConfig.getLocation("spawn");
    }
}
