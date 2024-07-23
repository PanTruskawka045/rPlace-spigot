package me.pan_truskawka045.rplace;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class BoardSettings {

    private int size;
    private boolean autoSave;
    private int cooldown;
    private boolean kickAfterPalce;

    public void load(FileConfiguration config) {
        this.size = config.getInt("board.size");
        this.autoSave = config.getBoolean("world.disable-auto-save");
        this.cooldown = config.getInt("board.cooldown");
        this.kickAfterPalce = config.getBoolean("board.kick-after-place");
    }
}
