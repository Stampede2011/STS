package co.carrd.andwhat5.sts.config;
import com.google.common.collect.Lists;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.util.List;

@ConfigSerializable
public class STSConfig {


    @Setting
    Messages messages = new Messages();

    @ConfigSerializable
    public static class Messages
    {
        @Setting
        public static String prefix = "&8[&a&lSTS&8] &f";

        @Setting
        public static String lastPokemon = "&cYou can't sell your last Pokemon!";

        @Setting
        public static String missingPokemon = "&cError selling %pokemon%. Is it still in your party?";

        @Setting
        public static String sellPokemon = "&aYou sold your %pokemon% for $%price%!";

        @Setting
        public static String reload = "&aPlugin has been reloaded!";

        @Setting
        public static String loadError = "&cCould not load your party! Please try again...";

    }

    @Setting(comment = "All available boosters to modify the price. Set to 0 to disable a booster.")
    Boosters boosters = new Boosters();

    @ConfigSerializable
    public static class Boosters
    {
        @Setting(comment = "The amount of money per level of the Pokemon.")
        public static int moneyPerLevel = 15;

        @Setting(comment = "The amount of money if the Pokemon is level 100.")
        public static int maxLevel = 1000;

        @Setting(comment = "The amount of money per % of the Pokemons ivs. Example: $5 for a 1% IV Pokemon, $10 for a 2% IV Pokemon.")
        public static int moneyPerIV = 5;

        @Setting(comment = "The amount of money per % of the Pokemons evs. Example: $5 for a 1% EV Pokemon, $10 for a 2% EV Pokemon.")
        public static int moneyPerEV = 5;

        @Setting(comment = "The amount of money given if the Pokemon has perfect IVs.")
        public static int perfectIVBooster = 1000;

        @Setting(comment = "The amount of money given if the Pokemon has perfect EVs.")
        public static int perfectEVBooster = 1000;

        @Setting(comment = "The amount of money given if the Pokemon has a Hidden Ability.")
        public static int hiddenAbilityBooster = 1000;

        @Setting(comment = "The amount of money given if the Pokemon is shiny.")
        public static int shinyBooster = 2000;

        @Setting(comment = "The amount of money given if the Pokemon is a legendary.")
        public static int legendaryBooster = 4000;

        @Setting(comment = "The amount of money given to the player if the Pokemon has a special texture.")
        public static int specialTextureBooster = 1000;
    }

    @Setting(comment = "Customize the look of the GUI.")
    GUI gui = new GUI();

    @ConfigSerializable
    public static class GUI
    {
        @Setting
        public static String mainMenuTitle = "&8Server Trade Station";

        @Setting
        public static String confirmMenuTitle = "&8Server Trade Confirm";

        @Setting(comment = "The amount of money given to the player if the Pokemon has a special texture.")
        Pokemon pokemonItem = new Pokemon();

        @ConfigSerializable
        public static class Pokemon
        {
            @Setting(comment = "The name of the item for each Pokemon. %pokemon% is replaced with the Pokemon name.")
            public static String displayName = "&6&l%pokemon%";

            @Setting(comment = "The format of each booster in the lore. %booster% is replaced with the Booster name and %price% is replaced with the price.")
            public static String loreBooster = "&a%booster%: $&b%price%";

            @Setting(comment = "The format of the total in the lore. %total% is replaced with the total of all Boosters.")
            public static String loreTotal = "&6Total: &b$%total%";
        }

        @Setting(comment = "The item displayed when there is no Pokemon in that slot")
        Empty emptyItem = new Empty();

        @ConfigSerializable
        public static class Empty
        {
            @Setting
            public static ItemType itemType = ItemTypes.AIR;

            @Setting
            public static int unsafeDamage = 0;

            @Setting
            public static String displayName = "&c&lNo Pokemon Found!";

            @Setting
            public static List<String> lore = Lists.newArrayList();
        }

        @Setting(comment = "The item displayed for confirming to sell a Pokemon.")
        Confirm confirmItem = new Confirm();

        @ConfigSerializable
        public static class Confirm
        {
            @Setting
            public static ItemType itemType = Sponge.getRegistry().getType(ItemType.class, "pixelmon:power_weight").get();

            @Setting
            public static int unsafeDamage = 0;

            @Setting(comment = "%pokemon% replaced with Pokemon name. %price% replaced with price of Pokemon.")
            public static String displayName = "&aSell %pokemon% for $%price%";

            @Setting(comment = "%pokemon% replaced with Pokemon name. %price% replaced with price of Pokemon.")
            public static List<String> lore = Lists.newArrayList();
        }

        @Setting(comment = "The item displayed for confirming to sell a Pokemon.")
        Cancel cancelItem = new Cancel();

        @ConfigSerializable
        public static class Cancel
        {
            @Setting
            public static ItemType itemType = Sponge.getRegistry().getType(ItemType.class, "pixelmon:power_bracer").get();

            @Setting
            public static int unsafeDamage = 0;

            @Setting(comment = "%pokemon% replaced with Pokemon name. %price% replaced with price of Pokemon.")
            public static String displayName = "&cCancel";

            @Setting(comment = "%pokemon% replaced with Pokemon name. %price% replaced with price of Pokemon.")
            public static List<String> lore = Lists.newArrayList();
        }

    }

}