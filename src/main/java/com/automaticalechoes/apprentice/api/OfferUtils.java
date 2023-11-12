package com.automaticalechoes.apprentice.api;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class OfferUtils {
    public static final TagKey<Item> WEAPONS = TagKey.create(Registries.ITEM,new ResourceLocation("weapons"));

    public static int RandomRepairCost(){
        return Config.REPAIR_BASE_COST.get() + Apprentice.RANDOM.nextInt(Config.REPAIR_RANDOM_COST.get());
    }

    public static int GetXpPreLevel(int level){
        return level > 1 ? 10 : 2;
    }

    public static Double getRandomModifier(){
        Integer integer = Config.IMPROVE_BASE.get();
        int i = Apprentice.RANDOM.nextInt(10 - integer);
        return (integer + i) / 10.0D;
    }

    public static Double getRandomNumber(){
        return (double) Apprentice.RANDOM.nextInt(64);
    }


}
