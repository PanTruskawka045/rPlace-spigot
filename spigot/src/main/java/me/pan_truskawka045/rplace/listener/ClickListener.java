package me.pan_truskawka045.rplace.listener;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.rplace.RPlacePlugin;
import me.pan_truskawka045.rplace.packet.PacketPlayerInteraction;
import me.pan_truskawka045.rplace.packet.PacketSetBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

@RequiredArgsConstructor
public class ClickListener implements Listener {

    private final RPlacePlugin rPlacePlugin;

    @EventHandler(priority = EventPriority.LOW)
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //PLACE
            event.setCancelled(true);

            if (rPlacePlugin.getCooldownService().isOnCooldown(player.getUniqueId())) {
                player.sendMessage(Component.text("You are on cooldown!").color(TextColor.color(0xFF4200)));
                return;
            }

            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null) {
                return;
            }

            int halfSize = rPlacePlugin.getBoardSettings().getSize() / 2;

            PlayerInventory inventory = player.getInventory();
            int x = clickedBlock.getX() + halfSize;
            int z = clickedBlock.getZ() + halfSize;
            Material type = inventory.getItemInMainHand().getType();

            rPlacePlugin.getConnection().sendPacket(new PacketSetBlock(x, z, (short) type.ordinal()));
            rPlacePlugin.getConnection().sendPacket(new PacketPlayerInteraction(player.getUniqueId()));
            rPlacePlugin.getCooldownService().putInteraction(player.getUniqueId());

            if (rPlacePlugin.getBoardSettings().isKickAfterPalce()) {
                player.kick(Component.text("You've placed a block! \nLet others participate in the event.").color(TextColor.color(0xC300FF)));
            }
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            rPlacePlugin.getInventoryManager().openBlockSelection(player);
        }
    }

}
