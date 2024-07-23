package me.pan_truskawka045.rplace.network;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Options;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.pan_truskawka045.rplace.packet.Packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class NatsConnection {

    private final String host;
    private final int port;

    @Setter
    private Consumer<Packet> packetConsumer = p -> {
    };
    private Connection connection;

    public boolean connect() {
        Options options = new Options.Builder()
                .server(host + ":" + port)
                .build();
        try {
            connection = Nats.connect(options);
            subscribe();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void subscribe() {
        Dispatcher dispatcher = this.connection.createDispatcher(message -> {
            try {
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(message.getData());
                ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                Object in = inputStream.readObject();
                if (!(in instanceof Packet)) {
                    return;
                }
                packetConsumer.accept((Packet) in);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        dispatcher.subscribe("packet");
    }

    public void sendPacket(Packet packet) {

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(packet);
            oos.close();

            connection.publish("packet", buffer.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


}
