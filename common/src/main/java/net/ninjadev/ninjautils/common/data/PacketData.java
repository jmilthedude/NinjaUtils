package net.ninjadev.ninjautils.common.data;

import net.minecraft.network.PacketByteBuf;

public interface PacketData {
    void write(PacketByteBuf buf);
    <T extends PacketData> T read(PacketByteBuf buf);
}
