package xyz.n7mn.dev.pingplugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Collection;

public class PingCommand implements CommandExecutor {

    private final Plugin plugin;

    public PingCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player;

        if (sender instanceof Player){
            player = (Player) sender;
        } else {
            if (args.length != 1){
                return true;
            }

            player = plugin.getServer().getPlayer(args[0]);
            if (player == null){
                return true;
            }
        }

        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");

            try {
                Object handle = player.getClass().getMethod("getHandle").invoke(player);
                int ping = (int) handle.getClass().getField("ping").get(handle);

                sender.sendMessage(ChatColor.AQUA + "" + player.getName() + "のPing: " + ping + "ms");
            } catch (Exception e){
                Method getPing = craftPlayerClass.getMethod("getPing");
                Integer ping = (Integer) getPing.invoke(player);

                sender.sendMessage(ChatColor.AQUA + "" + player.getName() + "のPing: " + ping + "ms");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
