package net.ninjadev.ninjautils.data;

import net.minecraft.network.PacketByteBuf;

public interface PacketData {
    void write(PacketByteBuf buf);
    <T extends PacketData> T read(PacketByteBuf buf);
}
