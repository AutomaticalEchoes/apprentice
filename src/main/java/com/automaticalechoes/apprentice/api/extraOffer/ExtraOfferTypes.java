package com.automaticalechoes.apprentice.api.extraOffer;

import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.EnchantmentImprove;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.ImproveOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.ModifierImprove;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.RepairOffer;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Function;

public class ExtraOfferTypes {
    public static final HashMap<String, ExtraOfferType<?>> EXTRA_OFFER_TYPES = new HashMap<>();
    public static final ExtraOfferType<RecordOffer> RECORD_OFFER_TYPE = Register("record", RecordOffer::new);
    public static final ExtraOfferType<RepairOffer> REPAIR_OFFER_TYPE = Register("repair", RepairOffer::new);
    public static final ExtraOfferType<ImproveOffer> MODIFIER_IMPROVE_OFFER_TYPE = Register("modifier_improve", ModifierImprove::new);
    public static final ExtraOfferType<ImproveOffer> ENCHANTMENT_IMPROVE_OFFER_TYPE = Register("enchantment_improve", EnchantmentImprove::new);
    public static <T extends ExtraOffer<?>> ExtraOfferType<T> Register(String name, Function<CompoundTag, T> supplier) {
        ExtraOfferType<T> tExtraOfferType = new ExtraOfferType<>(name, supplier);
        EXTRA_OFFER_TYPES.put(name, tExtraOfferType);
        return tExtraOfferType;
    }


    public static record ExtraOfferType<T extends ExtraOffer<?>>(String name, Function< CompoundTag, T> supplier) {
        public static final String EXTRA_OFFER = "extra_offer";

        @Nullable
        public static ExtraOfferType<?> getOfferType(String name){
            return EXTRA_OFFER_TYPES.get(name);
        }

        public T build(CompoundTag compoundTag) {
            return supplier.apply(compoundTag);
        }

        public String getName() {
            return name;
        }
    }
}
