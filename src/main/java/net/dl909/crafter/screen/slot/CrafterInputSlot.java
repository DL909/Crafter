package net.dl909.crafter.screen.slot;

import net.dl909.crafter.screen.CrafterScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CrafterInputSlot
        extends Slot {
    private final CrafterScreenHandler crafterScreenHandler;

    public CrafterInputSlot(Inventory inventory, int index, int x, int y, CrafterScreenHandler crafterScreenHandler) {
        super(inventory, index, x, y);
        this.crafterScreenHandler = crafterScreenHandler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return !this.crafterScreenHandler.isSlotDisabled(this.id) && super.canInsert(stack);
    }


    @Override
    public void markDirty() {
        super.markDirty();
        this.crafterScreenHandler.onContentChanged(this.inventory);
    }
}

