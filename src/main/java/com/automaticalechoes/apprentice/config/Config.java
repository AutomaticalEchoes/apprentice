package com.automaticalechoes.apprentice.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_BASE_PRE_COST;
    public static final ForgeConfigSpec.ConfigValue<Double> REPAIR_MUT_PRE_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_VILLAGER_XP;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_MAX_USE;

    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_MAX_USE;

    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_MAX_USE;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_BASE;

    static {
        BUILDER.push("master piece config");
        BUILDER.push("trade of repair");
        REPAIR_MAX_USE = BUILDER.defineInRange("max trade time",20,5,20);
        REPAIR_VILLAGER_XP = BUILDER.defineInRange("villager get xp when trade",1,1,3);
        REPAIR_COST_BASE = BUILDER.defineInRange("trade base cost",5,5,10);
        REPAIR_COST_RANDOM = BUILDER.defineInRange("trade cost = base cost + Random(randomCost)",12,5,20);
        REPAIR_BASE_PRE_COST = BUILDER.defineInRange("repair value = basePreCost * cost",10,5,25);
        REPAIR_MUT_PRE_LEVEL = BUILDER.defineInRange("repair durability = base * mut * villagerLevel",0.40F,0.20F,0.40F);
        BUILDER.pop();

        BUILDER.push("trade of record");
        RECORD_MAX_USE = BUILDER.defineInRange("max trade time",20,5,20);
        RECORD_COST_BASE = BUILDER.defineInRange("trade cost base",14,10,20);
        RECORD_COST_RANDOM = BUILDER.defineInRange("trade cost random",17,15,20);
        BUILDER.pop();

        BUILDER.push("trade of improve");
        IMPROVE_MAX_USE = BUILDER.defineInRange("max trade time",3,1,5);
        IMPROVE_COST_BASE = BUILDER.defineInRange("trade cost base",48,40,64);
        IMPROVE_COST_RANDOM = BUILDER.defineInRange("trade cost random",8,0,32);
        IMPROVE_BASE = BUILDER.defineInRange(" (base + Random(10 - base)) / 10",3,1,10);

        BUILDER.pop();


        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
