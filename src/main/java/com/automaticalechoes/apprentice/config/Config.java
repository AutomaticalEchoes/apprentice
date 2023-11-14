package com.automaticalechoes.apprentice.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_BASE_PRE_COST;
    public static final ForgeConfigSpec.ConfigValue<Double> REPAIR_MUT_PRE_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> REPAIR_MAX_USE;

    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECORD_MAX_USE;

    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_MAX_USE;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_MODIFIER_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> IMPROVE_MODIFIER_RANDOM;

    public static final ForgeConfigSpec.ConfigValue<Integer> FRESH_COST_BASE;
    public static final ForgeConfigSpec.ConfigValue<Integer> FRESH_COST_RANDOM;
    public static final ForgeConfigSpec.ConfigValue<Integer> FRESH_MAX_USE;
    public static final ForgeConfigSpec.ConfigValue<Integer> FRESH_COST_LEVEL_BONUS;

    static {
        BUILDER.push("master piece config");
        BUILDER.push("trade of repair");
        REPAIR_MAX_USE = BUILDER.defineInRange("max trade time",20,5,20);
        REPAIR_COST_BASE = BUILDER.defineInRange("trade base cost",5,5,10);
        REPAIR_COST_RANDOM = BUILDER.defineInRange("trade cost = base cost + Random(randomCost)",12,5,20);
        REPAIR_BASE_PRE_COST = BUILDER.comment("repair base = basePreCost * cost").defineInRange("basePreCost",10,5,25);
        REPAIR_MUT_PRE_LEVEL = BUILDER.comment("repair durability = base * mut * villagerLevel").defineInRange("mut pre level",0.40F,0.20F,0.40F);
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
        IMPROVE_MODIFIER_BASE = BUILDER.defineInRange("modifierBase",5,1,10);
        IMPROVE_MODIFIER_RANDOM = BUILDER.defineInRange("modifierRandomMax",5,1,10);
        BUILDER.push("modifier = (modifierBase + modifierRandom) / 10.\n Example: Damage = WeaponsBaseDamage * (1 + modifier)");
        BUILDER.pop();

        BUILDER.push("trade of fresh");
        FRESH_MAX_USE = BUILDER.defineInRange("max trade time",20,5,20);
        FRESH_COST_BASE = BUILDER.defineInRange("trade cost base",15,10,20);
        FRESH_COST_RANDOM = BUILDER.defineInRange("trade cost random",8,0,10);
        FRESH_COST_LEVEL_BONUS = BUILDER.comment("fresh trade cost = base + random + level * bonus").defineInRange("bonusPreLevel", 2,1,4);
        BUILDER.pop();

        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
