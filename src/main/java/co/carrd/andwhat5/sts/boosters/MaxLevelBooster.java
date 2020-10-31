package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;

public class MaxLevelBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon)
    {
        return pokemon.getInteger("Level") == 100 ? STSConfig.Boosters.maxLevel : 0;
    }

    public String getItemLore()
    {
        return "Max Level Booster";
    }
}