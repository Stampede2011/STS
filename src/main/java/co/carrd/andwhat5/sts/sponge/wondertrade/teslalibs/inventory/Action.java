/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.event.item.inventory.ClickInventoryEvent
 *  org.spongepowered.api.event.item.inventory.InteractInventoryEvent
 *  org.spongepowered.api.item.inventory.Slot
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Element;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Slot;

public class Action<T extends InteractInventoryEvent> {
    private final T event;
    private final Player player;

    Action(T event, Player player) {
        this.event = event;
        this.player = player;
    }

    public T getEvent() {
        return this.event;
    }

    public Player getPlayer() {
        return this.player;
    }

    public static class Click<T extends ClickInventoryEvent>
    extends Action<T> {
        private final Element element;
        private final Slot slot;

        Click(T event, Player player, Element element, Slot slot) {
            super(event, player);
            this.element = element;
            this.slot = slot;
        }

        public Element getElement() {
            return this.element;
        }

        public Slot getSlot() {
            return this.slot;
        }
    }

}

