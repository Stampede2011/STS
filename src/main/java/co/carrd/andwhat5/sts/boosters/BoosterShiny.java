package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;


public class BoosterShiny
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        boolean isShiny = pokemon.getBoolean("IsShiny");
        return isShiny ? STSConfig.General.shinyBooster : 0;
    }




    public String getItemLore() { return "Shiny Boost: $"; }
}
