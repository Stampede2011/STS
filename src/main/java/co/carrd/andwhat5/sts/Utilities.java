package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmongenerations.core.config.PixelmonItems;
import com.pixelmongenerations.core.enums.EnumSpecies;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import com.pixelmongenerations.core.util.helper.SpriteHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

public class Utilities
{
    public static org.spongepowered.api.item.inventory.ItemStack getPokemonItem(EnumSpecies species, int form, boolean isEgg, boolean isShiny) {
        ItemStack nativeItem = new ItemStack(PixelmonItems.itemPixelmonSprite);
        NBTTagCompound nbt = new NBTTagCompound();
        String idValue = String.format("%03d", new Object[] { Integer.valueOf(species.getNationalPokedexInteger()) });
        if (isEgg) {
            switch (species) {
                case Manaphy:
                case Togepi:
                    nbt.setString("SpriteName", "pixelmon:sprites/eggs/manaphy1");
                    nativeItem.setTagCompound(nbt);
                    return ItemStackUtil.fromNative(nativeItem);
            }
            nbt.setString("SpriteName", "pixelmon:sprites/eggs/egg1");
        } else if (isShiny) {
            nbt.setString("SpriteName", "pixelmon:sprites/shinypokemon/" + idValue + SpriteHelper.getSpriteExtra(species.name, form));
        } else {
            nbt.setString("SpriteName", "pixelmon:sprites/pokemon/" + idValue + SpriteHelper.getSpriteExtra(species.name, form));
        }
        nativeItem.setTagCompound(nbt);
        return ItemStackUtil.fromNative(nativeItem);
    }

    public static int getPrice(NBTTagCompound pokemon) {
        int money = 0;

        for (IBooster booster : STS.boosters)
            money += booster.getMoney(pokemon);

        if (pokemon.getBoolean("isEgg") && STSConfig.Boosters.eggModifier != 0)
            money -= (STSConfig.Boosters.eggModifier / 100.0) * money;

        return money;
    }

    public static boolean sellPokemon(Player player, NBTTagCompound pokemon, int price) {
        PlayerStorage ps = (PlayerStorage)PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
        if (ps == null)
            return false;
        int slot = 0;
        for (NBTTagCompound nbt : ps.partyPokemon) {

            if (nbt == pokemon) {
                ps.removeFromPartyPlayer(slot);
                ps.addCurrency(price);
                return true;
            }
            slot++;
        }
        return false;
    }

    public static Text toText(String msg) {
        return TextSerializers.FORMATTING_CODE.deserialize(msg);
    }

    public static Text getMessage(String msg) {
        return toText(STSConfig.Messages.prefix + msg);
    }

}
