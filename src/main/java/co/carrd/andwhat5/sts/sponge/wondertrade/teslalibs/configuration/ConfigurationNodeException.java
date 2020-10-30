/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  ninja.leaping.configurate.ConfigurationNode
 *  ninja.leaping.configurate.objectmapping.ObjectMappingException
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.configuration;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Deprecated
public class ConfigurationNodeException
extends ObjectMappingException {
    private final ConfigurationNode node;

    public ConfigurationNodeException(ConfigurationNode node, String message) {
        super(message);
        this.node = node;
    }

    public ConfigurationNodeException(ConfigurationNode node, String format, Object ... args) {
        this(node, String.format(format, args));
    }

    public ConfigurationNodeException(ConfigurationNode node, Throwable cause) {
        super(cause);
        this.node = node;
    }

    public ConfigurationNode getNode() {
        return this.node;
    }

    public Unchecked asUnchecked() {
        return new Unchecked(this);
    }

    public static class Unchecked
    extends RuntimeException {
        private Unchecked(ConfigurationNodeException exception) {
            super((Throwable)((Object)exception));
        }

        public synchronized ConfigurationNodeException getCause() {
            return (ConfigurationNodeException)((Object)super.getCause());
        }
    }

}

