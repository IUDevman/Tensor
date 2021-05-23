package dev.tensor.backend.events;

import dev.darkmagician6.eventapi.imp.EventCancellable;
import net.minecraft.util.math.BlockPos;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

public final class BlockInteractEvent extends EventCancellable {

    private final Type type;
    private final BlockPos blockPos;

    public BlockInteractEvent(Type type, BlockPos blockPos) {
        this.type = type;
        this.blockPos = blockPos;
    }

    public Type getType() {
        return this.type;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public enum Type {
        Damage,
        Break
    }
}
