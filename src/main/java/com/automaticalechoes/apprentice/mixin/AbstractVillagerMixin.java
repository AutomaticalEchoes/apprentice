package com.automaticalechoes.apprentice.mixin;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.api.AbsoluteItemListing;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Set;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {
    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> p_146738_, Level p_146739_) {
        super(p_146738_, p_146739_);
    }
//    @Inject(method = "addOffersFromItemListings",
//            at = @At(value = "INVOKE" ,shift = At.Shift.BEFORE, target = "Ljava/util/Set;size()I" ),
//            locals = LocalCapture.CAPTURE_FAILHARD)
//    private void addOffers(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_, CallbackInfo ci, Set<Integer> set){
//        for (int i = 0; i < p_35279_.length; i++) {
//            if (!(p_35279_[i] instanceof AbsoluteItemListing absoluteItemListing)) continue;
//            if (!absoluteItemListing.isExtra()) {
//                set.add(i);
//            }
//        }
//    }

    @Inject(method = "addOffersFromItemListings",
            at = @At("RETURN"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void addOffers(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_, CallbackInfo ci,  ArrayList<VillagerTrades.ItemListing> arraylist){
        for (VillagerTrades.ItemListing itemListing : arraylist) {
            if(itemListing instanceof AbsoluteItemListing absoluteItemListing){
                if(absoluteItemListing.isExtra()){
                    p_35278_.add(absoluteItemListing.getOffer((AbstractVillager)(Object)this, this.random));
                }
            }
        }
    }

}
