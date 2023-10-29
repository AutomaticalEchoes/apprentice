package com.automaticalechoes.apprentice.api.extraOffer;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.trading.MerchantOffers;

public interface LevelUpChangeOffer {
    void LevelUp(Villager villager, VillagerData villagerData, MerchantOffers merchantOffers);
}
