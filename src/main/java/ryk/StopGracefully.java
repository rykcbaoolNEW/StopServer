package ryk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class StopGracefully extends JavaPlugin implements CommandExecutor {

    public static StopGracefully instance;

    @Override
    public void onEnable() {
        PluginCommand command = getCommand("stopserver");
        if (command != null) {
            command.setExecutor(this);
        } else {
            getLogger().severe("Command 'stopserver' not found in plugin.yml!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Require OP
        if (!sender.isOp()) {
            sender.sendMessage("§cYou must be OP to use this command.");
            return true;
        }

        // Require exactly one argument (delay in seconds)
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /stopserver <seconds>");
            return true;
        }

        int delaySeconds;
        try {
            delaySeconds = Integer.parseInt(args[0]);
            if (delaySeconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number: " + args[0]);
            return true;
        }

        int delayTicks = delaySeconds * 20;

        Bukkit.broadcastMessage("§eThe server will restart in §c" + delaySeconds + " §eseconds...");

        // Schedule shutdown AFTER delay
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.broadcastMessage("§cServer is stopping...");
            Bukkit.shutdown();
        }, delayTicks);

        return true;
    }
}



