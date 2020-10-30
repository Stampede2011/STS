/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Range
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.PredicateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import java.util.function.Function;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.text.Text;

public class NumberParser<T extends Number & Comparable>
extends StandardParser<T> {
    private final Function<String, T> function;

    public NumberParser(Function<String, T> function, ImmutableMap<String, String> messages) {
        super(messages);
        this.function = function;
    }

    @Override
    public T parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next();
        try {
            return (T)(this.function.apply(arg));
        }
        catch (NumberFormatException e) {
            throw args.createError(this.getMessage("invalid-number", "Unable to parse <arg> into a number.", "arg", arg));
        }
        catch (Exception e) {
            throw args.createError(this.getMessage("exception", "Error parsing input <arg>: <exception>", "arg", arg, "exception", e.getMessage()));
        }
    }

    public PredicateParser<T> inRange(Range<T> range) {
        return Arguments.predicate(range, this,ImmutableMap.of("invalid-value", ("Value <value> must be within range " + range + ".")));
    }
}

