package me.pan_truskawka045.rplace.cooldown;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.rplace.RPlacePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class CooldownService {

    private final Map<UUID, Long> lastInteractions = new HashMap<>();
    private final RPlacePlugin rPlacePlugin;

    public synchronized boolean isOnCooldown(UUID uuid) {
        return lastInteractions.getOrDefault(uuid, 0L) + rPlacePlugin.getBoardSettings().getCooldown() * 1000L > System.currentTimeMillis();
    }

    public synchronized void putInteraction(UUID uuid) {
        lastInteractions.put(uuid, System.currentTimeMillis());
    }

}
