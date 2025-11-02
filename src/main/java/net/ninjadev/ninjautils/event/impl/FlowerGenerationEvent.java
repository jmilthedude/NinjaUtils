package net.ninjadev.ninjautils.event.impl;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.ninjadev.ninjautils.event.Event;

public class FlowerGenerationEvent extends Event<FlowerGenerationEvent, FlowerGenerationEvent.Data> {

    public static class Data {

        private final StructureWorldAccess world;
        private final BlockPos pos;
        private BlockState state;

        private boolean stateChanged;

        public Data(StructureWorldAccess world, BlockPos pos, BlockState state) {
            this.world = world;
            this.pos = pos;
            this.state = state;
        }

        public StructureWorldAccess getWorld() {
            return world;
        }

        public BlockPos getPos() {
            return pos;
        }

        public BlockState getState() {
            return state;
        }

        public Data setState(BlockState state) {
            this.state = state;
            this.stateChanged = true;
            return this;
        }

        public boolean isStateChanged() {
            return stateChanged;
        }
    }
}
