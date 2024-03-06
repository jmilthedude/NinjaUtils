package net.ninjadev.ninjautilsclient.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautilsclient.NinjaUtilsClient;

public class NotifyServerPacket implements FabricPacket {

    public static final PacketType<NotifyServerPacket> TYPE = PacketType.create(NinjaUtilsClient.id("notify_server"), NotifyServerPacket::new);

    private final int test;

    public NotifyServerPacket(PacketByteBuf buf) {
        this.test = buf.readInt();
    }

    public NotifyServerPacket(int test) {
        this.test = test;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.test);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
