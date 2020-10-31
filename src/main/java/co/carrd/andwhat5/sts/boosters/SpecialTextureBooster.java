package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;

public class SpecialTextureBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        return (pokemon.getInteger("specialTexture") != 0) ? STSConfig.Boosters.specialTextureBooster : 0;
    }

    public String getItemLore() {
        return "Special Texture Booster";
    }
}
