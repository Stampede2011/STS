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
 *  org.spongepowered.api.entity.Entity
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.entity.living.player.User
 *  org.spongepowered.api.profile.GameProfile
 *  org.spongepowered.api.service.user.UserStorageService
 *  org.spongepowered.api.text.Text
 *  org.spongepowered.api.util.Identifiable
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.Arguments;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.FunctionParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.SelectorParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Identifiable;

public class UserParser
extends StandardParser<User> {
    public UserParser(ImmutableMap<String, String> messages) {
        super(messages);
    }

    @Override
    public User parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next();
        Player player = Sponge.getServer().getPlayer(arg).orElse(null);
        try {
            return player != null ? player : (User)((UserStorageService)Sponge.getServiceManager().provideUnchecked(UserStorageService.class)).get(arg).orElseThrow(() -> args.createError(this.getMessage("no-user", "No user found with name <arg>.", "arg", arg)));
        }
        catch (IllegalArgumentException e) {
            throw args.createError(this.getMessage("invalid-name", "Invalid name <arg>.", "arg", arg));
        }
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        return UserParser.complete(args, ((UserStorageService)Sponge.getServiceManager().provideUnchecked(UserStorageService.class)).getAll().stream().map(GameProfile::getName).filter(Optional::isPresent).map(Optional::get));
    }

    public OrSourceParser<User> orSource() {
        return Arguments.orSource(User.class::cast, this, ImmutableMap.of("exception", "Unable to parse user and source is not a User."));
    }

    public SelectorParser<User> selector() {
        return Arguments.selector(s -> s.filter(User.class::isInstance).map(User.class::cast), this, ImmutableMap.of());
    }

    public ValueParser<UUID> toUuid() {
        return this.map(Identifiable::getUniqueId);
    }
}

