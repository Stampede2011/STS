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
import java.util.function.Predicate;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.text.Text;

public class PredicateParser<T>
extends DelegateParser<T, T> {
    private final Predicate<T> predicate;

    public PredicateParser(Predicate<T> predicate, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        super(delegate, messages);
        this.predicate = predicate;
    }

    @Override
    public T parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        Object value = this.delegate.parseValue(src, args);
        try {
            if (this.predicate.test((T) value)) {
                return (T) value;
            }
        }
        catch (Exception e) {
            throw args.createError(this.getMessage("exception", "<exception>", "exception", e.getMessage()));
        }
        throw args.createError(this.getMessage("invalid-value", "The value <value> does not meet the requirements for this argument.", "value", value));
    }
}

