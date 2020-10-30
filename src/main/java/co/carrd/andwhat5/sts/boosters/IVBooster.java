package co.carrd.andwhat5.sts.boosters;

import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Stats;

public class IVBooster
implements IBooster {
    @Override
    public int getMoney(Pokemon pokemon) {
        IVStore store = pokemon.getStats().ivs;
        float ivHP = store.hp;
        float ivAtk = store.attack;
        float ivDef = store.defence;
        float ivSpeed = store.speed;
        float ivSAtk = store.specialAttack;
        float ivSDef = store.specialDefence;
        return Math.round((ivHP + ivDef + ivAtk + ivSpeed + ivSAtk + ivSDef) / 186.0f * 100.0f) * STSConfig.General.moneyPerIV;
    }

    @Override
    public String getItemLore() {
        return "Money from IV Percentage: $";
    }
}

