package me.krumbit.cookieclickerplugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.krumbit.cookieclickerplugin.CookieClickerPlugin;
import me.krumbit.cookieclickerplugin.util.CC;
import me.krumbit.cookieclickerplugin.util.ClickerInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("item")
public class ItemCommand extends BaseCommand {
    private final CookieClickerPlugin cookieClickerPlugin;

    public ItemCommand(CookieClickerPlugin cookieClickerPlugin) { this.cookieClickerPlugin = cookieClickerPlugin; }

    @Default
    public void giveItem(Player player) {
        player.sendMessage(CC.translate("&aEnjoy ur cool item :nerd:"));
        player.getInventory().addItem(getCookieItem());
    }

    public static ItemStack getCookieItem() {
        ItemStack krumbitCookie = new ItemStack(Material.COOKIE);
        ItemMeta cookieMeta = krumbitCookie.getItemMeta();
        cookieMeta.setDisplayName(CC.translate("&5&lKrumbit's &6&lCookie"));
        List<String> cookieLore = new ArrayList<String>();
        cookieLore.add(CC.translate("&7Krumbit's favorite chocolate chip cookie."));

        cookieMeta.setLore(cookieLore);
        krumbitCookie.setItemMeta(cookieMeta);
        ClickerInventory.makeShine(krumbitCookie);

        return krumbitCookie;
    }

}
