package co.carrd.andwhat5.sts.ui;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import com.pixelmongenerations.core.enums.EnumSpecies;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;




public class UIConfirm
{
    private int price;
    private NBTTagCompound pokemon;
    private Player player;

    public UIConfirm(Player player, NBTTagCompound pokemon, int price) {
        this.price = price;
        this.pokemon = pokemon;
        this.player = player;
    }

    public void displayGUI() {
        Element blueGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element redGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.RED).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element blackGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element whiteGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.WHITE).add(Keys.DISPLAY_NAME, Text.of("")).build());

        ItemStack p = Utilities.getPokemonItem(EnumSpecies.getFromNameAnyCase(this.pokemon.getString("Name")), this.pokemon.getInteger("Variant"), this.pokemon.getBoolean("isEgg"), this.pokemon.getBoolean("IsShiny"));
        ArrayList<Text> lore = new ArrayList<Text>();
        double total = 0.0D;
        for (IBooster booster : STS.boosters) {
            if (booster.getMoney(this.pokemon) != 0) {
                total += booster.getMoney(this.pokemon);
                lore.add(Utilities.toText(STSConfig.GUI.Pokemon.loreBooster
                        .replace("%booster%", booster.getItemLore())
                        .replace("%price%", String.valueOf(booster.getMoney(this.pokemon)))
                ));
            }
        }
        lore.add(Text.EMPTY);
        lore.add(Utilities.toText(STSConfig.GUI.Pokemon.loreTotal
                .replace("%total%", String.valueOf(Double.valueOf(total)))
        ));
        p.offer(Keys.ITEM_LORE, lore);
        p.offer(Keys.DISPLAY_NAME, Utilities.toText(STSConfig.GUI.Pokemon.displayName.replace("%pokemon%", this.pokemon.getString("Name"))));
        Element pok = Element.of(p);

        Consumer<Action.Click> close = c -> {

            PlayerStorage s = (PlayerStorage)PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)this.player).orElse(null);
            if (s == null) {

                this.player.closeInventory();
            } else {

                UISTS ui = new UISTS(this.player, s.partyPokemon);
                ui.displayGUI();
            }
        };
        Consumer<Action.Click> sell = s -> {

            if (Utilities.sellPokemon(this.player, this.pokemon, this.price)) {
                STS.getLogger().info(this.player.getName() + " has sold a " + this.pokemon.getString("Name") + " for $" + this.price + "!");
                this.player.sendMessage(Utilities.getMessage(STSConfig.Messages.sellPokemon
                        .replace("%pokemon%", this.pokemon.getString("Name"))
                        .replace("%price%", String.valueOf(this.price))
                ));
                this.player.closeInventory();
            } else {
                this.player.sendMessage(Utilities.getMessage(STSConfig.Messages.missingPokemon
                        .replace("%pokemon%", this.pokemon.getString("Name"))
                ));
                this.player.closeInventory();
            }
        };

        ItemStack confirmItem = Sponge.getRegistry()
                .createBuilder(ItemStack.Builder.class)
                .itemType(STSConfig.GUI.Confirm.itemType)
                .quantity(1)
                .build();

        confirmItem = Sponge.getRegistry()
                .createBuilder(ItemStack.Builder.class)
                .fromContainer(confirmItem.toContainer().set(DataQuery.of("UnsafeDamage"), STSConfig.GUI.Confirm.unsafeDamage))
                .build();

        confirmItem.offer(Keys.DISPLAY_NAME, Utilities.toText(STSConfig.GUI.Confirm.displayName
                .replace("%pokemon%", this.pokemon.getString("Name"))
                .replace("%price%", String.valueOf(this.price))
        ));

        if (!STSConfig.GUI.Confirm.lore.isEmpty()) {
            List<Text> loreConfirm = new ArrayList<>();

            for (String line : STSConfig.GUI.Confirm.lore)
                loreConfirm.add(Utilities.toText(line
                        .replace("%pokemon%", this.pokemon.getString("Name"))
                        .replace("%price%", String.valueOf(this.price))
                ));

            confirmItem.offer(Keys.ITEM_LORE, loreConfirm);
        }


        ItemStack cancelItem = Sponge.getRegistry()
                .createBuilder(ItemStack.Builder.class)
                .itemType(STSConfig.GUI.Cancel.itemType)
                .quantity(1)
                .build();

        cancelItem = Sponge.getRegistry()
                .createBuilder(ItemStack.Builder.class)
                .fromContainer(cancelItem.toContainer().set(DataQuery.of("UnsafeDamage"), STSConfig.GUI.Cancel.unsafeDamage))
                .build();

        cancelItem.offer(Keys.DISPLAY_NAME, Utilities.toText(STSConfig.GUI.Cancel.displayName
                .replace("%pokemon%", this.pokemon.getString("Name"))
                .replace("%price%", String.valueOf(this.price))
        ));

        if (!STSConfig.GUI.Cancel.lore.isEmpty()) {
            List<Text> loreCancel = new ArrayList<>();

            for (String line : STSConfig.GUI.Cancel.lore)
                loreCancel.add(Utilities.toText(line
                        .replace("%pokemon%", this.pokemon.getString("Name"))
                        .replace("%price%", String.valueOf(this.price))
                ));

            cancelItem.offer(Keys.ITEM_LORE, loreCancel);
        }

        Element cancel = Element.of(cancelItem, close);
        Element confirm = Element.of(confirmItem, sell);

        Layout layout = Layout.builder()
                .set(redGlass, new int[] { 0, 1, 3, 4, 5, 7, 8 })
                .set(redGlass, new int[] { 2, 6 })
                .set(blackGlass, new int[] { 9, 17 })
                .set(whiteGlass, new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 })
                .set(pok, 13)
                .set(confirm, 11)
                .set(cancel, 15)
                .build();
        View view = View.builder()
                .archetype(InventoryArchetypes.CHEST)
                .property(InventoryTitle.of(Utilities.toText(STSConfig.GUI.confirmMenuTitle)))
                .build((STS.getInstance()).container);
        view.define(layout);
        view.open(this.player);
    }
}
