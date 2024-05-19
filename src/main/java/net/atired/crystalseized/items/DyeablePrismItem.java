package net.atired.crystalseized.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.List;

public interface DyeablePrismItem {
    String TAG_COLOR = "color";
    String TAG_DISPLAY = "display";
    int DEFAULT_LEATHER_COLOR = 11111;
    default boolean hasCustomColor(ItemStack p_41114_) {
        CompoundTag $$1 = p_41114_.getTagElement("display");
        return $$1 != null && $$1.contains("color", 99);
    }
    default int getColor(ItemStack p_41122_) {
        CompoundTag $$1 = p_41122_.getTagElement("display");
        return $$1 != null && $$1.contains("color", 99) ? $$1.getInt("color") : 10511680;
    }
    default void clearColor(ItemStack p_41124_) {
        CompoundTag $$1 = p_41124_.getTagElement("display");
        if ($$1 != null && $$1.contains("color")) {
            $$1.remove("color");
        }

    }
    default void setColor(ItemStack p_41116_, int p_41117_) {
        p_41116_.getOrCreateTagElement("display").putInt("color", p_41117_);
    }

}
