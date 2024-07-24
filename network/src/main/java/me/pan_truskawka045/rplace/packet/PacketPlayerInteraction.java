package me.pan_truskawka045.rplace.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PacketPlayerInteraction extends Packet {

    private UUID uuid;

}
