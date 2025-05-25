package ryk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class StopGracefullyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou must be OP to use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUsage: /stopserver <seconds>");
            return true;
        }

        int delaySeconds;
        try {
            delaySeconds = Integer.parseInt(args[0]);
            if (delaySeconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number of seconds: " + args[0]);
            sender.sendMessage("§cUsage: /stopserver <seconds>");
            return true;
        }

        int delayTicks = delaySeconds * 20;

        Bukkit.broadcastMessage("§eThe server will restart in §c" + delaySeconds + " §eseconds...");
        Bukkit.getScheduler().runTaskLater((Plugin) this, Bukkit::shutdown, delayTicks);
        return true;
    }
}

