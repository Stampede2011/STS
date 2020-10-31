package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmongenerations.core.enums.EnumSpecies;
import net.minecraft.nbt.NBTTagCompound;

public class LegendaryBooster
        implements IBooster
{
    public int getMoney(NBTTagCompound pokemon) {
        boolean isLegend = EnumSpecies.legendaries.contains(pokemon.getString("Name"));
        boolean isUltraBeast = EnumSpecies.ultrabeasts.contains(pokemon.getString("Name"));
        return (isLegend || isUltraBeast) ? STSConfig.Boosters.legendaryBooster : 0;
    }

    public String getItemLore() {
        return "Legend Boost";
    }
}
