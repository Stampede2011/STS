/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ClassToInstanceMap
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.MutableClassToInstanceMap
 *  com.google.inject.Inject
 *  com.google.inject.ProvisionException
 *  javax.annotation.Nullable
 *  javax.inject.Inject
 *  org.spongepowered.api.Sponge
 *  org.spongepowered.api.command.CommandCallable
 *  org.spongepowered.api.command.spec.CommandSpec
 *  org.spongepowered.api.plugin.PluginContainer
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Aliases;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Children;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Command;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Description;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Permission;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message.Message;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.inject.Inject;
import com.google.inject.ProvisionException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public class CommandService {
    private final PluginContainer container;
    private final ClassToInstanceMap<Command> instances = MutableClassToInstanceMap.create();

    private CommandService(PluginContainer container) {
        this.container = container;
    }

    public static CommandService of(PluginContainer container) {
        return new CommandService(container);
    }

    public <T extends Command> T getInstance(Class<T> clazz) throws ProvisionException {
        Command instance = (Command)this.instances.getInstance(clazz);
        if (instance == null) {
            try {
                Constructor<T> constructor = CommandService.getDeclaredConstructor(clazz, Command.Settings.class);
                if (constructor != null) {
                    if (!constructor.isAnnotationPresent(Inject.class) && !constructor.isAnnotationPresent(javax.inject.Inject.class)) {
                        throw new ProvisionException("Command.Settings constructor for class " + clazz.getName() + " must be annotated with @Inject (google/javax).");
                    }
                    Command.Settings settings = Command.Settings.of(this);
                    Optional.ofNullable(clazz.getAnnotation(Aliases.class)).ifPresent(a -> settings.aliases(a.value()));
                    Optional.ofNullable(clazz.getAnnotation(Children.class)).ifPresent(c -> settings.children(c.value()));
                    Optional.ofNullable(clazz.getAnnotation(Description.class)).ifPresent(d -> settings.description(Message.toText(d.value())));
                    Optional.ofNullable(clazz.getAnnotation(Permission.class)).ifPresent(p -> settings.permission(p.value()));
                    instance = (Command)constructor.newInstance(settings);
                    this.instances.put(clazz, instance);
                } else {
                    constructor = CommandService.getDeclaredConstructor(clazz, CommandService.class);
                    if (constructor != null) {
                        System.out.println("Notice: Class " + clazz.getName() + " is using a deprecated constructor in TeslaLibs' CommandService.");
                        if (!constructor.isAnnotationPresent(Inject.class) && !constructor.isAnnotationPresent(javax.inject.Inject.class)) {
                            throw new ProvisionException("CommandService constructor for class " + clazz.getName() + " must be annotated with @Inject (google/javax).");
                        }
                        instance = constructor.newInstance(this);
                    } else {
                        constructor = CommandService.getDeclaredConstructor(clazz, new Class[0]);
                        if (constructor != null) {
                            if (!(Modifier.isPublic(constructor.getModifiers()) || constructor.isAnnotationPresent(Inject.class) || constructor.isAnnotationPresent(javax.inject.Inject.class))) {
                                throw new ProvisionException("Empty constructor for class " + clazz.getName() + " must be public or annotated with @Inject (google/javax).");
                            }
                            instance = constructor.newInstance(new Object[0]);
                        } else {
                            throw new ProvisionException("Unable to retrieve a suitable constructor for class " + clazz.getName() + ".");
                        }
                    }
                }
                this.instances.put(clazz, instance);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new ProvisionException("Unable to instantiate class " + clazz.getName(), (Throwable)e);
            }
        }
        return (T)instance;
    }

    @Nullable
    private static <T> Constructor<T> getDeclaredConstructor(Class<T> clazz, Class ... params) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            return constructor;
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    public void register(Class<? extends Command> clazz) {
        Command command = this.getInstance(clazz);
        Sponge.getCommandManager().register((Object)this.container, (CommandCallable)command.getSpec(), command.getAliases());
    }

    @SafeVarargs
    public final void register(Class<? extends Command> ... classes) {
        for (Class<? extends Command> clazz : classes) {
            this.register(clazz);
        }
    }
}

