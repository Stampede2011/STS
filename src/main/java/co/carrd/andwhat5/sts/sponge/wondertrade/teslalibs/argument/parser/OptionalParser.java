/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.DelegateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.FunctionParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

public class OptionalParser<T>
extends DelegateParser<T, Optional<T>> {
    public OptionalParser(ValueParser<T> delegate, ImmutableMap<String, String> unused) {
        super(delegate, unused);
    }

    @Override
    public void parse(Text key, CommandSource src, CommandArgs args, CommandContext ctx) {
        ((Optional)this.parseValue(src, args)).ifPresent(value -> {
            if (value instanceof Iterable) {
                ((Iterable)value).forEach(v -> ctx.putArg(key, v));
            } else {
                ctx.putArg(key, value);
            }
        });
    }

    @Override
    public Optional<T> parseValue(CommandSource src, CommandArgs args) {
        if (args.hasNext()) {
            Object state = args.getState();
            try {
                return Optional.of(this.delegate.parseValue(src, args));
            }
            catch (ArgumentParseException e) {
                args.setState(state);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isOptional() {
        return true;
    }

    public FunctionParser<Optional<T>, T> orElse(T value) {
        return this.map(p -> p.orElse(value));
    }

    public FunctionParser<Optional<T>, T> orElseGet(Supplier<T> supplier) {
        return this.map(p -> p.orElseGet(supplier));
    }
}

