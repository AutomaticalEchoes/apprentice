package com.automaticalechoes.apprentice.api.extraOffer.interfaces;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.Merchant;

public interface Improve {
    String ITEM_TAG = "item_tag";
    String EXTRA = "extra";
    String MASTER_PIECE = "master_piece";
    String IMPROVED = "improved";

    default boolean shouldExtra(Merchant merchant){
        return merchant instanceof Villager abstractVillager && abstractVillager.hasCustomName() && ((Villager) merchant).getVillagerData().getLevel() >= 5;
    }
}
