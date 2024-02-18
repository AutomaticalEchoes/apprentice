package com.automaticalechoes.apprentice.api;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.core.registries.BuiltInRegistries;

public class OfferUtils {
    public static int RandomRepairCost(){
        return Config.REPAIR_COST_BASE.get() + Apprentice.RANDOM.nextInt(Config.REPAIR_COST_RANDOM.get());
    }

    public static int GetXpPreLevel(int level){
        return level > 1 ? 10 : 2;
    }

    public static Double RandomModifier(){
        return (Config.IMPROVE_MODIFIER_BASE.get() + Apprentice.RANDOM.nextInt(Config.IMPROVE_MODIFIER_RANDOM.get())) / 10.0D;
    }

    public static Double RandomNumber(){
        return (double) Apprentice.RANDOM.nextInt(BuiltInRegistries.ENCHANTMENT.size());
    }

    public static int RandomRecordCost(){
        return Config.RECORD_COST_BASE.get() + Apprentice.RANDOM.nextInt(Config.RECORD_COST_RANDOM.get());
    }

    public static int RandomImproveCost(){
        return Config.IMPROVE_COST_BASE.get() + Apprentice.RANDOM.nextInt(Config.IMPROVE_COST_RANDOM.get());
    }

    public static int RandomFreshCost(){
        return Config.FRESH_COST_BASE.get() + Apprentice.RANDOM.nextInt(Config.FRESH_COST_RANDOM.get());
    }


}
