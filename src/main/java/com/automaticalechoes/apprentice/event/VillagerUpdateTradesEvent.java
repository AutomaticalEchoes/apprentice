package com.automaticalechoes.apprentice.event;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.eventbus.api.Event;

public class VillagerUpdateTradesEvent extends Event {
    private final Villager villager;
    private final MerchantOffers merchantOffers;
    private final VillagerData villagerData;

    public VillagerUpdateTradesEvent(Villager villager,MerchantOffers merchantOffers){
        this.villager = villager;
        this.merchantOffers = merchantOffers;
        this.villagerData = villager.getVillagerData();
    }

    public MerchantOffers getMerchantOffers() {
        return merchantOffers;
    }

    public Villager getVillager() {
        return villager;
    }

    public VillagerData getVillagerData() {
        return villagerData;
    }
}
