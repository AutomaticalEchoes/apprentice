package com.automaticalechoes.apprentice.api.extraOffer.interfaces;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.trading.MerchantOffers;

public interface LevelUpChangeOffer {
    String REWORD_XP_MUT = "reword_xp_mut";
    void LevelUp(Villager villager, VillagerData villagerData, MerchantOffers merchantOffers);
}
