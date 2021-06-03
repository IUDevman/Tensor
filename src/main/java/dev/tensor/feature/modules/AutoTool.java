package dev.tensor.feature.modules;

import dev.tensor.backend.events.BlockInteractEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
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
    @EventTarget
    public void onBlockInteract(BlockInteractEvent event) {
        switch (event.getType()) {
            case Break: {
                if (swapBack.getValue() && blockPosIntegerHashMap.containsKey(event.getBlockPos()) && getInventory().selectedSlot != blockPosIntegerHashMap.get(event.getBlockPos())) {
                    this.swap(blockPosIntegerHashMap.get(event.getBlockPos()));
                }

                if (blockPosIntegerHashMap.size() >= 10) blockPosIntegerHashMap.clear();
                break;
            }
            case Damage: {
                final int toolSlot = findBestTool(getWorld().getBlockState(event.getBlockPos()));

                if (toolSlot != -1) {
                    blockPosIntegerHashMap.put(event.getBlockPos(), blockPosIntegerHashMap.getOrDefault(event.getBlockPos(), getInventory().selectedSlot));
                    this.swap(toolSlot);
                }
                break;
            }
            default:
                break;
        }
    }

    private int findBestTool(BlockState blockState) {
        int slot = -1;
        double maxMultiplier = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = getInventory().getStack(i);

            if (itemStack.isEmpty()) continue;

            float speedMultiplier = itemStack.getMiningSpeedMultiplier(blockState);
            final int efficiencyLevel = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, itemStack);

            if (speedMultiplier > 1) {
                speedMultiplier += efficiencyLevel > 0 ? Math.pow(efficiencyLevel, 2) + 1 : 0;

                if (speedMultiplier > maxMultiplier) {
                    maxMultiplier = speedMultiplier;
                    slot = i;
                }
            }
        }

        return slot;
    }
}
