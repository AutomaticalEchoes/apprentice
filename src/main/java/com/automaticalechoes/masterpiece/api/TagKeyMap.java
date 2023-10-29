package com.automaticalechoes.masterpiece.api;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

import java.util.HashMap;

public class TagKeyMap {
    public static final HashMap<String, TagKey<Item>> KEY_TAG = new HashMap<>();
    public static final HashMap<TagKey<Item>,String> TAG_KEY = new HashMap<>();
    public static final String ARMOR = Put("armor", Tags.Items.ARMORS);
    public static final String TOOLS = Put("tools", Tags.Items.TOOLS);
    public static final String AXES = Put("axes", ItemTags.AXES);
    public static final String PICKAXES = Put("pickaxes", ItemTags.PICKAXES);
    public static final String SHOVELS = Put("shovels", ItemTags.SHOVELS);
    public static final String HOES = Put("hoes", ItemTags.HOES);
    public static String Put(String s , TagKey<Item> itemTagKey){
        KEY_TAG.put(s,itemTagKey);
        TAG_KEY.put(itemTagKey,s);
        return s;
    }

    public static String getKey(TagKey<Item> itemTagKey){
        return TAG_KEY.getOrDefault(itemTagKey,TOOLS);
    }

    public static TagKey<Item> getTag(String s){
        return KEY_TAG.getOrDefault(s,Tags.Items.TOOLS);
    }

    public static void init(){}
}
