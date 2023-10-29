package com.automaticalechoes.apprentice.api.extraOffer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public abstract class ExtraOffer<T extends  ExtraOffer<?>> extends MerchantOffer implements Extra<T>{
    public ExtraOffer(ItemStack costA, ItemStack result , int maxUses, int xp, float priceMultiplier) {
        super(costA, result, maxUses, xp, priceMultiplier);
    }

    public ExtraOffer(ItemStack costA, ItemStack costB, ItemStack result, int maxUses, int xp, float priceMultiplier) {
        super(costA, costB, result, maxUses, xp, priceMultiplier);
    }

    public ExtraOffer(ItemStack costA, ItemStack costB, ItemStack result, int uses, int maxUses, int xp, float priceMultiplier) {
        super(costA, costB, result, uses, maxUses, xp, priceMultiplier);
    }

    public ExtraOffer(ItemStack costA, ItemStack costB, ItemStack result, int uses, int maxUses, int xp, float priceMultiplier, int demand) {
        super(costA, costB, result, uses, maxUses, xp, priceMultiplier, demand);
    }

    public ExtraOffer(CompoundTag p_45351_) {
        super(p_45351_);
    }
}
