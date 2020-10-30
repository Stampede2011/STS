/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.item.inventory.ItemStack
 *  org.spongepowered.api.item.inventory.ItemStackSnapshot
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Action;
import java.util.function.Consumer;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public class Element {
    public static final Element EMPTY = Element.builder().build();
    private final ItemStackSnapshot item;
    private final Consumer<Action.Click> clickAction;

    private Element(ItemStackSnapshot item, Consumer<Action.Click> clickAction) {
        this.item = item;
        this.clickAction = clickAction;
    }

    public static Element of(ItemStack item, Consumer<Action.Click> clickAction) {
        return Element.builder().item(item).onClick(clickAction).build();
    }

    public static Element of(ItemStack item) {
        return Element.builder().item(item).build();
    }

    public static Element of(ItemStackSnapshot item, Consumer<Action.Click> clickAction) {
        return Element.builder().item(item).onClick(clickAction).build();
    }

    public static Element of(ItemStackSnapshot item) {
        return Element.builder().item(item).build();
    }

    public ItemStackSnapshot getItem() {
        return this.item;
    }

    public void process(Action.Click action) {
        this.clickAction.accept(action);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final Consumer<Action.Click> NONE = a -> {};
        private ItemStackSnapshot item = ItemStackSnapshot.NONE;
        private Consumer<Action.Click> clickAction = NONE;

        public Builder item(ItemStackSnapshot item) {
            this.item = item;
            return this;
        }

        public Builder item(ItemStack item) {
            return this.item(item.createSnapshot());
        }

        public Builder onClick(Consumer<Action.Click> action) {
            this.clickAction = action;
            return this;
        }

        public Element build() {
            return new Element(this.item, this.clickAction);
        }
    }

}

