package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;

public class CustomTextureBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        return (!pokemon.getString("CustomTexture").equals("")) ? STSConfig.Boosters.customTextureBooster : 0;
    }

    public String getItemLore() {
        return "Custom Texture Booster";
    }
}
