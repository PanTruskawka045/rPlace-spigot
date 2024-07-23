package me.pan_truskawka045.rplace.listener;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.rplace.RPlacePlugin;
import me.pan_truskawka045.rplace.packet.Packet;
import me.pan_truskawka045.rplace.packet.PacketClearBoard;
import me.pan_truskawka045.rplace.packet.PacketPlayerInteraction;
import me.pan_truskawka045.rplace.packet.PacketSetBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class PacketListener implements Consumer<Packet> {

    private final RPlacePlugin rPlacePlugin;

    @Override
    public void accept(Packet packet) {
        if (packet instanceof PacketSetBlock) {
            PacketSetBlock packetSetBlock = (PacketSetBlock) packet;
            Bukkit.getScheduler().runTask(rPlacePlugin, () -> rPlacePlugin.getBoardManager().setBlock(packetSetBlock.getX(), packetSetBlock.getZ(), Material.values()[packetSetBlock.getBlock()]));
            return;
        }
        if (packet instanceof PacketClearBoard) {
            Bukkit.getScheduler().runTask(rPlacePlugin, () -> rPlacePlugin.getBoardManager().clearBoard());
            return;
        }
        if (packet instanceof PacketPlayerInteraction) {
            PacketPlayerInteraction packetPlayerInteraction = (PacketPlayerInteraction) packet;
            rPlacePlugin.getCooldownService().putInteraction(packetPlayerInteraction.getUuid());
        }
    }
}
