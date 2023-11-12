package com.automaticalechoes.apprentice.client;

import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.ImproveOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.RepairOffer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void OnItemToolTip(ItemTooltipEvent event){
        if(event.getItemStack().getOrCreateTag().contains(ImproveOffer.MASTER_PIECE)) {
            String string = event.getItemStack().getOrCreateTag().getString(ImproveOffer.MASTER_PIECE);
            event.getToolTip().add(Component.translatable("apprentice.offer.improve_by").append(Component.translatable(string)).withStyle(ChatFormatting.GRAY));
        }

        if(event.getItemStack().getOrCreateTag().contains(RepairOffer.PROFESSION_BONUS)){
            String string = event.getItemStack().getOrCreateTag().getString(RepairOffer.PROFESSION_BONUS);
            event.getToolTip().add(Component.translatable("apprentice.offer.profession_bonus").append(Component.translatable(string)).withStyle(ChatFormatting.GRAY));
        }

    }

}
