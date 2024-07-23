package me.pan_truskawka045.rplace.command;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.rplace.RPlacePlugin;
import me.pan_truskawka045.rplace.packet.PacketClearBoard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class RPlaceCommand implements CommandExecutor, TabCompleter {

    private final RPlacePlugin rPlacePlugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("clear")) {
            rPlacePlugin.getConnection().sendPacket(new PacketClearBoard());
            commandSender.sendMessage("Board cleared");
            return true;
        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("clear");
        }
        return Collections.emptyList();
    }
}
