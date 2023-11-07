package com.automaticalechoes.apprentice.mixin;

import com.automaticalechoes.apprentice.api.mixin.ProfessionStorages;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {
    @Shadow @Final private Merchant merchant;
    @Inject(method = "onTake",
            at = @At(value = "INVOKE" ,shift = At.Shift.BEFORE, target = "Lnet/minecraft/world/item/trading/MerchantOffer;take(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z" ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onTake(Player player, ItemStack  p_150632_, CallbackInfo callbackInfo, MerchantOffer merchantOffer,ItemStack itemStack,ItemStack itemStack1){
        if(merchant instanceof Villager villager) {
            if (merchantOffer.satisfiedBy(itemStack, itemStack1)) {
                Storage(itemStack,itemStack1,villager,merchantOffer);
            } else if (merchantOffer.satisfiedBy(itemStack1, itemStack)) {
                Storage(itemStack1,itemStack,villager,merchantOffer);
            }
        }
    }


    private void Storage(ItemStack itemStack , ItemStack itemStack1, Villager villager ,MerchantOffer merchantOffer){
        VillagerProfession profession = villager.getVillagerData().getProfession();
        if(ProfessionStorages.ShouldStorages(profession,itemStack)){
            villager.getInventory().addItem(itemStack.copyWithCount(merchantOffer.getCostA().getCount()));
        }
        if(!merchantOffer.getCostB().isEmpty() && ProfessionStorages.ShouldStorages(profession,itemStack1)){
            villager.getInventory().addItem(itemStack.copyWithCount(merchantOffer.getCostB().getCount()));
        }
    }
}
