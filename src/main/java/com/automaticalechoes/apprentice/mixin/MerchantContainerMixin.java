package com.automaticalechoes.apprentice.mixin;


import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ContainerInteractionOffer;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(MerchantContainer.class)
public abstract class MerchantContainerMixin {
    @Shadow @Nullable private MerchantOffer activeOffer;

    @Shadow public abstract void setItem(int p_40013_, ItemStack p_40014_);

    @Shadow @Final private Merchant merchant;

    @Shadow public abstract ItemStack getItem(int p_40008_);

    @Inject(method = "updateSellItem",  at = {@At("RETURN")})
    public void updateSellItem(CallbackInfo callbackInfo){
        if(activeOffer instanceof ContainerInteractionOffer offer && !getItem(2).isEmpty()){
            Optional<ItemStack> optional = offer.interactionAssemble((MerchantContainer) (Object) this, merchant);
            optional.ifPresentOrElse(itemStack -> setItem(2, itemStack), () -> {
                activeOffer = null;
                setItem(2,ItemStack.EMPTY);
            });
            this.merchant.notifyTradeUpdated(this.getItem(2));
        }
    }
}
