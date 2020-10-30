/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.command.args.ArgumentParseException
 *  org.spongepowered.api.command.args.CommandArgs
 *  org.spongepowered.api.text.Text
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StandardParser;
import com.google.common.collect.ImmutableMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.text.Text;

public class DurationParser
extends StandardParser<Long> {
    public static final Pattern PATTERN = Pattern.compile("(?:([0-9]+)w)?(?:([0-9]+)d)?(?:([0-9]+)h)?(?:([0-9]+)m)?(?:([0-9]+)s)?(?:([0-9]+)ms)?");
    private static final long[] CONVERSIONS = new long[]{604800000L, 86400000L, 3600000L, 60000L, 1000L, 1L};

    public DurationParser(ImmutableMap<String, String> messages) {
        super(messages);
    }

    @Override
    public Long parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
        String arg = args.next();
        try {
            return Long.parseLong(arg);
        }
        catch (NumberFormatException ignored) {
            Matcher matcher = PATTERN.matcher(arg);
            if (matcher.matches()) {
                long time = 0L;
                for (int i = 1; i <= 6; ++i) {
                    time += matcher.group(i) != null ? (long)Integer.parseInt(matcher.group(i)) * CONVERSIONS[i - 1] : 0L;
                }
                return time;
            }
            throw args.createError(this.getMessage("invalid-format", "Input <arg> does not match the format of a duration.", "arg", arg));
        }
    }
}

