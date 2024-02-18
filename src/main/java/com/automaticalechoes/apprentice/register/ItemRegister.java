package com.automaticalechoes.apprentice.register;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.common.item.WorkRecord;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Apprentice.MODID);
    public static final ArrayList<DeferredItem<? extends Item>> MOD_ITEMS = new ArrayList<>();
    public static final DeferredItem<WorkRecord> WORK_RECORD = Register("work_record",()-> new WorkRecord(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SHOW_TOOL = ITEMS.register("show_tool", () -> new Item(new Item.Properties().durability(10)));
    public static final DeferredItem<Item> SHOW_TOOL_IMPROVE = ITEMS.register("show_tool_improve", () -> new Item(new Item.Properties().durability(10)));
    public static final DeferredItem<Item> SHOW_ARMOR = ITEMS.register("show_armor", () -> new Item(new Item.Properties().durability(10)));
    public static final DeferredItem<Item> SHOW_ARMOR_IMPROVE = ITEMS.register("show_armor_improve", () -> new Item(new Item.Properties().durability(10)));
    public static final DeferredItem<Item> ENCHANTABLE = ITEMS.register("enchantable",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FIXABLE = ITEMS.register("fixable", () -> new Item(new Item.Properties().durability(10)));
    public static <T extends Item> DeferredItem<T> Register(String key, Supplier<T> supplier){
        DeferredItem<T> register = ITEMS.register(key, supplier);
        MOD_ITEMS.add(register);
        return register;
    }
}
