package com.automaticalechoes.apprentice.common.item;

import com.automaticalechoes.apprentice.Apprentice;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Apprentice.MODID);
    public static final ArrayList<RegistryObject<? extends Item>> MOD_ITEMS = new ArrayList<>();
    public static final RegistryObject<WorkRecord> WORK_RECORD = Register("work_record",()-> new WorkRecord(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SHOW_TOOL = ITEMS.register("show_tool", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_TOOL_FIX = ITEMS.register("show_tool_fix", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_TOOL_IMPROVE = ITEMS.register("show_tool_improve", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_ARMOR = ITEMS.register("show_armor", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_ARMOR_FIX = ITEMS.register("show_armor_fix", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_ARMOR_IMPROVE = ITEMS.register("show_armor_improve", () -> new Item(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> SHOW_ALL_ENCHANTABLE = ITEMS.register("show_all_enchantable",() -> new Item(new Item.Properties()));
    public static <T extends Item> RegistryObject<T> Register(String key, Supplier<T> supplier){
        RegistryObject<T> register = ITEMS.register(key, supplier);
        MOD_ITEMS.add(register);
        return register;
    }
}
