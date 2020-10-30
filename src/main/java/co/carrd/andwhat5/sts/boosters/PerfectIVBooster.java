package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Stats;

public class PerfectIVBooster implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        IVStore store = pokemon.getStats().ivs;
        float ivHP = store.hp;
        float ivDef = store.defence;
        float ivAtk = store.attack;
        float ivSpeed = store.speed;
        float ivSAtk = store.specialAttack;
        float ivSDef = store.specialDefence;
        int per = Math.round((ivHP + ivDef + ivAtk + ivSpeed + ivSAtk + ivSDef) / 186.0f * 100.0f);
        return per == 100 ? STSConfig.General.perfectIVBooster : 0;
    }

    @Override
    public String getItemLore() {
        return "Perfect IV Booster: $";
    }
}

