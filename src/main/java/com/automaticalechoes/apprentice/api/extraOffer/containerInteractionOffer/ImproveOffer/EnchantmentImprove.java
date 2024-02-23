package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.apprentice.Apprentice;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.Merchant;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnchantmentImprove extends ImproveOffer{
    public EnchantmentImprove(ItemStack costA, TagKey<Item> itemTag, double extra, ItemStack showCostB, ItemStack showResult) {
        super(costA, itemTag, extra, showCostB, showResult);
    }

    public EnchantmentImprove(CompoundTag p_45351_) {
        super(p_45351_);
    }

    @Override
    public Optional<ItemStack> interactionAssemble(MerchantContainer merchantContainer, Merchant merchant) {
        ItemStack copy = (isEnchantable(merchantContainer.getItem(1)) ? merchantContainer.getItem(1) : merchantContainer.getItem(0)).copy();
        boolean isBook = copy.is(Items.BOOK);
        if(isBook)
            copy = new ItemStack(Items.ENCHANTED_BOOK);
        boolean shouldExtra = shouldExtra(merchant);
        List<Enchantment> finalEnchantments = new ArrayList<>();
        List<Enchantment> enchantments = Enchantments(copy,isBook);

        if(enchantments.size() == 0) return Optional.empty();
        int v = (int) this.extra % enchantments.size();
        finalEnchantments.add(enchantments.get(v));
        if(shouldExtra){
            int i = Apprentice.RANDOM.nextInt(enchantments.size());
            if(i != v){
                finalEnchantments.add(enchantments.get(i));
            }
        }

        for (Enchantment finalEnchantment : finalEnchantments) {
            if(isBook){
                EnchantedBookItem.addEnchantment(copy,new EnchantmentInstance(finalEnchantment,finalEnchantment.getMaxLevel()));
            }else {
                copy.enchant(finalEnchantment,finalEnchantment.getMaxLevel());
            }

        }

        String workerName = shouldExtra ? ((AbstractVillager) merchant).getCustomName().getString() : "apprentice.offer.unknown_worker";
        copy.getOrCreateTag().putString(MASTER_PIECE,workerName);
        copy.getOrCreateTag().putBoolean(IMPROVED , true);
        return Optional.of(copy);
    }

    @Override
    public boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_) {
        return p_45356_.getItem() == this.getCostA().getItem() && p_45356_.getCount() >= this.getCostA().getCount()
                && isEnchantable(p_45357_)  && !p_45357_.getOrCreateTag().contains(IMPROVED);
    }

    @Override
    public Type<ImproveOffer> getType() {
        return Types.ENCHANTMENT_IMPROVE_OFFER_TYPE;
    }

    public static List<Enchantment> Enchantments(ItemStack itemStack,boolean isBook){
        if(isBook){
            return ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(enchantment -> !enchantment.isCurse()).toList();
        }
        return ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(enchantment -> enchantment.canEnchant(itemStack) && !enchantment.isCurse()).toList();
    }

    public static boolean isEnchantable(ItemStack itemStack){
        return itemStack.isEnchantable() || itemStack.is(Items.BOOK);
    }
}
