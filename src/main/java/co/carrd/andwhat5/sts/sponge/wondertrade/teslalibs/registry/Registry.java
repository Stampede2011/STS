package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.Tuple;

public class Registry<T> {
    private final Map<String, Entry<T>> registry = Maps.newHashMap();
    private final Set<Entry<T>> distinct = Sets.newHashSet();

    private Registry() {
    }

    public static <T> Registry<T> of() {
        return new Registry<T>();
    }

    public Optional<Entry<T>> get(String id) {
        return Optional.ofNullable(this.registry.get(id.toLowerCase()));
    }

    public Optional<T> getValue(String id) {
        return this.get(id).map(Tuple::getFirst);
    }

    public Optional<PluginContainer> getContainer(String id) {
        return this.get(id).map(Tuple::getSecond);
    }

    public Map<String, Entry<T>> getAll() {
        return this.registry;
    }

    public Set<Entry<T>> getDistinct() {
        return this.distinct;
    }

    public boolean register(String id, T value, PluginContainer container) {
        id = id.toLowerCase();
        Entry entry = new Entry(value, container);
        Preconditions.checkArgument((this.registry.putIfAbsent(container.getId() + ":" + id, entry) == null), ("The id " + container.getId() + ":" + id + " has already been registered"));
        this.distinct.add(entry);
        return this.registry.putIfAbsent(id, entry) == null;
    }

    public boolean unregister(Entry<T> entry) {
        return this.registry.values().removeAll(ImmutableList.of(entry));
    }

    public void clear() {
        this.registry.clear();
        this.distinct.clear();
    }

    public static class Entry<T>
    extends Tuple<T, PluginContainer> {
        private Entry(T value, PluginContainer container) {
            super(value, container);
        }
    }

}

