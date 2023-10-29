package com.automaticalechoes.masterpiece.api;

import com.automaticalechoes.masterpiece.common.item.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ModelItem {
    public static ItemStack TOOL;
    public static ItemStack TOOL_BREAK;
    public static ItemStack TOOL_IMPROVE;
    public static ItemStack ARMOR;
    public static ItemStack ARMOR_BREAK;
    public static ItemStack ARMOR_IMPROVE;
    public static ItemStack ENCHANTABLE_EQUIP;
    public static ItemStack ENCHANTED_ITEM;
    public static ItemStack RECORD_ITEM;

    public static void init() {
        RECORD_ITEM = ItemRegister.WORK_RECORD.get().getDefaultInstance();

        TOOL_IMPROVE = ItemRegister.SHOW_TOOL_IMPROVE.get().getDefaultInstance();
        TOOL_BREAK = ItemRegister.SHOW_TOOL_FIX.get().getDefaultInstance();
        TOOL = ItemRegister.SHOW_TOOL.get().getDefaultInstance();

        ARMOR_IMPROVE = ItemRegister.SHOW_ARMOR_IMPROVE.get().getDefaultInstance();
        ARMOR_BREAK = ItemRegister.SHOW_ARMOR_FIX.get().getDefaultInstance();
        ARMOR = ItemRegister.SHOW_ARMOR.get().getDefaultInstance();

        ENCHANTABLE_EQUIP = ItemRegister.SHOW_ALL_ENCHANTABLE.get().getDefaultInstance();
        ENCHANTED_ITEM = ItemRegister.SHOW_ALL_ENCHANTABLE.get().getDefaultInstance();

        ENCHANTED_ITEM.setHoverName(Component.translatable("enchanted_item"));

        ARMOR_BREAK.setDamageValue(9);
        TOOL_BREAK.setDamageValue(9);

    }
}
