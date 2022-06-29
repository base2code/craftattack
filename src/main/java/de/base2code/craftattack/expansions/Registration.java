package de.base2code.craftattack.expansions;

import de.base2code.craftattack.discord.DiscordLink;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Registration {
    private final File file;
    private HashMap<DiscordLink, Boolean> registration = new HashMap<>();

    public Registration (File file) {
        this.file = file;
    }

    public void load() throws IOException {

    }

    public void save() throws IOException {

    }

    public void accept(String username, String discordid) {

    }

    public void deny(String username, String discordid) {

    }
}
