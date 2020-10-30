package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.common.item.ItemPixelmonSprite;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.math.BigDecimal;

public class Utilities {
    public static ItemStack getPokemonItem(EntityPixelmon pokemon) {
        /*String filePath = "pixelmon:sprites/";
        String specialTexture = pokemon.getCustomTexture();
        if ((pokemon.getFormEnum() instanceof EnumNoForm || pokemon.getFormEnum() instanceof EnumPrimal) && pokemon.getFormEnum().isTemporary())
        {
            ItemPixelmonSprite.getPhoto()
            specialTexture = EnumSpecialTexture.None;
        }
        if (pokemon.isShiny()) {
            filePath = filePath + "shiny";
            specialTexture = EnumSpecialTexture.None;
        }
        String path = filePath + "pokemon/" + pokemon.getSpecies().getNationalPokedexNumber() + SpriteHelper.getSpriteExtra(pokemon.getSpecies().name, pokemon.getForm(), pokemon.getGender());
        if (pokemon.isEgg()) {
            path = "pixelmon:sprites/eggs/egg1";
        }
        ItemStack itemStack = ItemStack.of(STS.getInstance().spriteItemType);
        DataContainer dc = itemStack.toContainer();
        dc.set(DataQuery.of("UnsafeData", "SpriteName"), path);
        return ItemStack.builder().fromContainer(dc).build();
        */
        return ItemStackUtil.fromNative(ItemPixelmonSprite.getPhoto(pokemon));
    }

    public static int getPrice(EntityPixelmon pokemon) {
        int money = 0;
        for (IBooster booster : STS.boosters) {
            money += booster.getMoney(pokemon);
        }
        return money;
    }

    public static boolean sellPokemon(Player player, EntityPixelmon pokemon1, int price) {
        PlayerStorage playerPartyStorage = (PlayerStorage) PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
        for (int i = 0; i < 6; ++i) {
            EntityPixelmon pokemon = playerPartyStorage.getPokemon(i, (World) player.getWorld());
            if (pokemon == null || !pokemon == pokemon1)
                continue;
            playerPartyStorage.set(i, null);
            STS.econ.getOrCreateAccount(player.getUniqueId())
                    .ifPresent(e ->
                            e.deposit(STS.econ.getDefaultCurrency(), BigDecimal.valueOf(price), Cause.of(EventContext.empty(), STS.getInstance())));
            return true;
        }
        return false;
    }
}

