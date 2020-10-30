/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.Sponge
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.world.Locatable
 *  org.spongepowered.api.world.Location
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;

public class CommandParser
extends StandardParser<String> {
    public CommandParser(ImmutableMap<String, String> unused) {
        super(unused);
    }

    @Override
    public String parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        return Arguments.remainingStrings().parseValue(src, args);
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        try {
            return ImmutableList.copyOf(Sponge.getCommandManager().getSuggestions(src, Arguments.remainingStrings().parseValue(src, args), src instanceof Locatable ? ((Locatable)src).getLocation() : null));
        }
        catch (ArgumentParseException argumentParseException) {
            return ImmutableList.of();
        }
    }
}

