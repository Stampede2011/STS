package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;

public class PerfectEVBooster implements IBooster
{
    @Override
    public int getMoney(NBTTagCompound pokemon)
    {
        float evHP = pokemon.getFloat("EVHP");
        float evAtk = pokemon.getFloat("EVAttack");
        float evDef = pokemon.getFloat("EVDefence");
        float evSpeed = pokemon.getFloat("EVSpeed");
        float evSAtk = pokemon.getFloat("EVSpAtt");
        float evSDef = pokemon.getFloat("EVSpDef");
        int total = Math.round(evHP + evDef + evAtk + evSpeed + evSAtk + evSDef);
        return total == 510 ? STSConfig.Boosters.perfectEVBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Perfect EV Booster";
    }
}