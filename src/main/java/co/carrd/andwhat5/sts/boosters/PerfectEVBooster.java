package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;

public class PerfectEVBooster implements IBooster
{
    @Override
    public int getMoney(Pokemon pokemon)
    {
        EVStore store = pokemon.getStats().evs;
        float ivHP = store.hp;
        float ivDef = store.defence;
        float ivAtk = store.attack;
        float ivSpeed = store.speed;
        float ivSAtk = store.specialAttack;
        float ivSDef = store.specialDefence;
        int total = Math.round(ivHP + ivDef + ivAtk + ivSpeed + ivSAtk + ivSDef);
        return total == EVStore.MAX_TOTAL_EVS ? STSConfig.General.perfectEVBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Perfect EV Booster: $";
    }
}
