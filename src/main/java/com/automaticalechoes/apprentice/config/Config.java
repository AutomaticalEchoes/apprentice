package com.automaticalechoes.apprentice.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Integer> REPAIR_COST_BASE;
    public static final ModConfigSpec.ConfigValue<Integer> REPAIR_COST_RANDOM;
    public static final ModConfigSpec.ConfigValue<Integer> REPAIR_BASE_PRE_COST;
    public static final ModConfigSpec.ConfigValue<Double> REPAIR_MUT_PRE_LEVEL;
    public static final ModConfigSpec.ConfigValue<Integer> REPAIR_MAX_USE;

    public static final ModConfigSpec.ConfigValue<Integer> RECORD_COST_BASE;
    public static final ModConfigSpec.ConfigValue<Integer> RECORD_COST_RANDOM;
    public static final ModConfigSpec.ConfigValue<Integer> RECORD_MAX_USE;

    public static final ModConfigSpec.ConfigValue<Integer> IMPROVE_COST_BASE;
    public static final ModConfigSpec.ConfigValue<Integer> IMPROVE_COST_RANDOM;
    public static final ModConfigSpec.ConfigValue<Integer> IMPROVE_MAX_USE;
    public static final ModConfigSpec.ConfigValue<Integer> IMPROVE_MODIFIER_BASE;
    public static final ModConfigSpec.ConfigValue<Integer> IMPROVE_MODIFIER_RANDOM;
    public static final ModConfigSpec.ConfigValue<Integer> LEARN_LIMIT;

    public static final ModConfigSpec.ConfigValue<Integer> FRESH_COST_BASE;
    public static final ModConfigSpec.ConfigValue<Integer> FRESH_COST_RANDOM;
    public static final ModConfigSpec.ConfigValue<Integer> FRESH_MAX_USE;
    public static final ModConfigSpec.ConfigValue<Integer> FRESH_COST_LEVEL_BONUS;

    public static final ModConfigSpec.ConfigValue<Integer> MAX_USES_LIMIT;
    public static final ModConfigSpec.ConfigValue<Integer> TRADES_NUMBER_PRE_UPDATE;

    static {
        BUILDER.push("master piece config");
        BUILDER.comment("trade cost = TradeCostBase + random(TradeCostRandom)");
        BUILDER.push("trade of repair");
        REPAIR_MAX_USE = BUILDER.defineInRange("tradeMaxUse",20,5,20);
        REPAIR_COST_BASE = BUILDER.defineInRange("tradeCostBase",5,5,10);
        REPAIR_COST_RANDOM = BUILDER.defineInRange("tradeCostRandom",12,5,20);
        BUILDER.comment("repair durability = (basePreCost + (mutPreLevel * villagerLevel) ) * cost");
        REPAIR_BASE_PRE_COST = BUILDER.defineInRange("repairBasePreCost",10,5,25);
        REPAIR_MUT_PRE_LEVEL = BUILDER.defineInRange("mutPreLevel",0.40F,0.20F,0.40F);
        BUILDER.pop();

        BUILDER.push("trade of record");
        RECORD_MAX_USE = BUILDER.defineInRange("tradeMaxUse",20,5,20);
        RECORD_COST_BASE = BUILDER.defineInRange("tradeCostBase",14,10,20);
        RECORD_COST_RANDOM = BUILDER.defineInRange("tradeCostRandom",10,0,10);
        BUILDER.pop();

        BUILDER.push("trade of improve");
        IMPROVE_MAX_USE = BUILDER.defineInRange("tradeMaxUse",3,1,5);
        IMPROVE_COST_BASE = BUILDER.defineInRange("tradeCostBase",48,40,64);
        IMPROVE_COST_RANDOM = BUILDER.defineInRange("tradeCostRandom",8,0,32);
        IMPROVE_MODIFIER_BASE = BUILDER.defineInRange("modifierBase",3,1,10);
        IMPROVE_MODIFIER_RANDOM = BUILDER.defineInRange("modifierRandom",5,1,10);
        LEARN_LIMIT = BUILDER.defineInRange("improveTradesLearnLimit", 20,15,99);
        BUILDER.comment("modifier = (modifierBase + modifierRandom) / 10.\n Example: Damage = WeaponsBaseDamage * (1 + modifier)");
        BUILDER.pop();

        BUILDER.push("trade of fresh");
        FRESH_MAX_USE = BUILDER.defineInRange("tradeMaxUse",20,5,20);
        FRESH_COST_BASE = BUILDER.defineInRange("tradeCostBase",10,10,20);
        FRESH_COST_RANDOM = BUILDER.defineInRange("tradeCostRandom",10,0,20);
        FRESH_COST_LEVEL_BONUS = BUILDER.comment("fresh trade cost = base + random + level * bonus").defineInRange("bonusPreLevel", 2,1,4);
        BUILDER.pop();

        BUILDER.push("others");
        MAX_USES_LIMIT = BUILDER.defineInRange("TradeMaxUseLimit", 30,30,99);
        TRADES_NUMBER_PRE_UPDATE = BUILDER.defineInRange("NumberOfNewTradesPreVillagerLevelUp",1,1,10);
        BUILDER.pop();

        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
