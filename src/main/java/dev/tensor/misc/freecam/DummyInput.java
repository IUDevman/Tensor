package dev.tensor.misc.freecam;

import net.minecraft.client.input.Input;

/**
 * @author wagyourtail#3826
 * Added with explicit permission, thanks! :D
 */

public final class DummyInput extends Input {

    public DummyInput() {
        this.movementForward = 0;
        this.movementSideways = 0;
        this.pressingForward = false;
        this.pressingBack = false;
        this.pressingLeft = false;
        this.pressingRight = false;
        this.jumping = false;
        this.sneaking = false;
    }
}
