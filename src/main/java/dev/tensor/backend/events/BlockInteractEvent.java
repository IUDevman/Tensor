package dev.tensor.backend.events;

import dev.tensor.misc.imp.Event;
import net.minecraft.util.math.BlockPos;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

public final class BlockInteractEvent extends Event {

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
