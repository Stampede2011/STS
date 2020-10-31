package co.carrd.andwhat5.sts.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class STSConfig {
    @Setting
    General general = new General();

    @ConfigSerializable
    public static class General {
        @Setting(comment="The amount of money per level of the Pokemon.")
        public static int moneyPerLevel = 15;
        @Setting(comment="The amount of money per % of the Pokemons iv. Example: $5 for a 1% IV Pokemon, $10 for a 2% IV Pokemon.")
        public static int moneyPerIV = 5;
        @Setting(comment="The amount of money given if the Pokemon has a Hidden Ability.")
        public static int hiddenAbilityBooster = 1000;
        @Setting(comment="The amount of money given if the Pokemon has perfect IVs.")
        public static int perfectIVBooster = 1000;
        @Setting(comment="The amount of money given if the Pokemon is shiny.")
        public static int shinyBooster = 2000;
        @Setting(comment="The amount of money given if the Pokemon is a legendary.")
        public static int legendaryBooster = 4000;
<<<<<<< Updated upstream
        @Setting(comment="The amount of money given to the player if the Pokemon has a custom texture.")
        public static int customTextureBooster = 1000;
        @Setting(comment="Allow the sales of eggs (Note: Players will be able to see the stats/type of the egg)")
        public static boolean allowEggs = false;
        @Setting(comment="The amount of money given if the Pokemon is an ultrabeast.")
        public static int ultraBeastBooster = 2500;
        @Setting(comment="The amount of money given if the Pokemon has perfect EVs.")
        public static int perfectEVBooster = 500;
        @Setting(comment="The amount of money given if the Pokemon is max level.")
        public static int MaxLevelBooster = 500;
=======

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
            public static String displayName = "&6%pokemon%";

            @Setting(comment = "The format of each booster in the lore. %booster% is replaced with the Booster name and %price% is replaced with the price.")
            public static String loreBooster = "&a%booster%: $&b%price%";

            @Setting(comment = "The format of the total in the lore. %total% is replaced with the total of all Boosters.")
            public static String loreTotal = "&aTotal: $&b%total%";
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
            public static String displayName = "";

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

//        @Setting(comment = "The amount of money given to the player if the Pokemon has a special texture.")
//        Border paneTop = new Border();
//
//        @Setting(comment = "The amount of money given to the player if the Pokemon has a special texture.")
//        Border paneMiddle = new Border();
//
//        @Setting(comment = "The amount of money given to the player if the Pokemon has a special texture.")
//        Border paneBottom = new Border();
//
//        @ConfigSerializable
//        public static class Border
//        {
//            @Setting
//            public static ItemType itemType = ItemTypes.STAINED_GLASS_PANE;
//
//            @Setting
//            public static int unsafeDamage = 0;
//
//            @Setting
//            public static String displayName = "";
//
//            @Setting
//            public static List<String> lore = Lists.newArrayList();
//        }

>>>>>>> Stashed changes
    }

}

