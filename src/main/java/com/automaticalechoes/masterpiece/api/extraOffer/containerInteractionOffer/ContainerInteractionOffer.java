package com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer;

import com.automaticalechoes.masterpiece.api.extraOffer.ExtraOffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;

import java.util.Optional;

public abstract class ContainerInteractionOffer<T extends ExtraOffer<?>> extends ExtraOffer<T> {
    public ContainerInteractionOffer(ItemStack costA, ItemStack result , int maxUses, int xp, float priceMultiplier) {
        super(costA, result, maxUses, xp, priceMultiplier);
    }

    public ContainerInteractionOffer(ItemStack costA, ItemStack costB, ItemStack result, int maxUses, int xp, float priceMultiplier) {
        super(costA, costB, result, maxUses, xp, priceMultiplier);
    }

    public ContainerInteractionOffer(ItemStack costA, ItemStack costB, ItemStack result, int uses, int maxUses, int xp, float priceMultiplier) {
        super(costA, costB, result, uses, maxUses, xp, priceMultiplier);
    }

    public ContainerInteractionOffer(ItemStack costA, ItemStack costB, ItemStack result, int uses, int maxUses, int xp, float priceMultiplier, int demand) {
        super(costA, costB, result, uses, maxUses, xp, priceMultiplier, demand);
    }

    public ContainerInteractionOffer(CompoundTag p_45351_) {
        super(p_45351_);
    }

    public abstract Optional<ItemStack> interactionAssemble(MerchantContainer merchantContainer , Merchant merchant);


}
