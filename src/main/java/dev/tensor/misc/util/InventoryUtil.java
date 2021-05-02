package dev.tensor.misc.util;

import dev.tensor.misc.imp.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

public enum InventoryUtil implements Wrapper {

    INSTANCE;

    public int findBlock(Block block) {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = getInventory().getStack(i);

            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem)) continue;

            if (((BlockItem) itemStack.getItem()).getBlock().is(block)) {
                slot = i;
                break;
            }
        }

        return slot;
    }

    public int findItem(Item item) {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = getInventory().getStack(i);

            if (itemStack.isEmpty()) continue;

            if (itemStack.getItem().equals(item)) {
                slot = i;
                break;
            }
        }

        return slot;
    }

    public int findBestTool(BlockState blockState) {
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

    public void swap(int slot) {
        getInventory().selectedSlot = slot;
    }
}
