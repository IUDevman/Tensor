package dev.tensor.feature.modules;

import dev.tensor.backend.events.BlockInteractEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.util.InventoryUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

@Module.Info(name = "AutoTool", category = Category.Player)
public final class AutoTool extends Module {

    public final BooleanSetting swapBack = new BooleanSetting("Swap Back", true);

    private final HashMap<BlockPos, Integer> blockPosIntegerHashMap = new HashMap<>();

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<BlockInteractEvent> blockEventListener = new Listener<>(event -> {

        switch (event.getType()) {
            case Break: {
                if (swapBack.getValue() && blockPosIntegerHashMap.containsKey(event.getBlockPos()) && getInventory().selectedSlot != blockPosIntegerHashMap.get(event.getBlockPos())) {
                    InventoryUtil.INSTANCE.swap(blockPosIntegerHashMap.get(event.getBlockPos()));
                }

                if (blockPosIntegerHashMap.size() >= 10) blockPosIntegerHashMap.clear();
                break;
            }
            case Damage: {
                final int toolSlot = InventoryUtil.INSTANCE.findBestTool(getWorld().getBlockState(event.getBlockPos()));

                if (toolSlot != -1) {
                    blockPosIntegerHashMap.put(event.getBlockPos(), blockPosIntegerHashMap.getOrDefault(event.getBlockPos(), getInventory().selectedSlot));
                    InventoryUtil.INSTANCE.swap(toolSlot);
                }
                break;
            }
            default:
                break;
        }
    });
}
