package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.configuration;

import com.google.common.reflect.TypeToken;
import java.util.function.Consumer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class NodeUtils {
    public static <T extends ConfigurationNode> void ifVirtual(T node, Consumer<T> consumer) {
        if (node.isVirtual()) {
            consumer.accept(node);
        }
    }

    public static <T extends ConfigurationNode> void ifAttached(T node, Consumer<T> consumer) {
        if (!node.isVirtual()) {
            consumer.accept(node);
        }
    }

    public static boolean hasParent(ConfigurationNode node) {
        return node.getParent() != null;
    }

    public static <T extends ConfigurationNode> T getRoot(T node) {
        ConfigurationNode root = node;
        while (NodeUtils.hasParent(root)) {
            root = root.getParent();
        }
        return (T) root;
    }

    public static <T> T getOrDefault(ConfigurationNode node, TypeToken<T> token, T def) {
        Object value = def;
        try {
            value = node.getValue(token, def);
        }
        catch (ObjectMappingException objectMappingException) {
            // empty catch block
        }
        return (T) value;
    }

    public static ConfigurationNode copy(ConfigurationNode node) {
        return (node instanceof CommentedConfigurationNode ? SimpleCommentedConfigurationNode.root() : SimpleConfigurationNode.root()).setValue((Object)node);
    }

    public static void move(ConfigurationNode from, ConfigurationNode to) {
        to.setValue(from);
        from.setValue(null);
    }

    public static void tryComment(ConfigurationNode node, String comment) {
        if (node instanceof CommentedConfigurationNode) {
            ((CommentedConfigurationNode)node).setComment(comment);
        }
    }
}

