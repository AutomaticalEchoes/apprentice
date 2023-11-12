package com.automaticalechoes.apprentice.api;

import com.automaticalechoes.apprentice.register.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModelItem {
    public static ItemStack TOOL;
    public static ItemStack TOOL_IMPROVE;
    public static ItemStack ARMOR;
    public static ItemStack ARMOR_IMPROVE;

    public static ItemStack RECORD_ITEM;

    public static ItemStack ENCHANTABLE;
    public static ItemStack ENCHANTED_ITEM;
    public static ItemStack FIXABLE_BREAK;
    public static ItemStack FIXABLE;

    public static void init() {
        RECORD_ITEM = ItemRegister.WORK_RECORD.get().getDefaultInstance();

        TOOL_IMPROVE = ItemRegister.SHOW_TOOL_IMPROVE.get().getDefaultInstance();
        TOOL = ItemRegister.SHOW_TOOL.get().getDefaultInstance();

        ARMOR_IMPROVE = ItemRegister.SHOW_ARMOR_IMPROVE.get().getDefaultInstance();
        ARMOR = ItemRegister.SHOW_ARMOR.get().getDefaultInstance();

        ENCHANTABLE = ItemRegister.ENCHANTABLE.get().getDefaultInstance();
        ENCHANTED_ITEM = Items.ENCHANTED_BOOK.getDefaultInstance();

        FIXABLE_BREAK = ItemRegister.FIXABLE.get().getDefaultInstance();
        FIXABLE = ItemRegister.FIXABLE.get().getDefaultInstance();

        ENCHANTED_ITEM.setHoverName(Component.translatable("apprentice.enchanted_item"));

        FIXABLE_BREAK.setDamageValue(9);

    }
}
