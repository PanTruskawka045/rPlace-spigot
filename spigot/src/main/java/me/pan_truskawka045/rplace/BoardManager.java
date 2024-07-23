package me.pan_truskawka045.rplace;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public class BoardManager {

    private final RPlacePlugin rPlacePlugin;


    public void clearBoard() {
        int size = rPlacePlugin.getBoardSettings().getSize();
        int halfSize = size / 2;
        World world = rPlacePlugin.getServer().getWorld("world");

        if (world == null) {
            return;
        }

        for (int x = -halfSize; x <= (size - halfSize); x++) {
            for (int z = -halfSize; z <= (size - halfSize); z++) {
                Block blockAt = world.getBlockAt(x, 0, z);
                blockAt.setType(Material.WHITE_WOOL);
            }
        }

    }

    public void setBlock(int x, int z, Material value) {
        int size = rPlacePlugin.getBoardSettings().getSize();
        int halfSize = size / 2;
        World world = rPlacePlugin.getServer().getWorld("world");

        if (world == null) {
            return;
        }

        world.getBlockAt(x - halfSize, 0, z - halfSize).setType(value);

    }
}
