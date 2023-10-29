package com.automaticalechoes.apprentice.mixin;

import com.automaticalechoes.apprentice.api.extraOffer.ExtraOffer;
import com.automaticalechoes.apprentice.api.mixin.MerchantOffersMixinInterface;
import com.automaticalechoes.apprentice.api.extraOffer.ExtraOfferTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffers.class)
public class MerchantOffersMixin implements MerchantOffersMixinInterface {
    private boolean hasImproveTrade = false;
    @Inject(method = "createTag",at = @At("RETURN"))
    public void createTag(CallbackInfoReturnable<CompoundTag> callbackInfoReturnable){
        callbackInfoReturnable.getReturnValue().putBoolean("hasImproveTrade",hasImproveTrade);
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V",at = @At("RETURN"))
    public void init(CompoundTag tag, CallbackInfo ci){
        ((MerchantOffers)(Object)this).clear();
        reInit(tag);
    }

    public void reInit(CompoundTag compoundTag){
        this.hasImproveTrade = compoundTag.getBoolean("hasImproveTrade");
        ListTag listtag = compoundTag.getList("Recipes", 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compound = listtag.getCompound(i);
            if(compound.contains(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER)){
                ExtraOfferTypes.ExtraOfferType<? extends ExtraOffer<?>> offerType = ExtraOfferTypes.ExtraOfferType.getOfferType(compound.getString(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER));
                if(offerType != null) {
                    ((MerchantOffers)(Object)this).add(offerType.build(compound));
                }
            }else {
                ((MerchantOffers)(Object)this).add(new MerchantOffer(compound));
            }

        }
    }

    @Inject(method = "writeToStream",at = @At("HEAD"),cancellable = true)
    public void writeToStream(FriendlyByteBuf p_45394_,CallbackInfo callbackInfo){
        p_45394_.writeCollection((MerchantOffers)(Object)this, (friendlyByteBuf, merchantOffer) -> {
            friendlyByteBuf.writeNbt(merchantOffer.createTag());
        });
        callbackInfo.cancel();
    }

    @Inject(method = "createFromStream",at = @At("HEAD"),cancellable = true)
    private static void createFromStream(FriendlyByteBuf p_45396_, CallbackInfoReturnable<MerchantOffers> callbackInfo){
        MerchantOffers merchantOffers = new MerchantOffers();
        int i = p_45396_.readVarInt();
        for (int i1 = 0; i1 < i; i1++) {
            CompoundTag compound = p_45396_.readNbt();
            if(compound.contains(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER)){
                ExtraOfferTypes.ExtraOfferType<? extends ExtraOffer<?>> offerType = ExtraOfferTypes.ExtraOfferType.getOfferType(compound.getString(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER));
                if(offerType != null) {
                    merchantOffers.add(offerType.build(compound));
                }
            }else {
                merchantOffers.add(new MerchantOffer(compound));
            }
        }

        callbackInfo.setReturnValue(merchantOffers);
//        return p_45396_.readCollection(new , (p_220328_) -> {
//            ItemStack itemstack = p_220328_.readItem();
//            ItemStack itemstack1 = p_220328_.readItem();
//            ItemStack itemstack2 = p_220328_.readItem();
//            boolean flag = p_220328_.readBoolean();
//            int i = p_220328_.readInt();
//            int j = p_220328_.readInt();
//            int k = p_220328_.readInt();
//            int l = p_220328_.readInt();
//            float f = p_220328_.readFloat();
//            int i1 = p_220328_.readInt();
//            MerchantOffer merchantoffer = new MerchantOffer(itemstack, itemstack2, itemstack1, i, j, k, f, i1);
//            if (flag) {
//                merchantoffer.setToOutOfStock();
//            }
//
//            merchantoffer.setSpecialPriceDiff(l);
//            return merchantoffer;
//        });
    }

    @Override
    public Boolean hasImprove() {
        return this.hasImproveTrade;
    }

    @Override
    public void setHasImprove(Boolean b) {
        this.hasImproveTrade = b;
    }
}
