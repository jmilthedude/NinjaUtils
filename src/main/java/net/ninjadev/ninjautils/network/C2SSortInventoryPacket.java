package net.ninjadev.ninjautils.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ninjadev.ninjautils.util.Constants;

public class C2SSortInventoryPacket implements CustomPayload {

    public static final CustomPayload.Id<C2SSortInventoryPacket> PACKET_ID = new CustomPayload.Id<>(Constants.serverId("sort_inventory"));

    public static final PacketCodec<RegistryByteBuf, C2SSortInventoryPacket> PACKET_CODEC = PacketCodec.of(C2SSortInventoryPacket::write, C2SSortInventoryPacket::new);

    private final int keyCode;

    public C2SSortInventoryPacket(RegistryByteBuf buf) {
        this(buf.readInt());
    }

    public C2SSortInventoryPacket(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void write(RegistryByteBuf buf) {
        buf.writeInt(this.keyCode);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
