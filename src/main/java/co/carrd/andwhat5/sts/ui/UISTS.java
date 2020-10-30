package co.carrd.andwhat5.sts.ui;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import com.pixelmongenerations.core.enums.EnumSpecies;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

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
        Element blueGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element redGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.RED).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element blackGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element whiteGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.WHITE).add(Keys.DISPLAY_NAME, Text.of("")).build());
        Element star = Element.of(ItemStack.builder().itemType(ItemTypes.END_CRYSTAL).add(Keys.DISPLAY_NAME, Text.of("")).build());

        Layout.Builder layout = Layout.builder().set(redGlass, new int[] { 0, 1, 3, 4, 5, 7, 8 }).set(redGlass, new int[] { 2, 6 }).set(blackGlass, new int[] { 9, 17 }).set(whiteGlass, new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 }).set(star, 13);
        int slot = 10;
        for (NBTTagCompound poke : this.pokemon) {

            if (poke == null) {

                layout.set(Element.of(ItemStack.of(ItemTypes.AIR, 1)), new int[0]);
            }
            else {

                this.noPokemon++;
                int price = Utilities.getPrice(poke);
                Consumer<Action.Click> action = a -> {

                    if (this.noPokemon == 1) {

                        this.player.sendMessage(Text.of("[STS] You can't sell your last Pokemon!"));
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
                        lore.add(Text.of(new Object[] { TextColors.GREEN, booster.getItemLore(), TextColors.AQUA, Integer.valueOf(booster.getMoney(poke)) }));
                    }
                }
                lore.add(Text.EMPTY);
                lore.add(Text.of(new Object[] { TextColors.GREEN, "Total: ", TextColors.AQUA, Double.valueOf(total) }));
                p.offer(Keys.ITEM_LORE, lore);
                p.offer(Keys.DISPLAY_NAME, Text.of(new Object[] { TextColors.GOLD, poke.getString("Name") }));
                layout.set(Element.of(p, action), slot);
            }
            if (slot == 12) {
                slot += 2;
            } else {
                slot++;
            }
        }  Layout lay = layout.build();
        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(TextSerializers.FORMATTING_CODE.deserialize("Server Trade Station"))).build((STS.getInstance()).container);
        view.define(lay);
        view.open(this.player);
    }
}
