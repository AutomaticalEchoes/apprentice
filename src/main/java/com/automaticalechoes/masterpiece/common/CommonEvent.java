package com.automaticalechoes.masterpiece.common;

import com.automaticalechoes.masterpiece.api.*;
import com.automaticalechoes.masterpiece.api.extraOffer.LevelUpChangeOffer;
import com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.ImproveOffer.ImproveOffer;
import com.automaticalechoes.masterpiece.api.extraOffer.RecordOffer;
import com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.RepairOffer;
import com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.ImproveOffer.VillagerProfessionImproveOffer;
import com.automaticalechoes.masterpiece.api.mixin.MerchantOffersMixinInterface;
import com.automaticalechoes.masterpiece.api.mixin.VillagerMixinInterface;
import com.automaticalechoes.masterpiece.config.Config;
import com.automaticalechoes.masterpiece.event.VillagerUpdateTradesEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber
public class CommonEvent {

    @SubscribeEvent
    public static void VillagerTrades(VillagerTradesEvent event){
        event.getTrades().get(5).add(new AbsoluteItemListing(AbsoluteItemListing::Record).IsExtra(true));
        if(event.getType() == VillagerProfession.ARMORER){
            event.getTrades().get(1).add(new AbsoluteItemListing(AbsoluteItemListing::RepairArmor).IsExtra(true));
        }else if(event.getType() == VillagerProfession.TOOLSMITH || event.getType() == VillagerProfession.WEAPONSMITH){
            event.getTrades().get(1).add(new AbsoluteItemListing(AbsoluteItemListing::RepairTool).IsExtra(true));
        }
    }

    @SubscribeEvent
    public static void OnVillagerUpdateTrades(VillagerUpdateTradesEvent event){
        int level = event.getVillagerData().getLevel();
        if(event.getMerchantOffers().size() > 20 && level >= 5 && !((MerchantOffersMixinInterface)event.getMerchantOffers()).hasImprove()){
            ImproveOffer improveOffer = VillagerProfessionImproveOffer.OFFERS.get(event.getVillagerData().getProfession());
            if(improveOffer != null){
                event.getMerchantOffers().add(improveOffer);
                ((MerchantOffersMixinInterface)event.getMerchantOffers()).setHasImprove(true);
            }
        }

        Optional<MerchantOffer> first = event.getMerchantOffers().stream().filter(offer -> offer instanceof RecordOffer).findFirst();
        first.ifPresent(merchantOffer -> {
            RecordOffer recordOffer = (RecordOffer) merchantOffer;
            recordOffer.change(event.getMerchantOffers(), event.getVillager());
        });


        LevelUpChangeOffer[] Offers = event.getMerchantOffers().stream()
                .filter((offer) -> offer instanceof LevelUpChangeOffer)
                .toArray(value -> new LevelUpChangeOffer[value]);

        if(Offers.length > 0){
            for (LevelUpChangeOffer levelUpChangeOffer : Offers) {
                levelUpChangeOffer.LevelUp(event.getVillager(), event.getVillagerData(), event.getMerchantOffers());
            }
        }
    }

    @SubscribeEvent
    public static void OnVillagerTradeAction(TradeWithVillagerEvent event){
        if(event.getAbstractVillager() instanceof Villager villager
                && event.getMerchantOffer() instanceof RecordOffer recordOffer
                && event.getEntity() instanceof ServerPlayer serverPlayer){
            recordOffer.change(event.getAbstractVillager().getOffers(),villager);
            ((VillagerMixinInterface)villager).updateOffers();
        }
    }





}
