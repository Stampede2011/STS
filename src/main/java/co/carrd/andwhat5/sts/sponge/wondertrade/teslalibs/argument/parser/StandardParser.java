/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message.Message;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.text.Text;

public abstract class StandardParser<T>
implements ValueParser<T> {
    protected final ImmutableMap<String, String> messages;

    public StandardParser(ImmutableMap<String, String> messages) {
        this.messages = messages;
    }

    public Text getMessage(String key, String def, Object ... args) {
        return Message.of(this.messages.getOrDefault(key, def)).args(args).toText();
    }

    public static ImmutableList<String> complete(CommandArgs args, Stream<String> stream) {
        return args.nextIfPresent().map(String::toLowerCase).map(a -> stream.filter(s -> s.toLowerCase().startsWith((String)a))).orElse(stream).collect(ImmutableList.toImmutableList());
    }
}

