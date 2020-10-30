package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class BoosterMoneyPerLevel
implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        int level = pokemon.getLevel();
        return level * STSConfig.General.moneyPerLevel;
    }

    @Override
    public String getItemLore() {
        return "Money from Level : $";
    }
}

