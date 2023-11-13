package com.automaticalechoes.apprentice.mixin;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.api.AbsoluteItemListing;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(AbstractVillager.class)
public class AbstractVillagerMixin {
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
            at = @At(value = "INVOKE" ,shift = At.Shift.BEFORE, target = "Ljava/util/Set;iterator()Ljava/util/Iterator;" ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void addOffers(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_, CallbackInfo ci, Set<Integer> set){
        Set<Integer> extraSet = Sets.newHashSet();
        Integer[] values = set.toArray(Integer[]::new);
        for (int i = 0; i < p_35279_.length; i++) {
            if (set.contains(i) || !(p_35279_[i] instanceof AbsoluteItemListing absoluteItemListing)) continue;
            if (absoluteItemListing.isExtra()) {
                extraSet.add(i);
            }else {
                set.remove(values[Apprentice.RANDOM.nextInt(set.size())]);
                set.add(i);
            }
            set.addAll(extraSet);
        }
    }

}
