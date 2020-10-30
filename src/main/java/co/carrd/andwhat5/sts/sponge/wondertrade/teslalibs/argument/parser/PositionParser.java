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
 *  org.spongepowered.api.entity.Entity
 *  org.spongepowered.api.text.Text
 *  org.spongepowered.api.util.blockray.BlockRay
 *  org.spongepowered.api.util.blockray.BlockRay$BlockRayBuilder
 *  org.spongepowered.api.util.blockray.BlockRayHit
 *  org.spongepowered.api.world.Locatable
 *  org.spongepowered.api.world.Location
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;

public class PositionParser
extends StandardParser<Vector3d> {
    public static final ImmutableList<String> MODIFIERS = ImmutableList.of("#me", "#self", "#first", "#target");

    public PositionParser(ImmutableMap<String, String> messages) {
        super(messages);
    }

    @Override
    public Vector3d parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        Optional<Vector3d> source;
        String arg = args.next();
        if (arg.startsWith("#")) {
            switch (arg.toLowerCase()) {
                case "#me": 
                case "#self": {
                    if (src instanceof Locatable) {
                        return ((Locatable)src).getLocation().getPosition();
                    }
                    throw args.createError(this.getMessage("not-locatable", "The use of <arg> requires the source to have a location.", "arg", arg));
                }
                case "#first": 
                case "#target": {
                    if (src instanceof Entity) {
                        return BlockRay.from((Entity)(src)).stopFilter(BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(), 1)).build().end().map(BlockRayHit::getPosition).orElseThrow(() -> args.createError(this.getMessage("no-target", "No block target found.")));
                    }
                    throw args.createError(this.getMessage("not-entity", "The use of ", arg, " requires the source to be an entity."));
                }
            }
            throw args.createError(this.getMessage("invalid-modifier", "No known modifier with the name <arg>.", "arg", arg));
        }
        Optional<Vector3d> optional = source = src instanceof Locatable ? Optional.of(((Locatable)src).getLocation().getPosition()) : Optional.empty();
        if (arg.contains(",")) {
            String[] split = arg.split(",");
            if (split.length == 3) {
                return this.parseVector(args, source, split[0], split[1], split[2]);
            }
            throw args.createError(this.getMessage("invalid-format", "Expected three position components in <arg>, received <size>.", "arg", arg, "size", split.length));
        }
        return this.parseVector(args, source, arg, args.next(), args.next());
    }

    private Vector3d parseVector(CommandArgs args, Optional<Vector3d> source, String xArg, String yArg, String zArg) throws ArgumentParseException {
        return new Vector3d(this.parseComponent(args, xArg, source.map(Vector3d::getX)), this.parseComponent(args, yArg, source.map(Vector3d::getY)), this.parseComponent(args, zArg, source.map(Vector3d::getZ)));
    }

    private double parseComponent(CommandArgs args, String arg, Optional<Double> optPos) throws ArgumentParseException {
        try {
            if (arg.startsWith("~")) {
                double relative = optPos.orElseThrow(() -> args.createError(this.getMessage("not-locatable", "The use of <arg> requires the source to have a location.", "arg", arg)));
                return arg.length() > 1 ? Double.parseDouble(arg.substring(1)) + relative : relative;
            }
            return Double.parseDouble(arg);
        }
        catch (NumberFormatException e) {
            throw args.createError(this.getMessage("invalid-number", "The argument <arg> is not a number: <exception>", "arg", arg, "exception", e.getMessage()));
        }
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return PositionParser.complete(args, MODIFIERS.stream());
    }

    public OrSourceParser<Vector3d> orSource() {
        return Arguments.orSource(s -> ((Locatable)s).getLocation().getPosition(), this, ImmutableMap.of("exception", "Unable to parse position and source does not have a location."));
    }
}

