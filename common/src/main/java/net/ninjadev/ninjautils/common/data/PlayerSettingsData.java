package net.ninjadev.ninjautils.common.data;

import net.minecraft.network.PacketByteBuf;

public class PlayerSettingsData implements PacketData {

    public static final PlayerSettingsData EMPTY = new PlayerSettingsData();

    public boolean sortInventoryEnabled;
    public boolean sortInventoryKeybind;

    public PlayerSettingsData() {}

    public PlayerSettingsData(PacketByteBuf buf) {
        this.read(buf);
    }

    public PlayerSettingsData setSortInventoryEnabled(boolean value) {
        this.sortInventoryEnabled = value;
        return this;
    }

    public PlayerSettingsData setSortInventoryKeybind(boolean value) {
        this.sortInventoryKeybind = value;
        return this;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.sortInventoryEnabled);
        buf.writeBoolean(this.sortInventoryKeybind);
    }

    @Override
    public <T extends PacketData> T read(PacketByteBuf buf) {
        this.sortInventoryEnabled = buf.readBoolean();
        this.sortInventoryKeybind = buf.readBoolean();
        return (T) this;
    }
}
