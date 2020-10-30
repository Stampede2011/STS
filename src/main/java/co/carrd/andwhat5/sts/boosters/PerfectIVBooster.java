package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;


public class PerfectIVBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        float ivHP = pokemon.getByte("IVHP");
        float ivAtk = pokemon.getByte("IVAttack");
        float ivDef = pokemon.getByte("IVDefence");
        float ivSpeed = pokemon.getByte("IVSpeed");
        float ivSAtk = pokemon.getByte("IVSpAtt");
        float ivSDef = pokemon.getByte("IVSpDef");
        int per = Math.round((ivHP + ivDef + ivAtk + ivSpeed + ivSAtk + ivSDef) / 186.0F * 100.0F);
        return (per == 100) ? STSConfig.General.perfectIVBooster : 0;
    }



    public String getItemLore() { return "Perfect IV Booster: $"; }
}
