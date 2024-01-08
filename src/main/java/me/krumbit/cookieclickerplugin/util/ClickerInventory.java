package me.krumbit.cookieclickerplugin.util;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.krumbit.cookieclickerplugin.CookieClickerPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClickerInventory implements InventoryProvider {
    private final CookieClickerPlugin cookieClickerPlugin;

    private ItemStack cookie;
    private ItemStack clicker;

    public ClickerInventory(CookieClickerPlugin cookieClickerPlugin) {
        this.cookieClickerPlugin = cookieClickerPlugin;
    }

    public void open(Player player) {
        SmartInventory.builder()
                .id("cookieMenu")
                .provider(this)
                .manager(CookieClickerPlugin.getInventoryManager())
                .size(3, 9)
                .title(CC.translate("&6&lCookie Clicker"))
                .build()
                .open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        int timesClicked = this.cookieClickerPlugin.getClickerManager().getPlayerClicks(player.getUniqueId());
        boolean hasClicker = this.cookieClickerPlugin.getClickerManager().hasClickerPowerup(player.getUniqueId());

        //Cookie
        cookie = new ItemStack(Material.COOKIE);
        ItemMeta cookieMeta = cookie.getItemMeta();
        cookieMeta.setDisplayName(CC.translate("&6&lCookie"));
        List<String> cookieLore = new ArrayList<String>();
        cookieLore.add(CC.translate("&7You have " + CC.GOLD + timesClicked + CC.GRAY + " cookies."));

        contents.fill(ClickableItem.empty(emptyGlass(7)));

        //Check times clicked and set cookie lore
        if(timesClicked >= 100 && timesClicked < 500) {
            cookieLore.add("");
            cookieLore.add(CC.translate("&e&lx2 Boost"));
            makeShine(cookie);

            contents.fillRect(0, 3, 2, 5, ClickableItem.empty(emptyGlass(4)));
        } else if (timesClicked >= 500) {
            cookieLore.add("");
            cookieLore.add(CC.translate("&a&lx4 Boost"));
            makeShine(cookie);

            contents.fillRect(0, 3, 2, 5, ClickableItem.empty(makeShine(emptyGlass(5))));
        }

        if (!hasClicker) {
            clicker = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta clickerMeta = clicker.getItemMeta();
            clickerMeta.setDisplayName(CC.translate("&cClicker"));
            List<String> clickerLore = new ArrayList<String>();
            clickerLore.add(CC.translate("&7Automatically bakes 1 cookie per second."));
            clickerLore.add("");
            clickerLore.add(CC.translate("&cUnlock for 100 cookies."));
            clickerMeta.setLore(clickerLore);
            clicker.setItemMeta(clickerMeta);
        } else {
            clicker = new ItemStack(Material.FEATHER);
            ItemMeta clickerMeta = clicker.getItemMeta();
            clickerMeta.setDisplayName(CC.translate("&aClicker"));
            List<String> clickerLore = new ArrayList<String>();
            clickerLore.add(CC.translate("&7Automatically bakes 1 cookie per second."));
            clickerLore.add("");
            clickerLore.add(CC.translate("&aUnlocked!"));
            clickerMeta.setLore(clickerLore);
            clicker.setItemMeta(clickerMeta);
            makeShine(clicker);
        }

        //Set cookie cookieMeta
        cookieMeta.setLore(cookieLore);
        cookie.setItemMeta(cookieMeta);

        //Set cookie slot
        contents.set(1, 4, ClickableItem.of(cookie, cookie -> {
            this.cookieClickerPlugin.getClickerManager().addPlayerClicks(player.getUniqueId(), getCookieMultiplier(this.cookieClickerPlugin.getClickerManager().getPlayerClicks(player.getUniqueId())));
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
            //contents.set(1, 4, ClickableItem.empty(this.cookie));
        }));

        //Set clicker slot
        contents.set(0, 8, ClickableItem.of(clicker, clicker -> {
            if (!hasClicker) {
                if (timesClicked < 100) {
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 0.5f);
                    player.sendMessage(CC.translate("&cYou don't have enough cookies to purchase that!"));
                } else {
                    this.cookieClickerPlugin.getClickerManager().setClickerPowerup(player.getUniqueId(), true);
                    this.cookieClickerPlugin.getClickerManager().removePlayerClicks(player.getUniqueId(), 100);

                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                    player.sendMessage(CC.translate("&aUnlocked the &lClicker &apower-up."));
                }
            }
        }));

        //Clicker power-up
        if (hasClicker) {
            int state = contents.property("state", 0);
            contents.setProperty("state", state + 1);

            if (state % 20 != 0) return;

            this.cookieClickerPlugin.getClickerManager().addPlayerClicks(player.getUniqueId(), getCookieMultiplier(this.cookieClickerPlugin.getClickerManager().getPlayerClicks(player.getUniqueId())));
        }
    }

    public int getCookieMultiplier(int currentClicks) {
        if (currentClicks >= 100 && currentClicks < 500) {
            return 2;
        } else if (currentClicks >= 500) {
            return 4;
        } else {
            return 1;
        }
    }

    public static ItemStack makeShine(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack emptyGlass(int color) {
        ItemStack emptyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
        ItemMeta glassMeta = emptyGlass.getItemMeta();
        glassMeta.setDisplayName(" ");
        emptyGlass.setItemMeta(glassMeta);
        return emptyGlass;
    }

}
