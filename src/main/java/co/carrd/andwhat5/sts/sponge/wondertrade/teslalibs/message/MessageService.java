package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message.Message;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

public class MessageService {

    ClassLoader classLoader;
    String name;

    private final LoadingCache<Locale, ResourceBundle> cache = Caffeine.newBuilder().build(k -> ResourceBundle.getBundle(name, k , classLoader));

    private MessageService(ClassLoader loader, String name) {
        this.name = name;
        classLoader = loader;
    }

    public static MessageService of(ClassLoader loader, String name) {
        return new MessageService(loader, name);
    }

    public static MessageService of(Path path, String name) throws MalformedURLException {
        return MessageService.of(new URLClassLoader(new URL[]{path.toUri().toURL()}), name);
    }

    public static MessageService of(PluginContainer container, String name) {
        return MessageService.of(new URLClassLoader(new URL[]{Sponge.class.getClassLoader().getResource("assets/" + container.getId())}), name);
    }

    public ResourceBundle getBundle(Locale locale) {
        return this.cache.get(locale);
    }

    public Message get(String key, Locale locale) {
        ResourceBundle bundle = this.getBundle(locale);
        return Message.of(bundle.containsKey(key) ? bundle.getString(key) : key);
    }

    public void reload() {
        this.cache.invalidateAll();
    }
}

