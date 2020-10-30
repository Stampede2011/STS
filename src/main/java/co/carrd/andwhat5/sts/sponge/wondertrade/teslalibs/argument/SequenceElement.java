/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.command.args.CommandContext
 *  org.spongepowered.api.command.args.CommandElement
 *  org.spongepowered.api.command.args.GenericArguments
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.CommandElement;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class SequenceElement
extends org.spongepowered.api.command.args.CommandElement {
    private static final Class OPTIONAL = GenericArguments.optional((org.spongepowered.api.command.args.CommandElement)GenericArguments.seq((org.spongepowered.api.command.args.CommandElement[])new org.spongepowered.api.command.args.CommandElement[0])).getClass();
    private final ImmutableList<org.spongepowered.api.command.args.CommandElement> elements;

    public SequenceElement(ImmutableList<org.spongepowered.api.command.args.CommandElement> elements) {
        super(null);
        this.elements = elements;
    }

    public void parse(CommandSource src, CommandArgs args, CommandContext ctx) throws ArgumentParseException {
        for (org.spongepowered.api.command.args.CommandElement element : this.elements) {
            element.parse(src, args, ctx);
        }
    }

    public ImmutableList<String> complete(CommandSource src, CommandArgs args, CommandContext ctx) {
        HashSet completions = Sets.newHashSet();
        for (org.spongepowered.api.command.args.CommandElement element : this.elements) {
            Object state = args.getState();
            try {
                element.parse(src, args, ctx);
                if (state.equals(args.getState())) {
                    completions.addAll(element.complete(src, args, ctx));
                    args.setState(state);
                    continue;
                }
                if (args.hasNext()) {
                    completions.clear();
                    continue;
                }
                args.setState(state);
                completions.addAll(element.complete(src, args, ctx));
                if (!element.getClass().equals((Object)OPTIONAL) && (!(element instanceof CommandElement) || !((CommandElement)element).getParser().isOptional())) break;
                args.setState(state);
            }
            catch (ArgumentParseException ignored) {
                args.setState(state);
                completions.addAll(element.complete(src, args, ctx));
                break;
            }
        }
        return ImmutableList.copyOf((Collection)completions);
    }

    public Text getUsage(CommandSource src) {
        return Text.joinWith((Text)Text.of((String)" "), (Iterable)this.elements.stream().map(e -> e.getUsage(src)).collect(Collectors.toList()));
    }

    @Deprecated
    protected Object parseValue(CommandSource src, CommandArgs args) {
        throw new UnsupportedOperationException("Attempted to parse a value from sequence.");
    }
}

