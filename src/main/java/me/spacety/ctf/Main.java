package me.spacety.ctf;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        ManaHandler manaHandler = new ManaHandler();
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getCommand("ctf").setExecutor(new CTFCommandHandler(this));
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }



}
