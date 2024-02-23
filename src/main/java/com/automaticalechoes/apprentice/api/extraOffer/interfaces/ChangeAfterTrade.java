package com.automaticalechoes.apprentice.api.extraOffer.interfaces;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffers;

public interface ChangeAfterTrade {
    void change(MerchantOffers offers, Villager villager);
}
