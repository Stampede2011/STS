package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.registry;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.registry.Registry;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.Tuple;

public class RegistryService<T> {
    private final Registry<T> registry;

    private RegistryService(Registry<T> registry) {
        this.registry = registry;
    }

    public static <T> RegistryService<T> of(Registry<T> registry) {
        return new RegistryService<T>(registry);
    }

    public Optional<Registry.Entry<T>> get(String id) {
        return this.registry.get(id);
    }

    public Optional<T> getValue(String id) {
        return this.registry.getValue(id);
    }

    public Optional<PluginContainer> getContainer(String id) {
        return this.registry.getContainer(id);
    }

    public Map<String, Tuple<T, PluginContainer>> getAll() {
        return Collections.unmodifiableMap(this.registry.getAll());
    }

    public Set<Tuple<T, PluginContainer>> getDistinct() {
        return Collections.unmodifiableSet(this.registry.getDistinct());
    }

    @Deprecated
    public boolean register(String id, T value, PluginContainer container) {
        return this.registry.register(id, value, container);
    }
}

