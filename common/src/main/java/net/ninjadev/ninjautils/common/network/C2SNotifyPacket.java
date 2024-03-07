package net.ninjadev.ninjautils.common.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautils.common.util.SharedConstants;

import java.util.UUID;

public class C2SNotifyPacket implements FabricPacket {

    public static final PacketType<C2SNotifyPacket> TYPE = PacketType.create(SharedConstants.serverId("notify_server"), C2SNotifyPacket::new);

    private final UUID playerId;

    public C2SNotifyPacket(PacketByteBuf buf) {
        this(buf.readUuid());
    }

    public C2SNotifyPacket(UUID playerId) {
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
