/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.command.args.CommandElement
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.CommandElement;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

public class FlagsElement
extends org.spongepowered.api.command.args.CommandElement {
    private final ImmutableMap<ImmutableList<String>, org.spongepowered.api.command.args.CommandElement> flags;
    private final ImmutableMap<String, org.spongepowered.api.command.args.CommandElement> aliases;

    public FlagsElement(ImmutableMap<ImmutableList<String>, org.spongepowered.api.command.args.CommandElement> flags) {
        super(null);
        this.flags = flags;
        ImmutableMap.Builder builder = ImmutableMap.builder();
        flags.forEach((k, v) -> k.forEach(f -> builder.put((Object)("-" + f.toLowerCase()), v)));
        this.aliases = builder.build();
    }

    public void parse(CommandSource src, CommandArgs args, CommandContext ctx) throws ArgumentParseException {
        while (args.hasNext() && args.peek().startsWith("-")) {
            String[] split = args.next().split("=", 2);
            org.spongepowered.api.command.args.CommandElement element = (org.spongepowered.api.command.args.CommandElement)this.aliases.get((Object)split[0]);
            if (element == null) {
                throw args.createError(Text.of("Unknown flag ", split[0], "."));
            }
            if (split.length == 2) {
                args.insertArg(split[1]);
            }
            try {
                element.parse(src, args, ctx);
            }
            catch (ArgumentParseException e) {
                throw args.createError(Text.of((Object[])new Object[]{"Error parsing value for flag ", split[0], ": ", e.getText()}));
            }
        }
    }

    /*
     * WARNING - bad return control flow
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        ArrayList<ImmutableList<String>> unused = Lists.newArrayList(this.flags.keySet());
        try {
            while (args.hasNext() && args.peek().startsWith("-")) {
                String[] split = args.next().split("=", 2);
                org.spongepowered.api.command.args.CommandElement element = this.aliases.get(split[0].toLowerCase());
                if (element == null || split.length == 1 && !args.hasNext()) {
                    return unused.stream().flatMap(Collection::stream).map(s -> "-" + s).filter(s -> s.toLowerCase().startsWith(split[0].toLowerCase())).collect(ImmutableList.toImmutableList());
                }
                if (split.length == 2) {
                    args.insertArg(split[1]);
                }
                Object state = args.getState();
                try {
                    element.parse(src, args, ctx);
                    unused.removeIf(l -> l.contains(split[0].substring(1).toLowerCase()));
                }
                catch (ArgumentParseException e) {
                    args.setState(state);
                    String start = split.length == 2 ? split[0] + "=" : "";
                    return element.complete(src, args, ctx).stream().map(s -> start + s).collect(ImmutableList.toImmutableList());
                }
            }
        }
        catch (ArgumentParseException split) {
            // empty catch block
        }
        return args.nextIfPresent().map(String::toLowerCase).map(a -> unused.stream().flatMap(Collection::stream).map(s -> "-" + s).filter(s -> s.toLowerCase().startsWith((String)a)).collect(ImmutableList.toImmutableList())).orElse(ImmutableList.of());
    }

    public Text getUsage(CommandSource src) {
        ArrayList args = Lists.newArrayList();
        for (Map.Entry entry : this.flags.entrySet()) {
            args.add("[" + String.join((CharSequence)"|", (Iterable)entry.getKey()));
            Text usage = ((org.spongepowered.api.command.args.CommandElement)entry.getValue()).getUsage(src);
            if (!usage.isEmpty()) {
                args.add(": ");
                args.add(usage);
            }
            args.add("] ");
        }
        return Text.of((Object[])args.toArray());
    }

    @Deprecated
    protected Object parseValue(CommandSource src, CommandArgs args) {
        throw new UnsupportedOperationException("Attempted to parse a value from flags.");
    }

    public static class Builder {
        private static final ValueParser<Boolean> TRUE = (src, args) -> Boolean.TRUE;
        private Map<ImmutableList<String>, org.spongepowered.api.command.args.CommandElement> flags = Maps.newHashMap();

        public Builder flag(org.spongepowered.api.command.args.CommandElement element, String ... flags) {
            this.flags.put((ImmutableList<String>)Arrays.stream(flags).map(String::toLowerCase).collect(ImmutableList.toImmutableList()), element);
            return this;
        }

        public Builder flag(String ... flags) {
            return flags.length != 0 ? this.flag(TRUE.toElement(flags[0]), flags) : this;
        }

        public FlagsElement build() {
            return new FlagsElement((ImmutableMap<ImmutableList<String>, org.spongepowered.api.command.args.CommandElement>)ImmutableMap.copyOf(this.flags));
        }
    }

}

