package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import java.util.ArrayList;

public class UltraBeastBooster
implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        return EnumSpecies.ultrabeasts.contains(pokemon.getSpecies().name) ? STSConfig.General.ultraBeastBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Ultra Beast Booster: $";
    }
}

