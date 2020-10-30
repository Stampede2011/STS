package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class MaxLevelBooster implements IBooster
{
        public int getMoney(Pokemon pokemon)
        {
            return pokemon.getLevel() == 100 ? STSConfig.General.MaxLevelBooster : 0;
        }

        public String getItemLore()
        {
            return "Max Level Booster: $";
        }
    }

