package me.pan_truskawka045.rplace;

import lombok.Getter;
import me.pan_truskawka045.rplace.command.RPlaceCommand;
import me.pan_truskawka045.rplace.cooldown.CooldownService;
import me.pan_truskawka045.rplace.inventory.InventoryManager;
import me.pan_truskawka045.rplace.listener.BlockedListener;
import me.pan_truskawka045.rplace.listener.ClickListener;
import me.pan_truskawka045.rplace.listener.JoinListener;
import me.pan_truskawka045.rplace.listener.PacketListener;
import me.pan_truskawka045.rplace.network.NatsConnection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RPlacePlugin extends JavaPlugin {

    private NatsConnection connection;

    private final BoardSettings boardSettings = new BoardSettings();
    private final BoardManager boardManager = new BoardManager(this);
    private final InventoryManager inventoryManager = new InventoryManager();
    private final CooldownService cooldownService = new CooldownService(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        String natsHost = getConfig().getString("nats.host");
        int natsPort = getConfig().getInt("nats.port");

        connection = new NatsConnection(natsHost, natsPort);

        if (!connection.connect()) {
            getLogger().severe("Failed to connect to NATS server");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        connection.setPacketConsumer(new PacketListener(this));
        boardSettings.load(getConfig());

        boardManager.clearBoard();

        registerCommandAndTabCompleter("rplace", new RPlaceCommand(this));

        World world = Bukkit.getWorlds().iterator().next();

        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0.5, 0.5);
        worldBorder.setSize(boardSettings.getSize() + 1);
        worldBorder.setWarningDistance(0);

        if (boardSettings.isAutoSave()) {
            world.setAutoSave(false);
        }

        Bukkit.getPluginManager().registerEvents(new BlockedListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(inventoryManager, this);

        if(Bukkit.getSpawnRadius() > 0){
            getLogger().warning("Spawn radius is set to " + Bukkit.getSpawnRadius() + " blocks. This may cause issues with the plugin. Please set it to 0 in server.properties");
        }
    }

    private void registerCommandAndTabCompleter(String cmd, Object executor) {
        if (!(executor instanceof TabCompleter) || !(executor instanceof CommandExecutor)) {
            throw new IllegalArgumentException("Executor must implement TabCompleter and CommandExecutor");
        }
        PluginCommand command = getCommand(cmd);
        if (command == null) {
            throw new IllegalArgumentException("Command not found");
        }
        command.setExecutor((CommandExecutor) executor);
        command.setTabCompleter((TabCompleter) executor);
    }
}
