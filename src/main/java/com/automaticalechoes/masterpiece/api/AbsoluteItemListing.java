package com.automaticalechoes.masterpiece.api;

import com.automaticalechoes.masterpiece.api.extraOffer.RecordOffer;
import com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.RepairOffer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public  class AbsoluteItemListing implements VillagerTrades.ItemListing {
    protected boolean isExtra = false;
    protected final BiFunction<Entity,RandomSource,MerchantOffer> offerFunc;
    public AbsoluteItemListing(BiFunction<Entity,RandomSource,MerchantOffer> offerFunc){
        this.offerFunc = offerFunc;
    }
    public AbsoluteItemListing IsExtra(boolean isExtra){
        this.isExtra = isExtra;
        return this;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity p_219693_, RandomSource p_219694_) {
        return offerFunc.apply(p_219693_,p_219694_);
    }

    public boolean isExtra(){
        return isExtra;
    }


    public static MerchantOffer RepairArmor(Entity p_219693_, RandomSource p_219694_) {
        return new RepairOffer(new ItemStack(Items.EMERALD, OfferUtils.RandomRepairCost()) , Tags.Items.ARMORS, ModelItem.ARMOR_BREAK, ModelItem.ARMOR);
    }

    public static MerchantOffer RepairTool(Entity p_219693_, RandomSource p_219694_){
        return new RepairOffer(new ItemStack(Items.EMERALD, OfferUtils.RandomRepairCost()), Tags.Items.TOOLS, ModelItem.TOOL_BREAK ,ModelItem.TOOL);
    }

    public static MerchantOffer Record(Entity p_219693_, RandomSource p_219694_){
        return new RecordOffer();
    }
}
