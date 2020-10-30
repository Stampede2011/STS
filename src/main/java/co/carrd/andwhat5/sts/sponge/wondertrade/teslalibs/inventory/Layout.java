package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Element;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.spongepowered.api.item.inventory.property.InventoryDimension;

public class Layout {
    private final ImmutableMap<Integer, Element> elements;
    private final InventoryDimension dimension;

    private Layout(ImmutableMap<Integer, Element> elements, InventoryDimension dimension) {
        this.elements = elements;
        this.dimension = dimension;
    }

    public Element getElement(int index) {
        return this.elements.getOrDefault(index, Element.EMPTY);
    }

    public ImmutableMap<Integer, Element> getElements() {
        return this.elements;
    }

    public InventoryDimension getDimension() {
        return this.dimension;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final InventoryDimension DEFAULT = InventoryDimension.of((int)9, (int)6);
        private Map<Integer, Element> elements = Maps.newHashMap();
        private InventoryDimension dimension = DEFAULT;
        private int rows = 6;
        private int columns = 9;
        private int capacity = 54;

        public Builder dimension(InventoryDimension dimension) {
            this.dimension = dimension;
            this.rows = dimension.getRows();
            this.columns = dimension.getColumns();
            this.capacity = this.rows * this.columns;
            return this;
        }

        public Builder set(Element element, int index) {
            Preconditions.checkElementIndex((int)index, (int)this.capacity);
            this.elements.put(index, element);
            return this;
        }

        public Builder set(Element element, int ... indices) {
            for (int i : indices) {
                this.set(element, i);
            }
            return this;
        }

        public Builder setAll(Map<Integer, Element> elements) {
            for (Map.Entry<Integer, Element> entry : elements.entrySet()) {
                this.set(entry.getValue(), (int)entry.getKey());
            }
            return this;
        }

        public Builder row(Element element, int index) {
            return this.range(element, index * this.columns, (index + 1) * this.columns);
        }

        public Builder column(Element element, int index) {
            for (int i = index; i < this.capacity; i += this.columns) {
                this.set(element, i);
            }
            return this;
        }

        public Builder range(Element element, int lower, int upper) {
            for (int i = lower; i < upper; ++i) {
                this.set(element, i);
            }
            return this;
        }

        public Builder center(Element element) {
            return this.set(element, this.capacity / 2);
        }

        public Builder border(Element element) {
            int i;
            if (this.rows < 3 || this.columns < 3) {
                return this.range(element, 0, this.capacity);
            }
            for (i = 0; i <= this.columns; ++i) {
                this.set(element, i, this.capacity - i - 1);
            }
            for (i = 2 * this.columns; i < this.capacity - this.columns; i += this.columns) {
                this.set(element, i, i - 1);
            }
            return this;
        }

        public Builder checker(Element even, Element odd) {
            for (int i = 0; i < this.capacity; ++i) {
                this.set(i % 2 == 0 ? even : odd, i);
            }
            return this;
        }

        public Builder fill(Element element) {
            for (int i = 0; i < this.capacity; ++i) {
                if (this.elements.containsKey(i)) continue;
                this.set(element, i);
            }
            return this;
        }

        public Builder page(Collection<Element> elements) {
            int index = 0;
            for (Element element : elements) {
                while (this.elements.containsKey(index)) {
                    ++index;
                }
                this.set(element, index++);
            }
            return this;
        }

        public Builder replace(Element initial, Element replacement) {
            for (Map.Entry<Integer, Element> entry : this.elements.entrySet()) {
                if (entry.getValue() != initial) continue;
                entry.setValue(replacement);
            }
            return this;
        }

        public Builder overlay(Layout layout, int index) {
            Preconditions.checkElementIndex(index, this.capacity);
            int rows = layout.getDimension().getRows();
            int columns = layout.getDimension().getColumns();
            Preconditions.checkState((index % this.columns + columns < this.columns), (Object)"Layout overflows horizontally.");
            Preconditions.checkState((index / this.rows + rows < this.rows), (Object)"Layout overflows vertically.");
            for (int r = 0; r < rows; ++r) {
                for (int c = 0; c < columns; ++c) {
                    Element element = layout.elements.get((r * columns + c));
                    if (element == null) continue;
                    this.set(element, r * this.columns + index + c);
                }
            }
            return this;
        }

        public Builder from(Layout layout) {
            return this.reset().dimension(layout.getDimension()).setAll((Map<Integer, Element>)layout.getElements());
        }

        public Builder reset() {
            this.elements.clear();
            return this.dimension(DEFAULT);
        }

        public Layout build() {
            return new Layout(ImmutableMap.copyOf(this.elements), this.dimension);
        }
    }

}

