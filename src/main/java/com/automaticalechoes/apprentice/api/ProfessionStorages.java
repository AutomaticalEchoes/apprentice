package com.automaticalechoes.apprentice.api;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ProfessionStorages {
    public static final HashMap<VillagerProfession, Set<Item>> STORAGES = new HashMap<>();

    public static void PutStorages(VillagerProfession villagerProfession, Item item){
        PutStorages(villagerProfession, List.of(item));
    }

    public static void PutStorages(VillagerProfession villagerProfession, Item... items){
        PutStorages(villagerProfession, List.of(items));
    }

    public static void PutStorage(VillagerProfession villagerProfession, TagKey<Item> itemTagKey){
        Iterable<Holder<Item>> holderIterable = BuiltInRegistries.ITEM.getTagOrEmpty(itemTagKey);
        for (Holder<Item> itemHolder : holderIterable) {
            PutStorages(villagerProfession, itemHolder.value());
        }
    }

    public static void PutStorages(VillagerProfession villagerProfession , Collection<Item> item){
        if(!STORAGES.containsKey(villagerProfession)){
            STORAGES.put(villagerProfession,new LinkedHashSet<>());
        }
        STORAGES.get(villagerProfession).addAll(item);
    }

    public static boolean ShouldStorages(VillagerProfession villagerProfession, Item item){
        return STORAGES.containsKey(villagerProfession) && STORAGES.get(villagerProfession).contains(item);
    }

    public static boolean ShouldStorages(VillagerProfession villagerProfession, ItemStack itemStack){
        return !itemStack.isEmpty() && ShouldStorages(villagerProfession,itemStack.getItem());
    }

    public static void init(){
//        List<Item>  ore = List.of(Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.IRON_ORE, Items.GOLD_ORE, Items.DIAMOND_ORE);
//        PutStorages(VillagerProfession.ARMORER, ore);
//        PutStorages(VillagerProfession.WEAPONSMITH,ore);
//        PutStorages(VillagerProfession.TOOLSMITH,ore);
    }
}
