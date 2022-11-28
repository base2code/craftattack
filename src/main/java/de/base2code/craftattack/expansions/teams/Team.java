package de.base2code.craftattack.expansions.teams;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private UUID teamLeader;
    private Location baseLocation;
    private final ArrayList<UUID> teamMembers;
    private final String teamName;

    public Team(UUID teamLeader, Location baseLocation, ArrayList<UUID> teamMembers, String teamName) {
        this.teamLeader = teamLeader;
        this.baseLocation = baseLocation;
        this.teamMembers = teamMembers;
        this.teamName = teamName;
    }

    public UUID getTeamLeader() {
        return teamLeader;
    }

    public ArrayList<UUID> getTeamMembers() {
        return teamMembers;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(Location location) {
        this.baseLocation = location;
    }

    public void setTeamLeader(UUID teamLeader) {
        this.teamLeader = teamLeader;
    }

    public void addTeamMember(UUID teamMember) {
        this.teamMembers.add(teamMember);
    }

    public void removeTeamMember(UUID teamMember) {
        this.teamMembers.remove(teamMember);
    }

    public String getTeamName() {
        return teamName;
    }
}
