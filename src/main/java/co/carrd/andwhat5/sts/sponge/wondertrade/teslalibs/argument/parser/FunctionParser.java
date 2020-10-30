/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.DelegateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.text.Text;

public class FunctionParser<T, R>
extends DelegateParser<T, R> {
    private final Function<T, R> function;

    public FunctionParser(Function<T, R> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        super(delegate, messages);
        this.function = function;
    }

    @Override
    public R parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        Object value = this.delegate.parseValue(src, args);
        try {
            return this.function.apply((T) value);
        }
        catch (Exception e) {
            throw args.createError(this.getMessage("exception", "Error modifying value <value>: <exception>", "value", value, "exception", e.getMessage()));
        }
    }
}

