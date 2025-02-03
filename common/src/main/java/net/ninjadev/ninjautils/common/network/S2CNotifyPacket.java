package net.ninjadev.ninjautils.common.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ninjadev.ninjautils.common.util.SharedConstants;

public class S2CNotifyPacket implements CustomPayload {

    public static final Id<S2CNotifyPacket> PACKET_ID = new Id<>(SharedConstants.serverId("notify_client"));

    public static final PacketCodec<RegistryByteBuf, S2CNotifyPacket> PACKET_CODEC = PacketCodec.of(S2CNotifyPacket::write, S2CNotifyPacket::new);

    private final boolean acknowledge;

    public S2CNotifyPacket(RegistryByteBuf buf) {
        this(buf.readBoolean());
    }

    public S2CNotifyPacket(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public boolean acknowledged() {
        return acknowledge;
    }

    public void write(RegistryByteBuf buf) {
        buf.writeBoolean(this.acknowledge);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
