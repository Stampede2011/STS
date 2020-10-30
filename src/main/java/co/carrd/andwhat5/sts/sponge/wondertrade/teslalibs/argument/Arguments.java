package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.FlagsElement;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.SequenceElement;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.CatalogTypeParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ChoicesParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.CommandParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.DateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.DurationParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.FunctionParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.LocationParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.NumberParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OptionalParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.OrSourceParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.PlayerParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.PositionParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.PredicateParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.SelectorParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.StringParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.UserParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.ValueParser;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.argument.parser.WorldParser;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.World;

public class Arguments {
    public static final ImmutableMap<String, Boolean> BOOLEANS = ImmutableMap.<String, Boolean>builder().put("true", Boolean.TRUE).put("t", Boolean.TRUE).put("1", Boolean.TRUE).put("false", Boolean.FALSE).put("f", Boolean.FALSE).put("0", Boolean.FALSE).build();
    public static final ImmutableMap<String, Tristate> TRISTATES = ImmutableMap.<String, Tristate>builder().put("true", Tristate.TRUE).put("t", Tristate.TRUE).put("1", Tristate.TRUE).put("false", Tristate.FALSE).put("f", Tristate.FALSE).put("0", Tristate.FALSE).put("undefined", Tristate.UNDEFINED).put("u", Tristate.UNDEFINED).put("1/2", Tristate.UNDEFINED).build();
    private static final StringParser STRING = Arguments.string(false, ImmutableMap.of());
    private static final StringParser REMAINING_STRINGS = Arguments.string(true, ImmutableMap.of());
    private static final NumberParser<Integer> INTEGER = Arguments.number(Integer::decode, ImmutableMap.of("invalid-format", "Expected <arg> to be an integer."));
    private static final NumberParser<Long> LONG = Arguments.number(Long::decode, ImmutableMap.of("invalid-format", "Expected <arg> to be a long (integer)."));
    private static final NumberParser<Float> FLOAT = Arguments.number(Float::valueOf, ImmutableMap.of("invalid-format", "Expected <arg> to be a float (decimal)."));
    private static final NumberParser<Double> DOUBLE = Arguments.number(Double::valueOf, ImmutableMap.of("invalid-format", "Expected <arg> to be a double (decimal)."));
    private static final ChoicesParser<Boolean> BOOLEAN = Arguments.choices(BOOLEANS, ImmutableMap.of("no-choice", "Expected <key> to be a boolean (true/false)."));
    private static final ChoicesParser<Tristate> TRISTATE = Arguments.choices(TRISTATES, ImmutableMap.of("no-choice", "Expected <key> to be a tristate (true/false/undefined)."));
    private static final PlayerParser PLAYER = Arguments.player(ImmutableMap.of());
    private static final SelectorParser<Player> PLAYER_SELECTOR = PLAYER.selector();
    private static final UserParser USER = Arguments.user(ImmutableMap.of());
    private static final SelectorParser<User> USER_SELECTOR = USER.selector();
    private static final WorldParser WORLD = Arguments.world(ImmutableMap.of());
    private static final PositionParser POSITION = Arguments.position(ImmutableMap.of());
    private static final LocationParser LOCATION = Arguments.location(WORLD.orSource(), POSITION, ImmutableMap.of());
    private static final CommandParser COMMAND = Arguments.command(ImmutableMap.of());
    private static final DateParser DATE = Arguments.date(ImmutableMap.of());
    private static final DurationParser DURATION = Arguments.duration(ImmutableMap.of());

    public static ValueParser<String> string() {
        return STRING;
    }

    public static ValueParser<String> remainingStrings() {
        return REMAINING_STRINGS;
    }

    public static NumberParser<Integer> intObj() {
        return INTEGER;
    }

    public static NumberParser<Long> longObj() {
        return LONG;
    }

    public static NumberParser<Double> doubleObj() {
        return DOUBLE;
    }

    public static NumberParser<Float> floatObj() {
        return FLOAT;
    }

    public static ChoicesParser<Boolean> booleanObj() {
        return BOOLEAN;
    }

    public static ChoicesParser<Tristate> tristate() {
        return TRISTATE;
    }

    public static PlayerParser player() {
        return PLAYER;
    }

    public static SelectorParser<Player> playerSelector() {
        return PLAYER_SELECTOR;
    }

    public static UserParser user() {
        return USER;
    }

    public static SelectorParser<User> userSelector() {
        return USER_SELECTOR;
    }

    public static WorldParser world() {
        return WORLD;
    }

    public static PositionParser position() {
        return POSITION;
    }

    public static LocationParser location() {
        return LOCATION;
    }

    public static CommandParser command() {
        return COMMAND;
    }

    public static DateParser date() {
        return DATE;
    }

    public static DurationParser duration() {
        return DURATION;
    }

    public static StringParser string(boolean remaining, ImmutableMap<String, String> unused) {
        return new StringParser(remaining, unused);
    }

    public static <T extends Number & Comparable> NumberParser<T> number(Function<String, T> function, ImmutableMap<String, String> messages) {
        return new NumberParser<T>(function, messages);
    }

    public static <T> ChoicesParser<T> choices(Map<String, T> choices, ImmutableMap<String, String> messages) {
        return new ChoicesParser<T>(choices, messages);
    }

    public static PlayerParser player(ImmutableMap<String, String> messages) {
        return new PlayerParser(messages);
    }

    public static UserParser user(ImmutableMap<String, String> messages) {
        return new UserParser(messages);
    }

    public static <T> OrSourceParser<T> orSource(Function<CommandSource, T> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        return new OrSourceParser<T>(function, delegate, messages);
    }

    public static WorldParser world(ImmutableMap<String, String> messages) {
        return new WorldParser(messages);
    }

    public static PositionParser position(ImmutableMap<String, String> messages) {
        return new PositionParser(messages);
    }

    public static LocationParser location(ValueParser<World> world, ValueParser<Vector3d> position, ImmutableMap<String, String> unused) {
        return new LocationParser(world, position, unused);
    }

    public static CommandParser command(ImmutableMap<String, String> messages) {
        return new CommandParser(messages);
    }

    public static DateParser date(ImmutableMap<String, String> messages) {
        return new DateParser(messages);
    }

    public static DurationParser duration(ImmutableMap<String, String> messages) {
        return new DurationParser(messages);
    }

    public static <T extends CatalogType> CatalogTypeParser<T> catalogType(Class<T> type, ImmutableMap<String, String> messages) {
        return new CatalogTypeParser<T>(type, messages);
    }

    public static <T> PredicateParser<T> predicate(Predicate<T> predicate, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        return new PredicateParser<T>(predicate, delegate, messages);
    }

    public static <T, R> FunctionParser<T, R> function(Function<T, R> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        return new FunctionParser<T, R>(function, delegate, messages);
    }

    public static <T> OptionalParser<T> optional(ValueParser<T> delegate, ImmutableMap<String, String> unused) {
        return new OptionalParser<T>(delegate, unused);
    }

    public static <T> SelectorParser<T> selector(Function<Stream<Entity>, Stream<T>> function, ValueParser<T> delegate, ImmutableMap<String, String> messages) {
        return new SelectorParser<T>(function, delegate, messages);
    }

    public static SequenceElement sequence(CommandElement ... elements) {
        return new SequenceElement(ImmutableList.copyOf(elements));
    }

    public static FlagsElement.Builder flags() {
        return new FlagsElement.Builder();
    }
}

