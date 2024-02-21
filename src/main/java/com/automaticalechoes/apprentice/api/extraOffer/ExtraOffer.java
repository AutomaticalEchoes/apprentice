package com.automaticalechoes.apprentice.api.extraOffer;

import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.FreshOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.EnchantmentImprove;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.ImproveOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.ModifierImprove;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.RepairOffer;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.Extra;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Function;

public abstract class ExtraOffer<T extends ExtraOffer<?>> extends MerchantOffer implements Extra<T> {

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

    @Override
    public MerchantOffer copy() {
        return this.getType().build(this.createTag());
    }

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.putString(EXTRA_OFFER,getType().getName());
        return tag;
    }

    public static class Types{
        public static final HashMap<String, Type<?>> EXTRA_OFFER_TYPES = new HashMap<>();
        public static final Type<RecordOffer> RECORD_OFFER_TYPE = Register("record", RecordOffer::new);
        public static final Type<RepairOffer> REPAIR_OFFER_TYPE = Register("repair", RepairOffer::new);
        public static final Type<ImproveOffer> MODIFIER_IMPROVE_OFFER_TYPE = Register("modifier_improve", ModifierImprove::new);
        public static final Type<ImproveOffer> ENCHANTMENT_IMPROVE_OFFER_TYPE = Register("enchantment_improve", EnchantmentImprove::new);
        public static final Type<FreshOffer> FRESH_OFFER_TYPE = Register("fresh",FreshOffer::new);
        public static <T extends ExtraOffer<?>> Type<T> Register(String name, Function<CompoundTag, T> supplier) {
            Type<T> tExtraOfferType = new Type<>(name, supplier);
            EXTRA_OFFER_TYPES.put(name, tExtraOfferType);
            return tExtraOfferType;
        }

        @Nullable
        public static ExtraOffer.Type<?> getOfferType(String name){
            return EXTRA_OFFER_TYPES.get(name);
        }
    }
}
