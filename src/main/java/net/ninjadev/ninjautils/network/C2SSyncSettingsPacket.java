package net.ninjadev.ninjautils.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ninjadev.ninjautils.data.PlayerSettingsData;
import net.ninjadev.ninjautils.util.Constants;

public class C2SSyncSettingsPacket implements CustomPayload {

    public static final CustomPayload.Id<C2SSyncSettingsPacket> PACKET_ID = new CustomPayload.Id<>(Constants.serverId("sync_settings"));

    public static final PacketCodec<RegistryByteBuf, C2SSyncSettingsPacket> PACKET_CODEC = PacketCodec.of(C2SSyncSettingsPacket::write, C2SSyncSettingsPacket::new);

    private final PlayerSettingsData data;

    public C2SSyncSettingsPacket(RegistryByteBuf buf) {
        this.data = new PlayerSettingsData(buf);
    }

    public C2SSyncSettingsPacket(PlayerSettingsData data) {
        this.data = data;
    }

    public PlayerSettingsData getPlayerSettings() {
        return data;
    }

    public void write(RegistryByteBuf buf) {
        this.data.write(buf);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
