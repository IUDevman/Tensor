package dev.tensor.misc.imp;

import dev.tensor.Tensor;
import dev.tensor.backend.mixins.accessors.ChatHudAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

public interface Methods extends Global {

    String clientPrefix = Formatting.DARK_GRAY + "[" + Formatting.DARK_RED + Tensor.INSTANCE.MOD_NAME + Formatting.DARK_GRAY + "]";

    default void sendClientMessage(String message, boolean prefix) {
        this.getChatHud().addMessage(new LiteralText((prefix ? clientPrefix + " " : "") + Formatting.GRAY + message));
    }

    default void sendReplaceableClientMessage(String message, int id, boolean prefix) {
        ((ChatHudAccessor) this.getChatHud()).addReplaceable(new LiteralText((prefix ? clientPrefix + " " : "") + Formatting.GRAY + message), id);
    }

    default void removeReplaceableClientMessage(int id) {
        ((ChatHudAccessor) this.getChatHud()).removeReplaceable(id);
    }

    default int findBlock(Block block) {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = this.getInventory().getStack(i);

            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem)) continue;

            if (((BlockItem) itemStack.getItem()).getBlock().equals(block)) {
                slot = i;
                break;
            }
        }

        return slot;
    }

    default int findItem(Item item) {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = this.getInventory().getStack(i);

            if (itemStack.isEmpty()) continue;

            if (itemStack.getItem().equals(item)) {
                slot = i;
                break;
            }
        }

        return slot;
    }

    default void swap(int slot) {
        this.getInventory().selectedSlot = slot;
    }
}
