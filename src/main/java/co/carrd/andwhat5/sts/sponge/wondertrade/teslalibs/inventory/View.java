/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.event.cause.Cause
 *  org.spongepowered.api.event.item.inventory.ClickInventoryEvent
 *  org.spongepowered.api.event.item.inventory.InteractInventoryEvent
 *  org.spongepowered.api.event.item.inventory.InteractInventoryEvent$Close
 *  org.spongepowered.api.item.inventory.Inventory
 *  org.spongepowered.api.item.inventory.Inventory$Builder
 *  org.spongepowered.api.item.inventory.InventoryArchetype
 *  org.spongepowered.api.item.inventory.InventoryProperty
 *  org.spongepowered.api.item.inventory.ItemStack
 *  org.spongepowered.api.item.inventory.ItemStackSnapshot
 *  org.spongepowered.api.item.inventory.Slot
 *  org.spongepowered.api.item.inventory.property.SlotIndex
 *  org.spongepowered.api.item.inventory.query.QueryOperation
 *  org.spongepowered.api.item.inventory.query.QueryOperationType
 *  org.spongepowered.api.item.inventory.query.QueryOperationTypes
 *  org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult
 *  org.spongepowered.api.item.inventory.transaction.SlotTransaction
 *  org.spongepowered.api.plugin.PluginContainer
 *  org.spongepowered.api.scheduler.Task
 *  org.spongepowered.api.scheduler.Task$Builder
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation.Animatable;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Action;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Displayable;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Element;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Layout;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperation;
import org.spongepowered.api.item.inventory.query.QueryOperationType;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

public class View
implements Animatable<Layout>,
Displayable {
    private final Inventory inventory;
    private final Map<Integer, Element> slots = Maps.newHashMap();
    private final Consumer<Action<InteractInventoryEvent.Close>> closeAction;
    private final PluginContainer container;

    private View(Inventory.Builder builder, Consumer<Action<InteractInventoryEvent.Close>> closeAction, PluginContainer container) {
        this.inventory = builder.listener(ClickInventoryEvent.class, this::processClick).listener(InteractInventoryEvent.Close.class, this::processClose).build(container);
        this.closeAction = closeAction;
        this.container = container;
    }

    public static View of(InventoryArchetype archetype, PluginContainer container) {
        return View.builder().archetype(archetype).build(container);
    }

    @Override
    public void open(Player player) {
        Task.builder().execute(t -> player.openInventory(this.inventory)).delayTicks(1L).submit(this.container);
    }

    public View define(Layout layout) {
        this.slots.clear();
        for (int i = 0; i < this.inventory.capacity(); ++i) {
            this.setElement(i, layout.getElement(i));
        }
        return this;
    }

    public View update(Layout layout) {
        layout.getElements().forEach(this::setElement);
        return this;
    }

    public void setElement(int index, Element element) {
        this.inventory.query(new QueryOperation[]{QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(index))}).first().set(element.getItem().createStack());
        this.slots.put(index, element);
    }

    private void processClick(ClickInventoryEvent event) {
        event.setCancelled(true);
        event.getCause().first(Player.class).ifPresent(p -> event.getTransactions().forEach(t -> t.getSlot().getProperty(SlotIndex.class, "slotindex").ifPresent(i -> {
            Element element = this.slots.get(i.getValue());
            if (element != null) {
                element.process(new Action.Click<>(event, p, element, t.getSlot()));
            }
        })));
    }

    private void processClose(InteractInventoryEvent.Close event) {
        event.getCause().first(Player.class).ifPresent(p -> this.closeAction.accept(new Action<>(event, (Player)p)));
    }

    @Override
    public void nextFrame(Layout frame) {
        this.update(frame);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final Consumer<Action<InteractInventoryEvent.Close>> NONE = a -> {};
        private Inventory.Builder builder = Inventory.builder();
        private Consumer<Action<InteractInventoryEvent.Close>> closeAction = NONE;

        public Builder archetype(InventoryArchetype archetype) {
            this.builder.of(archetype);
            return this;
        }

        public Builder property(InventoryProperty property) {
            this.builder.property(property);
            return this;
        }

        public Builder onClose(Consumer<Action<InteractInventoryEvent.Close>> action) {
            this.closeAction = action;
            return this;
        }

        public View build(PluginContainer container) {
            return new View(this.builder, this.closeAction, container);
        }
    }

}

