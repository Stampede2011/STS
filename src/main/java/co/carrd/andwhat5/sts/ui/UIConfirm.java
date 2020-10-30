package co.carrd.andwhat5.sts.ui;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import com.pixelmongenerations.core.enums.EnumSpecies;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;




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
                lore.add(Text.of(new Object[] { TextColors.GREEN, booster.getItemLore(), TextColors.AQUA, Integer.valueOf(booster.getMoney(this.pokemon)) }));
            }
        }
        lore.add(Text.EMPTY);
        lore.add(Text.of(new Object[] { TextColors.GREEN, "Total: ", TextColors.AQUA, Double.valueOf(total) }));
        p.offer(Keys.ITEM_LORE, lore);
        p.offer(Keys.DISPLAY_NAME, Text.of(new Object[] { TextColors.GOLD, this.pokemon.getString("Name") }));
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
                this.player.sendMessage(Text.of(new Object[] { TextColors.DARK_GRAY, "[", TextColors.WHITE, "Server", TextColors.RED, "Sale", TextColors.DARK_GRAY, "]", TextColors.GREEN, " You sold your " + this.pokemon.getString("Name") + " for $", TextColors.AQUA, this.price + "!" }));
                this.player.closeInventory();
            } else {
                this.player.sendMessage(Text.of("[ServerSale] Error selling " + this.pokemon.getString("Name") + ". Is it still in your party?"));
                this.player.closeInventory();
            }
        };

        ItemStack rDye = ItemStack.of((ItemType)Sponge.getRegistry().getType(ItemType.class, "pixelmon:power_bracer").get());
        ItemStack gDye = ItemStack.of((ItemType)Sponge.getRegistry().getType(ItemType.class, "pixelmon:power_weight").get());
        rDye.offer(Keys.DISPLAY_NAME, Text.of(new Object[] { TextColors.RED, "Back" }));
        gDye.offer(Keys.DISPLAY_NAME, Text.of(new Object[] { TextColors.GREEN, "Sell " + this.pokemon.getString("Name") + " for $" + this.price }));

        Element redBrace = Element.of(rDye, close);
        Element greenBrace = Element.of(gDye, sell);










        Layout layout = Layout.builder().set(redGlass, new int[] { 0, 1, 3, 4, 5, 7, 8 }).set(redGlass, new int[] { 2, 6 }).set(blackGlass, new int[] { 9, 17 }).set(whiteGlass, new int[] { 18, 19, 20, 21, 22, 23, 24, 25, 26 }).set(pok, 13).set(greenBrace, 11).set(redBrace, 15).build();
        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(TextSerializers.FORMATTING_CODE.deserialize("Server Trade Confirm"))).build((STS.getInstance()).container);
        view.define(layout);
        view.open(this.player);
    }
}
