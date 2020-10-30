/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.entity.Entity
 *  org.spongepowered.api.text.Text
 *  org.spongepowered.api.text.selector.Selector
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.DelegateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.selector.Selector;

public class SelectorParser<T>
extends DelegateParser<T, Set<T>> {
    private Function<Stream<Entity>, Stream<T>> function;

    public SelectorParser(Function<Stream<Entity>, Stream<T>> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        super(delegate, messages);
        this.function = function;
    }

    @Override
    public Set<T> parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        if (args.hasNext() && args.peek().startsWith("@")) {
            String arg = args.next();
            try {
                return this.function.apply(Selector.parse((String)arg).resolve(src).stream()).collect(Collectors.toSet());
            }
            catch (IllegalArgumentException e) {
                throw args.createError(this.getMessage("invalid-selector", "The selector <arg> is not in the correct format: <exception>.", "arg", arg, "exception", e.getMessage()));
            }
            catch (Exception e) {
                throw args.createError(this.getMessage("exception", "<exception>", "exception", e.getMessage()));
            }
        }
        return Sets.newHashSet(this.delegate.parseValue(src, args));
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        Object state = args.getState();
        ImmutableList<String> completions = this.delegate.complete(src, args, ctx);
        args.setState(state);
        return ImmutableList.<String>builder().addAll(completions).addAll(Selector.complete(args.nextIfPresent().orElse(""))).build();
    }

    public OrSourceParser<Set<T>> orSource(Function<CommandSource, Set<T>> function) {
        return Arguments.orSource(function, this, ImmutableMap.of("exception", "Unable to parse selector or delegate and source is not of the proper type: <exception>"));
    }
}

