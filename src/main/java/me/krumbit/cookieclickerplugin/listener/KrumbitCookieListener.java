package me.krumbit.cookieclickerplugin.listener;

import lombok.RequiredArgsConstructor;
import me.krumbit.cookieclickerplugin.CookieClickerPlugin;
import me.krumbit.cookieclickerplugin.command.ItemCommand;
import me.krumbit.cookieclickerplugin.util.CC;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class KrumbitCookieListener implements Listener {

    private final CookieClickerPlugin cookieClickerPlugin;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CraftPlayer craftPlayer = (CraftPlayer) player;

        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (!(player.getItemInHand().isSimilar(ItemCommand.getCookieItem()))) return;

        Bukkit.getScheduler().runTaskTimer(this.cookieClickerPlugin, () -> craftPlayer.getHandle().drop(CraftItemStack.asNMSCopy(craftPlayer.getItemInHand()), true), 0L, 1L);

    }
}
