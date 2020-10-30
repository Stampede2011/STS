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

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

public class ChoicesParser<T>
extends StandardParser<T> {
    private final Map<String, T> choices;

    public ChoicesParser(Map<String, T> choices, ImmutableMap<String, String> messages) {
        super(messages);
        this.choices = choices;
    }

    @Override
    public T parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next();
        T value = this.choices.get(arg.toLowerCase());
        if (value != null) {
            return value;
        }
        throw args.createError(this.getMessage("no-choice", "Input <arg> is not a valid choice.", "arg", arg));
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        return ChoicesParser.complete(args, this.choices.keySet().stream());
    }
}

