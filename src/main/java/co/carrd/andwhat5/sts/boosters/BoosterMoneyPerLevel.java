package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;

public class BoosterMoneyPerLevel
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        int level = pokemon.getInteger("Level");
        return level * STSConfig.Boosters.moneyPerLevel;
    }

    public String getItemLore() {
        return "Level Money";
    }
}
