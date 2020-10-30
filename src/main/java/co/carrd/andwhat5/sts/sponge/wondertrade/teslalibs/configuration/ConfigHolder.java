/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  ninja.leaping.configurate.ConfigurationNode
 *  ninja.leaping.configurate.loader.ConfigurationLoader
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.configuration;

import java.io.IOException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigHolder<T extends ConfigurationNode> {
    private final ConfigurationLoader loader;
    private final T node;

    public ConfigHolder(ConfigurationLoader<T> loader) throws IOException {
        this.loader = loader;
        this.node = loader.load();
    }

    public static <T extends ConfigurationNode> ConfigHolder<T> of(ConfigurationLoader<T> loader) throws IOException {
        return new ConfigHolder<T>(loader);
    }

    public T getNode(Object ... path) {
        return (T)this.node.getNode(path);
    }

    public boolean save() {
        try {
            this.loader.save(this.node);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

