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
 *  org.spongepowered.api.text.Text
 *  org.spongepowered.api.world.Locatable
 *  org.spongepowered.api.world.World
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.World;

public class WorldParser
extends StandardParser<World> {
    public WorldParser(ImmutableMap<String, String> messages) {
        super(messages);
    }

    @Override
    public World parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next();
        return Sponge.getServer().getWorld(arg).orElseThrow(() -> args.createError(this.getMessage("no-world", "No world found with name <arg>.", "arg", arg)));
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return WorldParser.complete(args, Sponge.getServer().getWorlds().stream().map(World::getName));
    }

    public OrSourceParser<World> orSource() {
        return Arguments.orSource(s -> ((Locatable)s).getWorld(), this, ImmutableMap.of("exception", "Unable to parse world and source does not have a location."));
    }
}

