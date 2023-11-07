package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.apprentice.Apprentice;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.Merchant;
import net.minecraftforge.registries.ForgeRegistries;

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
        ItemStack copy = (merchantContainer.getItem(1).isEnchantable() ? merchantContainer.getItem(1) : merchantContainer.getItem(0)).copy();
        List<Enchantment> enchantments = Enchantments(copy);
        if(enchantments.size() == 0) return Optional.empty();
        int v =(int) this.extra % enchantments.size();
        Enchantment enchantment = enchantments.get(v);
        boolean shouldExtra = shouldExtra(merchant);
        copy.enchant(enchantment,enchantment.getMaxLevel());
        if(shouldExtra){
            int i = Apprentice.RANDOM.nextInt(enchantments.size());
            if(i != v){
                Enchantment enchantment1 = enchantments.get(i);
                copy.enchant(enchantment1,enchantment1.getMaxLevel());
            }
        }
        String workerName = shouldExtra ? ((AbstractVillager) merchant).getCustomName().getString() : "offer.unknown_worker";
        copy.getOrCreateTag().putString(MASTER_PIECE,workerName);
        copy.getOrCreateTag().putBoolean(IMPROVED , true);
        return Optional.of(copy);
    }

    @Override
    public boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_) {
        return p_45356_.getItem() == this.getCostA().getItem() && p_45356_.getCount() >= this.getCostA().getCount()
                && p_45357_.isEnchantable() && p_45357_.getCount() == 1  && !p_45357_.getOrCreateTag().contains(IMPROVED);
    }

    @Override
    public Type<ImproveOffer> getType() {
        return Types.ENCHANTMENT_IMPROVE_OFFER_TYPE;
    }

    public static List<Enchantment> Enchantments(ItemStack itemStack){
        return ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(enchantment -> enchantment.canEnchant(itemStack) && !enchantment.isCurse()).toList();
    }
}
