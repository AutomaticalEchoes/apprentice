package com.automaticalechoes.masterpiece.mixin;

import com.automaticalechoes.masterpiece.MasterPiece;
import com.automaticalechoes.masterpiece.api.AbsoluteItemListing;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(AbstractVillager.class)
public class AbstractVillagerMixin {
    @Inject(method = "addOffersFromItemListings",
            at = @At(value = "INVOKE" ,shift = At.Shift.BEFORE, target = "Ljava/util/Set;size()I" ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void addOffers(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_, CallbackInfo ci, Set<Integer> set){
        for (int i = 0; i < p_35279_.length; i++) {
            if(p_35279_[i] instanceof AbsoluteItemListing){
                set.add(i);
            }
        }
    }

}
