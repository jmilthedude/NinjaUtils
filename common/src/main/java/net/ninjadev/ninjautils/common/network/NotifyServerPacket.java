package net.ninjadev.ninjautils.common.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautils.common.util.SharedConstants;

import java.util.UUID;

public class NotifyServerPacket implements FabricPacket {

    public static final PacketType<NotifyServerPacket> TYPE = PacketType.create(SharedConstants.clientId("notify_server"), NotifyServerPacket::new);

    private final UUID playerId;

    public NotifyServerPacket(PacketByteBuf buf) {
        this(buf.readUuid());
    }

    public NotifyServerPacket(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(this.playerId);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
