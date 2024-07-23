package me.pan_truskawka045.rplace.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PacketSetBlock extends Packet {

    private int x, z;
    private short block;

}
