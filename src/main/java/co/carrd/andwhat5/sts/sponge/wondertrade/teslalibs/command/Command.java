/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  org.spongepowered.api.Sponge
 *  org.spongepowered.api.command.CommandCallable
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.CommandElement
 *  org.spongepowered.api.command.source.ConsoleSource
 *  org.spongepowered.api.command.spec.CommandExecutor
 *  org.spongepowered.api.command.spec.CommandSpec
 *  org.spongepowered.api.command.spec.CommandSpec$Builder
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Aliases;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Children;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.CommandService;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Description;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command.Permission;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message.Message;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public abstract class Command
implements CommandExecutor {
    private final CommandSpec spec;
    private final ImmutableList<Command> children;
    private final ImmutableList<String> aliases;
    private final Optional<String> permission;
    private final Optional<Text> description;
    private final Text usage;

    @Deprecated
    protected Command(CommandService service, Settings settings) {
        this(settings.setService(service));
    }

    protected Command(Settings settings) {
        settings.setAnnotations(this.getClass());
        this.children = ImmutableList.copyOf((Collection)settings.children);
        this.aliases = ImmutableList.copyOf((Collection)settings.aliases);
        this.permission = Optional.ofNullable(settings.permission);
        this.description = Optional.ofNullable(settings.description);
        CommandSpec.Builder builder = CommandSpec.builder().executor((CommandExecutor)this);
        if (!settings.elements.isEmpty()) {
            builder.arguments((settings.elements.size() == 1 ? settings.elements.get(0) : Arguments.sequence(settings.elements.toArray(new CommandElement[0]))));
        }
        this.children.forEach(c -> builder.child((CommandCallable)c.getSpec(), c.getAliases()));
        this.permission.ifPresent(((CommandSpec.Builder)builder)::permission);
        this.description.ifPresent(((CommandSpec.Builder)builder)::description);
        this.spec = builder.build();
        this.usage = settings.usage != null ? settings.usage : this.spec.getUsage((CommandSource)Sponge.getServer().getConsole());
    }

    public CommandSpec getSpec() {
        return this.spec;
    }

    public ImmutableList<? extends Command> getChildren() {
        return this.children;
    }

    public ImmutableList<String> getAliases() {
        return this.aliases;
    }

    public Optional<String> getPermission() {
        return this.permission;
    }

    public Optional<Text> getDescription() {
        return this.description;
    }

    public Text getUsage() {
        return this.usage;
    }

    @Deprecated
    protected static Settings settings() {
        return new Settings(null);
    }

    protected static class Settings {
        private CommandService service;
        private final List<Command> children = Lists.newArrayList();
        private final List<String> aliases = Lists.newArrayList();
        private final List<CommandElement> elements = Lists.newArrayList();
        private String permission;
        private Text description;
        private Text usage;
        @Deprecated
        private boolean deprecated = false;

        protected Settings(CommandService service) {
            this.service = service;
        }

        public static Settings of(CommandService service) {
            return new Settings(service);
        }

        @Deprecated
        public Settings arguments(CommandElement ... elements) {
            return this.elements(elements);
        }

        public Settings elements(CommandElement ... elements) {
            Collections.addAll(this.elements, elements);
            return this;
        }

        public final Settings children(Command ... children) {
            Collections.addAll(this.children, children);
            return this;
        }

        @SafeVarargs
        public final Settings children(Class<? extends Command> ... children) {
            Collections.addAll(this.children, Arrays.stream(children).map(this.service::getInstance).toArray(x$0 -> new Command[x$0]));
            return this;
        }

        public Settings aliases(String ... aliases) {
            Collections.addAll(this.aliases, aliases);
            return this;
        }

        public Settings permission(String permission) {
            this.permission = permission;
            return this;
        }

        public Settings description(Text description) {
            this.description = description;
            return this;
        }

        public Settings usage(Text usage) {
            this.usage = usage;
            return this;
        }

        @Deprecated
        private Settings setService(CommandService service) {
            this.service = service;
            this.deprecated = true;
            return this;
        }

        @Deprecated
        private void setAnnotations(Class<? extends Command> clazz) {
            if (this.deprecated) {
                Optional.ofNullable(clazz.getAnnotation(Aliases.class)).ifPresent(a -> this.aliases(a.value()));
                Optional.ofNullable(clazz.getAnnotation(Children.class)).ifPresent(c -> this.children(c.value()));
                Optional.ofNullable(clazz.getAnnotation(Description.class)).ifPresent(d -> this.description(Message.toText(d.value())));
                Optional.ofNullable(clazz.getAnnotation(Permission.class)).ifPresent(p -> this.permission(p.value()));
            }
        }
    }

}

