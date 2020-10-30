package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class HiddenAbilityBooster
implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        return pokemon.getAbilitySlot() == 2 ? STSConfig.General.hiddenAbilityBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Hidden Ability Booster: $";
    }
}

