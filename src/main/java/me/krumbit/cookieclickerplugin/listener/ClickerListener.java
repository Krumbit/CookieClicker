package me.krumbit.cookieclickerplugin.listener;

import me.krumbit.cookieclickerplugin.CookieClickerPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ClickerListener implements Listener {
    private final CookieClickerPlugin cookieClickerPlugin;

    public ClickerListener(CookieClickerPlugin cookieClickerPlugin) {
        this.cookieClickerPlugin = cookieClickerPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        if (this.cookieClickerPlugin.getClickerManager().getPlayerClicks(uuid) == null) {
            this.cookieClickerPlugin.getClickerManager().setPlayerClicks(uuid, 0);
        }

        if (this.cookieClickerPlugin.getClickerManager().hasClickerPowerup(uuid) == null) {
            this.cookieClickerPlugin.getClickerManager().setClickerPowerup(uuid, false);
        }
    }
}
