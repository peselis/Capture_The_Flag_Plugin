package me.spacety.ctf;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CTFCommandHandler implements CommandExecutor, TabCompleter {
    private Plugin plugin;
    public CTFCommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        System.out.println("Command received");
        if (strings.length < 1) {
            System.out.println("strings.length < 1");
            return true;
        }
        System.out.println(strings[1]);
        switch (strings[0]) {
            case "create":
                System.out.println("case: create");
                if (strings[1] == null || strings[2] == null) {
                    System.out.println("string[1] == null || strings[2] == null");
                    return true;
                }
                System.out.println("Handle creation");
                handleCreation(strings[1], strings[2], (Player) commandSender);
                break;
        }

        return true;
    }

    public void handleCreation(String type, String value, Player player) {

        int valueint = Integer.parseInt(value);

        System.out.println(type);

        switch (type) {
            case "InstantMana":
                System.out.println("InstantMana is the case!");
                handleInstantMana(valueint, player);
                break;
        }

    }

    public void handleInstantMana(int valueint, Player player) {

        System.out.println("handling Instant Mana!");
        InstantMana instantMana = new InstantMana(player.getLocation(), valueint);

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            if ("create".startsWith(args[0])) {
                return Arrays.asList("create");
            }
        } else if (args.length == 2) {
            if ("InstantMana".startsWith(args[1])) {
                return Arrays.asList("InstantMana");
            }
        }

        return new ArrayList<>();
    }

}

class InstantMana {
    private static HashMap<InstantMana, Integer> instanceMap;
    private int id;

    public InstantMana(Location location, int value) {

        if (instanceMap.isEmpty()) {
            this.id = 1;
        }else {
            this.id = Collections.max(instanceMap.values());
        }
        instanceMap.put(this, id);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                System.out.println("Runnig the loop!!!!");
                makeDoubleHelix(location, 2);
            }
        }, 0L, 1L);

    }

    public static InstantMana getInstance(int id) {
        for (Map.Entry<InstantMana, Integer> entry : instanceMap.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static void makeDoubleHelix(Location location, int r) {

        World world = location.getWorld();

        for (int i = 0; i < 360 * 0.3; i++) {

            double playerx = location.getX();
            double playery = location.getY();
            double playerz = location.getZ();

            double x = playerx + Math.cos(i) * r;

            double z = playerz + Math.sin(i) * r;

            double y = playery + 0.01 * i;

            Location location2 = new Location(world, x, y, z);

            Color tealColor = Color.fromRGB(0, 255, 255);

            float size = 1.0f;

            Particle.DustOptions dustOptions = new Particle.DustOptions(tealColor, size);

            world.spawnParticle(Particle.REDSTONE, location2, 1, dustOptions);


        }

    }

}