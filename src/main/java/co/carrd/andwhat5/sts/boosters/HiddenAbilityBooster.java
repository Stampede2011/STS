package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;



public class HiddenAbilityBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) { return (pokemon.getInteger("AbilitySlot") == 2) ? STSConfig.General.hiddenAbilityBooster : 0; }





    public String getItemLore() { return "Hidden Ability Booster: $"; }
}
