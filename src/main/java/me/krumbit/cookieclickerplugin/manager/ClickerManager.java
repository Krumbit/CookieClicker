package me.krumbit.cookieclickerplugin.manager;

import me.krumbit.cookieclickerplugin.CookieClickerPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickerManager {
    private final CookieClickerPlugin cookieClickerPlugin;

    private Map<UUID, Integer> playerClicks = new HashMap<>();
    private Map<UUID, Boolean> hasClicker = new HashMap<>();

    public ClickerManager(CookieClickerPlugin cookieClickerPlugin) {
        this.cookieClickerPlugin = cookieClickerPlugin;
    }

    public Integer getPlayerClicks(UUID uuid) {
        return this.playerClicks.get(uuid);
    }

    public Boolean hasClickerPowerup(UUID uuid) {
        return this.hasClicker.get(uuid);
    }
    public void setPlayerClicks(UUID uuid, int clicks) {
        this.playerClicks.put(uuid, clicks);
    }

    public void addPlayerClicks(UUID uuid, int clicks) {
        setPlayerClicks(uuid, getPlayerClicks(uuid) + clicks);
    }

    public void removePlayerClicks(UUID uuid, int clicks) {
        setPlayerClicks(uuid, getPlayerClicks(uuid) - clicks);
    }

    public void setClickerPowerup(UUID uuid, Boolean bool) {
        this.hasClicker.put(uuid, bool);
    }
}
