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

public class UISTS
{
    NBTTagCompound[] pokemon;
    Player player;
    int noPokemon;

    public UISTS(Player player, NBTTagCompound[] pokemon) {
        this.noPokemon = 0;
        this.pokemon = pokemon;
        this.player = player;
    }

    public void displayGUI() {
        Element redGlass = Element.of(ItemStack.builder()
                .itemType(ItemTypes.STAINED_GLASS_PANE)
                .add(Keys.DYE_COLOR, DyeColors.RED)
                .add(Keys.DISPLAY_NAME, Text.of(""))
                .build());
        Element blackGlass = Element.of(ItemStack.builder()
                .itemType(ItemTypes.STAINED_GLASS_PANE)
                .add(Keys.DYE_COLOR, DyeColors.BLACK)
                .add(Keys.DISPLAY_NAME, Text.of(""))
                .build());
        Element whiteGlass = Element.of(ItemStack.builder()
                .itemType(ItemTypes.STAINED_GLASS_PANE)
                .add(Keys.DYE_COLOR, DyeColors.WHITE)
                .add(Keys.DISPLAY_NAME, Text.of(""))
                .build());

        Layout.Builder layout = Layout.builder().set(redGlass, new int[] { 0, 1, 3, 4, 5, 7, 8 }).set(redGlass, new int[] { 2, 6 }).set(blackGlass, new int[] { 9, 13, 17 }).set(whiteGlass, new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 });
        int slot = 10;
        for (NBTTagCompound poke : this.pokemon) {

            if (poke == null || !(!poke.getBoolean("isEgg") || STSConfig.Boosters.allowEggs)) {

                ItemStack emptyItem = Sponge.getRegistry()
                        .createBuilder(ItemStack.Builder.class)
                        .itemType(STSConfig.GUI.Empty.itemType)
                        .quantity(1)
                        .build();

                emptyItem = Sponge.getRegistry()
                        .createBuilder(ItemStack.Builder.class)
                        .fromContainer(emptyItem.toContainer().set(DataQuery.of("UnsafeDamage"), STSConfig.GUI.Empty.unsafeDamage))
                        .build();

                emptyItem.offer(Keys.DISPLAY_NAME, Utilities.toText(STSConfig.GUI.Empty.displayName));

                if (!STSConfig.GUI.Empty.lore.isEmpty()) {
                    List<Text> loreEmpty = new ArrayList<>();

                    for (String line : STSConfig.GUI.Empty.lore)
                        loreEmpty.add(Utilities.toText(line));

                    emptyItem.offer(Keys.ITEM_LORE, loreEmpty);
                }

                layout.set(Element.of(emptyItem), slot);
            }
            else {

                this.noPokemon++;
                int price = Utilities.getPrice(poke);
                Consumer<Action.Click> action = a -> {

                    if (this.noPokemon == 1) {

                        this.player.sendMessage(Utilities.getMessage(STSConfig.Messages.lastPokemon));
                    } else {

                        UIConfirm con = new UIConfirm(this.player, poke, price);
                        con.displayGUI();
                    }
                };
                ItemStack p = Utilities.getPokemonItem(EnumSpecies.getFromNameAnyCase(poke.getString("Name")), poke.getInteger("Variant"), poke.getBoolean("isEgg"), poke.getBoolean("IsShiny"));
                ArrayList<Text> lore = new ArrayList<Text>();
                double total = 0.0D;
                for (IBooster booster : STS.boosters) {

                    if (booster.getMoney(poke) != 0) {
                        total += booster.getMoney(poke);
                        lore.add(Utilities.toText(STSConfig.GUI.Pokemon.loreBooster
                                .replace("%booster%", booster.getItemLore())
                                .replace("%price%", String.valueOf(booster.getMoney(poke)))
                        ));
                    }
                }
                if (poke.getBoolean("isEgg") && STSConfig.Boosters.eggModifier != 0) {
                    double perc = (STSConfig.Boosters.eggModifier / 100.0) * total;
                    total -= perc;
                    lore.add(Utilities.toText(STSConfig.GUI.Pokemon.loreBooster
                            .replace("%booster%", "Egg Modifier")
                            .replace("%price%", String.valueOf(-perc))
                    ));
                }
                lore.add(Text.EMPTY);
                lore.add(Utilities.toText(STSConfig.GUI.Pokemon.loreTotal
                        .replace("%total%", String.valueOf(Double.valueOf(total)))
                ));
                p.offer(Keys.ITEM_LORE, lore);
                p.offer(Keys.DISPLAY_NAME, Utilities.toText(STSConfig.GUI.Pokemon.displayName.replace("%pokemon%", poke.getString("Name"))));
                layout.set(Element.of(p, action), slot);
            }
            if (slot == 12) {
                slot += 2;
            } else {
                slot++;
            }
        }
        Layout lay = layout.build();
        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Utilities.toText(STSConfig.GUI.mainMenuTitle))).build((STS.getInstance()).container);
        view.define(lay);
        view.open(this.player);
    }
}
