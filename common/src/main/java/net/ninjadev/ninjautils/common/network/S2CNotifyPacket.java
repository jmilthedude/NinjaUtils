package net.ninjadev.ninjautils.common.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautils.common.util.SharedConstants;

public class S2CNotifyPacket implements FabricPacket {

    public static final PacketType<S2CNotifyPacket> TYPE = PacketType.create(SharedConstants.serverId("notify_client"), S2CNotifyPacket::new);

    private final boolean acknowledge;

    public S2CNotifyPacket(PacketByteBuf buf) {
        this(buf.readBoolean());
    }

    public S2CNotifyPacket(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public boolean acknowledged() {
        return acknowledge;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.acknowledge);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
