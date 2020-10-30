package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.interfaces.IBooster;

import java.util.List;

public class STSAPI {
    public static void addBooster(IBooster booster) {
        STS.boosters.add(booster);
    }

    public static void removeBooster(IBooster booster) {
        if (STS.boosters.contains(booster)) {
            STS.boosters.remove(booster);
        }
    }

    public static List<IBooster> getBoosters() {
        return STS.boosters;
    }
}

