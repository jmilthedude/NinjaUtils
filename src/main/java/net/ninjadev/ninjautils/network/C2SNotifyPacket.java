package net.ninjadev.ninjautils.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;
import net.ninjadev.ninjautils.util.Constants;

import java.util.UUID;

public record C2SNotifyPacket(UUID playerId) implements CustomPayload {

    public static final Id<C2SNotifyPacket> PACKET_ID = new Id<>(Constants.serverId("notify_server"));

    public static final PacketCodec<RegistryByteBuf, C2SNotifyPacket> PACKET_CODEC = Uuids.PACKET_CODEC.xmap(C2SNotifyPacket::new, C2SNotifyPacket::playerId).cast();

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
