/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  ninja.leaping.configurate.ConfigurationNode
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.configuration;

import ninja.leaping.configurate.ConfigurationNode;

public class ConfigurationException
extends RuntimeException {
    private final ConfigurationNode node;

    public ConfigurationException(ConfigurationNode node, String message) {
        super(message);
        this.node = node;
    }

    public ConfigurationException(ConfigurationNode node, String format, Object ... args) {
        this(node, String.format(format, args));
    }

    public ConfigurationException(ConfigurationNode node, Throwable cause) {
        super(cause);
        this.node = node;
    }

    public ConfigurationNode getNode() {
        return this.node;
    }
}

