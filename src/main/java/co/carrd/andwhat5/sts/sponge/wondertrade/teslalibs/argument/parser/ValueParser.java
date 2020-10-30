/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.CommandElement;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.FunctionParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OptionalParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.PredicateParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

@FunctionalInterface
public interface ValueParser<T> {
    public T parseValue(CommandSource var1, CommandArgs var2) throws ArgumentParseException;

    default public void parse(Text key, CommandSource src, CommandArgs args, CommandContext ctx) throws ArgumentParseException {
        T value = this.parseValue(src, args);
        if (value instanceof Iterable) {
            ((Iterable)value).forEach(v -> ctx.putArg(key, v));
        } else {
            ctx.putArg(key, value);
        }
    }

    default public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        return ImmutableList.of();
    }

    default public boolean isOptional() {
        return false;
    }

    default public <R> FunctionParser<T, R> map(Function<T, R> mapper) {
        return Arguments.function(mapper, this, ImmutableMap.of());
    }

    default public PredicateParser<T> filter(Predicate<T> predicate) {
        return Arguments.predicate(predicate, this, ImmutableMap.of());
    }

    default public OptionalParser<T> optional() {
        return Arguments.optional(this, ImmutableMap.of());
    }

    default public CommandElement<T> toElement(String key) {
        return new CommandElement<T>(Text.of(key), this);
    }
}

