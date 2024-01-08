package me.krumbit.cookieclickerplugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.krumbit.cookieclickerplugin.CookieClickerPlugin;
import me.krumbit.cookieclickerplugin.util.CC;
import me.krumbit.cookieclickerplugin.util.ClickerInventory;
import org.bukkit.entity.Player;


@CommandAlias("clicker")
public class ClickerCommand extends BaseCommand {
    private final CookieClickerPlugin cookieClickerPlugin;

    public ClickerCommand(CookieClickerPlugin cookieClickerPlugin) {
        this.cookieClickerPlugin = cookieClickerPlugin;
    }


    @Default
    public void openClickerInventory(Player player) {
        player.sendMessage(CC.translate("&aSuccessfully opened &6&lCookie Clicker"));
        new ClickerInventory(this.cookieClickerPlugin).open(player);
    }
}
