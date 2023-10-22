package me.spacety.ctf;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ManaHandler {
    private static HashMap<Player, Integer> manaMap;
    private static HashMap<Player, BossBar> manaBars;

    public ManaHandler() {
        manaMap = new HashMap<>();
        manaBars = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            BossBar bar = Bukkit.createBossBar("Mana", BarColor.BLUE, BarStyle.SOLID);
            bar.addPlayer(player);
            manaBars.put(player, bar);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                addMana();
            }
        }, 0L, 20L);
    }

    public void addMana() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int currentMana = manaMap.getOrDefault(player, 0);
            manaMap.put(player, currentMana + 1);

            BossBar bar = manaBars.get(player);
            if (bar != null) {
                bar.setTitle("Mana: " + (currentMana + 1));
                bar.setProgress(Math.min(1.0, (double) (currentMana + 1) / 100));
            }
        }
    }

    public int getMana(Player player) {
        return manaMap.getOrDefault(player, 0);
    }

    public static void addManaBar(Player player, BossBar bar) {
        manaBars.put(player, bar);
    }

    public static void removeManaBar(Player player) {
        BossBar bar = manaBars.remove(player);
        if (bar != null) {
            bar.removeAll();
        }
    }

    public void setMana(Player player, int newMana) {
        manaMap.put(player, newMana);

        BossBar bar = manaBars.get(player);
        if (bar != null) {
            bar.setTitle("Mana: " + newMana);
            bar.setProgress(Math.min(1.0, (double) newMana / 100));
        }
    }
}
