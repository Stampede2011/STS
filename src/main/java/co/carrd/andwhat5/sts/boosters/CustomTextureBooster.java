package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class CustomTextureBooster
implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        if (pokemon.getCustomTexture() == null) {
            return 0;
        }
        if (pokemon.getCustomTexture().isEmpty()) {
            return 0;
        }
        return STSConfig.General.customTextureBooster;
    }

    @Override
    public String getItemLore() {
        return "Custom Texture Booster: $";
    }
}

