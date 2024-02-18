package com.automaticalechoes.apprentice.mixin;

import com.automaticalechoes.apprentice.api.mixin.VillagerMixinInterface;
import com.automaticalechoes.apprentice.event.VillagerUpdateTradesEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffers;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Villager.class)
public abstract class VillagerMixin implements VillagerMixinInterface {
    @Shadow protected abstract void resendOffersToTradingPlayer();

    @Inject(method = "updateTrades",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/entity/npc/Villager;addOffersFromItemListings(Lnet/minecraft/world/item/trading/MerchantOffers;[Lnet/minecraft/world/entity/npc/VillagerTrades$ItemListing;I)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void updateTrades(CallbackInfo ci, VillagerData villagerdata, Int2ObjectMap int2objectmap, VillagerTrades.ItemListing[] avillagertrades$itemlisting, MerchantOffers merchantoffers){
        NeoForge.EVENT_BUS.post(new VillagerUpdateTradesEvent((Villager) (Object)this,merchantoffers));
    }

    @Override
    public void updateOffers() {
        resendOffersToTradingPlayer();
    }
}
