/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.command.args.CommandElement
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

public class CommandElement<T>
extends org.spongepowered.api.command.args.CommandElement
implements ValueParser<T> {
    private final ValueParser<T> parser;

    public CommandElement(Text key, ValueParser<T> parser) {
        super(key);
        this.parser = parser;
    }

    public ValueParser<T> getParser() {
        return this.parser;
    }

    public void parse(CommandSource src, CommandArgs args, CommandContext ctx) throws ArgumentParseException {
        this.parser.parse(this.getKey(), src, args, ctx);
    }

    @Override
    public T parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        return this.parser.parseValue(src, args);
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        return this.parser.complete(src, args, ctx);
    }
}

