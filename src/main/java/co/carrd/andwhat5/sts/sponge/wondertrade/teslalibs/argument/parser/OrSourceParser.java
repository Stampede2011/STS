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

public class OrSourceParser<T>
extends DelegateParser<T, T> {
    protected final Function<CommandSource, T> function;

    public OrSourceParser(Function<CommandSource, T> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        super(delegate, messages);
        this.function = function;
    }

    @Override
    public T parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        Object state = args.getState();
        try {
            return this.delegate.parseValue(src, args);
        }
        catch (ArgumentParseException e) {
            args.setState(state);
            try {
                return this.function.apply(src);
            }
            catch (Exception ex) {
                throw args.createError(this.getMessage("exception", "<exception>", "exception", ex.getMessage()));
            }
        }
    }

    @Override
    public boolean isOptional() {
        return true;
    }
}

