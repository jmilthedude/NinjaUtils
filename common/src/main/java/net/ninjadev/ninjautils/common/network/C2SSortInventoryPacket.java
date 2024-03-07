package net.ninjadev.ninjautils.common.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.ninjadev.ninjautils.common.util.SharedConstants;

public class C2SSortInventoryPacket implements FabricPacket {

    public static final PacketType<C2SSortInventoryPacket> TYPE = PacketType.create(SharedConstants.serverId("sort_inventory"), C2SSortInventoryPacket::new);

    private final int keyCode;

    public C2SSortInventoryPacket(PacketByteBuf buf) {
        this(buf.readInt());
    }

    public C2SSortInventoryPacket(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.keyCode);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
