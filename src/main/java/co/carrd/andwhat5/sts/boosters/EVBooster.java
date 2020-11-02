package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;

public class EVBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        float evHP = pokemon.getFloat("EVHP");
        float evAtk = pokemon.getFloat("EVAttack");
        float evDef = pokemon.getFloat("EVDefence");
        float evSpeed = pokemon.getFloat("EVSpeed");
        float evSAtk = pokemon.getFloat("EVSpAtt");
        float evSDef = pokemon.getFloat("EVSpDef");
        return Math.round((evHP + evDef + evAtk + evSpeed + evSAtk + evSDef) / 510.0F * 100.0F) * STSConfig.Boosters.moneyPerEV;
    }

    public String getItemLore() {
        return "EV Boost";
    }
}
