package me.krumbit.cookieclickerplugin;

import co.aikar.commands.BukkitCommandManager;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import me.krumbit.cookieclickerplugin.command.ClickerCommand;
import me.krumbit.cookieclickerplugin.command.ItemCommand;
import me.krumbit.cookieclickerplugin.listener.ClickerListener;
import me.krumbit.cookieclickerplugin.listener.KrumbitCookieListener;
import me.krumbit.cookieclickerplugin.manager.ClickerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CookieClickerPlugin extends JavaPlugin {

    private BukkitCommandManager bukkitCommandManager;

    @Getter
    private static InventoryManager inventoryManager;

    @Getter
    private ClickerManager clickerManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("CookieClicker Loaded");

        registerCommands();
        registerListeners();

        this.clickerManager = new ClickerManager(this);

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
    }

    @Override
    public void onDisable() {
        unregisterCommands();
    }

    private void registerCommands() {
        this.bukkitCommandManager = new BukkitCommandManager(this);

        this.bukkitCommandManager.registerCommand(new ClickerCommand(this));
        this.bukkitCommandManager.registerCommand(new ItemCommand(this));
    }

    private void unregisterCommands() {
        this.bukkitCommandManager.unregisterCommands();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ClickerListener(this), this);
        getServer().getPluginManager().registerEvents(new KrumbitCookieListener(this), this);
    }
}
