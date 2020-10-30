/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.flowpowered.math.vector.Vector3d
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.world.Locatable
 *  org.spongepowered.api.world.Location
 *  org.spongepowered.api.world.World
 *  org.spongepowered.api.world.extent.Extent
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class LocationParser
extends StandardParser<Location<World>> {
    private ValueParser<World> world;
    private ValueParser<Vector3d> position;

    public LocationParser(ValueParser<World> world, ValueParser<Vector3d> position, ImmutableMap<String, String> unused) {
        super(unused);
        this.world = world;
        this.position = position;
    }

    @Override
    public Location<World> parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        return new Location((Extent)this.world.parseValue(src, args), this.position.parseValue(src, args));
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        Object state = args.getState();
        try {
            this.world.parseValue(src, args);
            return this.position.complete(src, args, ctx);
        }
        catch (ArgumentParseException ignored) {
            args.setState(state);
            return this.world.complete(src, args, ctx);
        }
    }

    public OrSourceParser<Location<World>> orSource() {
        return Arguments.orSource(s -> ((Locatable)s).getLocation(), this, ImmutableMap.of("exception", "Unable to parse location and source does not have a location."));
    }
}

