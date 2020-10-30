package co.carrd.andwhat5.sts.ui;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Action;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Element;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.Layout;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.inventory.View;
import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.core.storage.PlayerStorage;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.function.Consumer;

public class UISTS {
    private PlayerStorage storage;
    private Player player;
    private int noPokemon = 0;

    public UISTS(Player player, PlayerStorage storage) {
        this.storage = storage;
        this.player = player;
    }

    public void displayGUI() {
        Element blueGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLUE).build());
        Element redGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.RED).build());
        Element blackGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.BLACK).build());
        Element whiteGlass = Element.of(ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DYE_COLOR, DyeColors.WHITE).build());
        Layout.Builder layout = Layout.builder().set(blueGlass, 0, 1, 3, 4, 5, 7, 8).set(redGlass, 2, 6).set(blackGlass, 9, 17).set(whiteGlass, 13, 18, 19, 20, 21, 22, 23, 24, 25, 26);
        int slot = 10;
        for (int i = 0; i < 6; ++i) {
            EntityPixelmon poke = this.storage.get(i);
            if (poke == null) {
                layout.set(Element.of(ItemStack.of(ItemTypes.AIR, 1)));
            }
            else {
                ++this.noPokemon;
                int price = Utilities.getPrice(poke);
                Consumer<Action.Click> action = a ->
                {
                    if (this.noPokemon == 1 || !this.moreThanTwoNonEggs(this.storage)) {
                        this.player.sendMessage(Text.of("[STS] You can't sell your last Pokemon!"));
                    } else if (poke.isEgg() && !STSConfig.General.allowEggs) {
                        this.player.sendMessage(Text.of("[STS] You cannot sell eggs!"));
                    } else {
                        UIConfirm con = new UIConfirm(this.player, poke, price);
                        con.displayGUI();
                    }
                };
                ItemStack p = Utilities.getPokemonItem(poke);
                ArrayList<Text> lore = new ArrayList<>();
                for (IBooster booster : STS.boosters) {
                    if (booster.getMoney(poke) == 0) continue;
                    lore.add(Text.of((booster.getItemLore() + booster.getMoney(poke))));
                }
                if (!poke.isEgg() || STSConfig.General.allowEggs) {
                    if (poke.isEgg()) {
                        p.offer(Keys.DISPLAY_NAME, Text.of(("\u2605 Egg ($" + price + ") \u2605")));
                    } else {
                        p.offer(Keys.DISPLAY_NAME, Text.of(("\u2605 " + poke.getSpecies().name + " ($" + price + ") \u2605")));
                    }
                    p.offer(Keys.ITEM_LORE, lore);
                } else {
                    p.offer(Keys.DISPLAY_NAME, Text.of("\u2605 Egg  (Cannot Be Sold) \u2605"));
                }
                layout.set(Element.of(p, action), slot);
            }
            if (slot == 12) {
                slot += 2;
                continue;
            }
            ++slot;
        }
        Layout lay = layout.build();
        View view = View.builder().archetype(InventoryArchetypes.CHEST).property(InventoryTitle.of(Text.of("Sell to Server"))).build(STS.getInstance().container);
        view.define(lay);
        view.open(this.player);
    }

    private boolean moreThanTwoNonEggs(PlayerStorage storage) {
        int count = 0;
        for (EntityPixelmon p : storage.getTeam()) {
            if (p.isEgg()) continue;
            ++count;
        }
        return count >= 2;
    }
}

