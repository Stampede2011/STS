package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.User;

public class StringParser
extends StandardParser<String> {
    private final boolean remaining;

    public StringParser(boolean remaining, ImmutableMap<String, String> unused) {
        super(unused);
        this.remaining = remaining;
    }

    @Override
    public String parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        if (this.remaining) {
            StringBuilder sb = new StringBuilder(args.next());
            while (args.hasNext()) {
                sb.append(" ").append(args.next());
            }
            return sb.toString();
        }
        return args.next();
    }

    @Override
    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        List all = args.getAll();
        String arg = all.isEmpty() ? "" : ((String)all.get(all.size() - 1)).toLowerCase();
        return Sponge.getServer().getOnlinePlayers().stream().map(User::getName).filter(n -> n.toLowerCase().startsWith(arg)).collect(ImmutableList.toImmutableList());
    }
}

