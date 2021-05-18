package dev.tensor.misc.freecam;

import net.minecraft.client.input.Input;

/**
 * @author wagyourtail#3826
 * Added with explicit permission, thanks! :D
 */

public final class DummyInput extends Input {

    public DummyInput() {
        movementForward = 0;
        movementSideways = 0;
        pressingForward = false;
        pressingBack = false;
        pressingLeft = false;
        pressingRight = false;
        jumping = false;
        sneaking = false;
    }
}
