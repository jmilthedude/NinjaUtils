package net.ninjadev.ninjautils.event.impl;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.event.CancellableEvent;

public class BlockUseEvent extends CancellableEvent<BlockUseEvent.Data> {

    public static class Data extends CancellableEvent.Data {

        private final World world;
        private final BlockState state;
        private final BlockPos pos;
        private final PlayerEntity player;
        private final Hand hand;
        private final BlockHitResult hit;
        private ActionResult result;

        public Data(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            this.world = world;
            this.state = state;
            this.pos = pos;
            this.player = player;
            this.hand = hand;
            this.hit = hit;
        }

        public World getWorld() {
            return this.world;
        }

        public BlockState getState() {
            return this.state;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public PlayerEntity getPlayer() {
            return this.player;
        }

        public Hand getHand() {
            return this.hand;
        }

        public BlockHitResult getHit() {
            return this.hit;
        }

        public ActionResult getResult() {
            return this.result;
        }

        public void setResult(ActionResult result) {
            this.result = result;
        }
    }
}
