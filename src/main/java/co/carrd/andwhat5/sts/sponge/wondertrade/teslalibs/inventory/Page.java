/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  org.spongepowered.api.data.key.Key
 *  org.spongepowered.api.data.key.Keys
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.item.ItemType
 *  org.spongepowered.api.item.ItemTypes
 *  org.spongepowered.api.item.inventory.InventoryArchetype
 *  org.spongepowered.api.item.inventory.InventoryArchetypes
 *  org.spongepowered.api.item.inventory.InventoryProperty
 *  org.spongepowered.api.item.inventory.ItemStack
 *  org.spongepowered.api.item.inventory.ItemStack$Builder
 *  org.spongepowered.api.item.inventory.property.AbstractInventoryProperty
 *  org.spongepowered.api.item.inventory.property.InventoryCapacity
 *  org.spongepowered.api.item.inventory.property.InventoryDimension
 *  org.spongepowered.api.plugin.PluginContainer
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Action;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Displayable;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Element;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Layout;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.View;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.AbstractInventoryProperty;
import org.spongepowered.api.item.inventory.property.InventoryCapacity;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public class Page
implements Displayable {
    public static final Element FIRST = Element.builder().build();
    public static final Element LAST = Element.builder().build();
    public static final Element NEXT = Element.builder().build();
    public static final Element PREVIOUS = Element.builder().build();
    public static final Element CURRENT = Element.builder().build();
    private final List<View> views = Lists.newArrayList();
    private final InventoryArchetype archetype;
    private final ImmutableList<InventoryProperty> properties;
    private final Layout layout;
    private final PluginContainer container;

    private Page(InventoryArchetype archetype, ImmutableList<InventoryProperty> properties, Layout layout, PluginContainer container) {
        this.archetype = archetype;
        this.properties = properties;
        this.layout = layout;
        this.container = container;
    }

    public static Page of(InventoryArchetype archetype, Layout layout, PluginContainer container) {
        return Page.builder().archetype(archetype).layout(layout).build(container);
    }

    public Page define(List<Element> elements) {
        this.views.clear();
        int capacity = this.archetype.getProperty(InventoryCapacity.class).map(AbstractInventoryProperty::getValue).orElse(this.layout.getDimension().getRows() * this.layout.getDimension().getColumns()) - this.layout.getElements().size();
        int pages = elements.isEmpty() ? 1 : (elements.size() - 1) / capacity + 1;
        for (int i = 1; i <= pages; ++i) {
            View.Builder builder = View.builder().archetype(this.archetype);
            this.properties.forEach(builder::property);
            this.views.add(builder.build(this.container).define(Layout.builder().from(this.layout).replace(FIRST, this.createElement("First Page", i, 1)).replace(LAST, this.createElement("Last Page", i, pages)).replace(NEXT, this.createElement("Next Page", i, i == pages ? i : i + 1)).replace(PREVIOUS, this.createElement("Previous Page", i, i == 1 ? i : i - 1)).replace(CURRENT, this.createElement("Current Page", i, i)).page(elements.subList((i - 1) * capacity, i == pages ? elements.size() : i * capacity)).build()));
        }
        return this;
    }

    private Element createElement(String name, int page, int target) {
        ItemStack item = ItemStack.builder().itemType(page == target ? ItemTypes.MAP : ItemTypes.PAPER).add(Keys.DISPLAY_NAME, Text.of(name, " (", target, ")")).quantity(target).build();
        return page == target ? Element.of(item) : Element.of(item, a -> this.open(a.getPlayer(), target));
    }

    public void open(Player player, int page) {
        this.views.get(page > 1 ? Math.min(page, this.views.size()) - 1 : 0).open(player);
    }

    @Override
    public void open(Player player) {
        this.views.get(0).open(player);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private InventoryArchetype archetype = InventoryArchetypes.DOUBLE_CHEST;
        private List<InventoryProperty> properties = Lists.newArrayList();
        private Layout layout;

        public Builder archetype(InventoryArchetype archetype) {
            this.archetype = archetype;
            return this;
        }

        public Builder property(InventoryProperty property) {
            this.properties.add(property);
            return this;
        }

        public Builder layout(Layout layout) {
            this.layout = layout;
            return this;
        }

        public Page build(PluginContainer container) {
            Preconditions.checkState((this.layout != null), "layout");
            return new Page(this.archetype, ImmutableList.copyOf(this.properties), this.layout, container);
        }
    }

}

