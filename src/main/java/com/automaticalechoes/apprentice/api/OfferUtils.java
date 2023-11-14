package com.automaticalechoes.apprentice.api;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.config.Config;
import com.mojang.datafixers.kinds.App;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.MultiMap;

import java.util.ArrayList;
import java.util.Map;

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
        return (double) Apprentice.RANDOM.nextInt(ForgeRegistries.ENCHANTMENTS.getValues().size());
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
