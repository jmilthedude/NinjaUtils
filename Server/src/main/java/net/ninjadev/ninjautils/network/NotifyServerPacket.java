package net.ninjadev.ninjautils.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NotifyServerPacket implements FabricPacket {

    public static final PacketType<NotifyServerPacket> TYPE = PacketType.create(new Identifier("ninjautilsclient", "notify_server"), NotifyServerPacket::new);

    private final int test;

    public NotifyServerPacket(PacketByteBuf buf) {
        this.test = buf.readInt();
    }

    public NotifyServerPacket(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
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
