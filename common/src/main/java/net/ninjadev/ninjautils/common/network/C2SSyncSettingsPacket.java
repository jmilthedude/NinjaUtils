package net.ninjadev.ninjautils.common.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautils.common.data.PlayerSettingsData;
import net.ninjadev.ninjautils.common.util.SharedConstants;

public class C2SSyncSettingsPacket implements FabricPacket {

    public static final PacketType<C2SSyncSettingsPacket> TYPE = PacketType.create(SharedConstants.serverId("sync_settings"), C2SSyncSettingsPacket::new);

    private final PlayerSettingsData data;

    public C2SSyncSettingsPacket(PacketByteBuf buf) {
        this.data = new PlayerSettingsData(buf);
    }

    public C2SSyncSettingsPacket(PlayerSettingsData data) {
        this.data = data;
    }

    public PlayerSettingsData getPlayerSettings() {
        return data;
    }

    @Override
    public void write(PacketByteBuf buf) {
        this.data.write(buf);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
