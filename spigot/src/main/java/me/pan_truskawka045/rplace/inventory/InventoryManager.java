package me.pan_truskawka045.rplace.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryManager implements Listener {

    private static final Material[] materials = new Material[]{
            Material.PURPLE_WOOL,
            Material.MAGENTA_WOOL,
            Material.PINK_WOOL,
            Material.RED_WOOL,
            Material.ORANGE_WOOL,
            Material.YELLOW_WOOL,
            Material.LIME_WOOL,
            Material.GREEN_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.CYAN_WOOL,
            Material.BLUE_WOOL,
            Material.WHITE_WOOL,
            Material.LIGHT_GRAY_WOOL,
            Material.GRAY_WOOL,
            Material.BROWN_WOOL,
            Material.BLACK_WOOL
    };

    public void openBlockSelection(Player player) {
        int size = (int) Math.ceil(materials.length / 7f) + 2;

        Inventory inv = Bukkit.getServer().createInventory(new BlockSelectionInventoryHolder(), size * 9, Component.text("Select a block"));

        for (int i = 0; i < materials.length; i++) {

            ItemStack item = new ItemStack(materials[i]);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(Component.text("Click to select this color").color(TextColor.color(0xFFC600)));
            ItemStack selected = player.getInventory().getItem(0);
            if (selected != null && selected.getType() == materials[i]) {
                itemMeta.lore(Arrays.asList(Component.empty(), Component.text("Selected").color(TextColor.color(0x00FF00))));
            }

            item.setItemMeta(itemMeta);

            int row = i / 7 + 1;
            int column = i % 7 + 1;
            inv.setItem(row * 9 + column, item);
        }


        player.openInventory(inv);

    }

    public void giveItems(Player player) {
        giveItems(player, Material.WHITE_WOOL);
    }

    public void giveItems(Player player, Material material) {
        player.getInventory().clear();
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Right click to place, left click to select a block").color(TextColor.color(0xFFC600)).decorate(TextDecoration.BOLD));
        item.setItemMeta(itemMeta);

        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, item);
        }

    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof BlockSelectionInventoryHolder) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                giveItems((Player) event.getWhoClicked(), event.getCurrentItem().getType());
                event.getWhoClicked().closeInventory();
            }
        }
    }

    private static final class BlockSelectionInventoryHolder implements InventoryHolder {

        @Override
        public Inventory getInventory() {
            return null;
        }
    }

}


