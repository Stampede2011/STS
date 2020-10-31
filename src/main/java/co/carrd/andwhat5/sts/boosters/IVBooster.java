package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;

public class IVBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        float ivHP = pokemon.getByte("IVHP");
        float ivAtk = pokemon.getByte("IVAttack");
        float ivDef = pokemon.getByte("IVDefence");
        float ivSpeed = pokemon.getByte("IVSpeed");
        float ivSAtk = pokemon.getByte("IVSpAtt");
        float ivSDef = pokemon.getByte("IVSpDef");
        return Math.round((ivHP + ivDef + ivAtk + ivSpeed + ivSAtk + ivSDef) / 186.0F * 100.0F) * STSConfig.Boosters.moneyPerIV;
    }

    public String getItemLore() {
        return "IV Boost";
    }
}
