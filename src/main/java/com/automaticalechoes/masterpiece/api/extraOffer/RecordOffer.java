package com.automaticalechoes.masterpiece.api.extraOffer;

import com.automaticalechoes.masterpiece.MasterPiece;
import com.automaticalechoes.masterpiece.api.ModelItem;
import com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.ImproveOffer.ImproveOffer;
import com.automaticalechoes.masterpiece.common.item.WorkRecord;
import com.automaticalechoes.masterpiece.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.List;
import java.util.UUID;

public class RecordOffer extends ExtraOffer<RecordOffer> {
    public static final String RECORD_OFFER = "record_offer";
    private ItemStack offerRecordItem = ModelItem.RECORD_ITEM;
    public RecordOffer() {
        this(new ItemStack(Items.EMERALD, Config.RECORD_COST.get()));
    }

    public RecordOffer(ItemStack costA) {
        this(costA, Config.RECORD_MAX_USE.get(),0,0);
    }

    public RecordOffer(ItemStack costA, int maxUse , int xp , float priceMultiplier) {
        super(costA, ModelItem.RECORD_ITEM, maxUse, xp, priceMultiplier);
    }

    public RecordOffer(CompoundTag p_45351_) {
        super(p_45351_);
        this.offerRecordItem = ItemStack.of(p_45351_.getCompound(RECORD_OFFER));
    }

    @Override
    public ItemStack assemble() {
        return offerRecordItem.copy();
    }

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.put(RECORD_OFFER ,this.offerRecordItem.save(new CompoundTag()));
        tag.putString(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER,getType().getName());
        return tag;
    }

    public void change(MerchantOffers offers, Villager villager){
        List<MerchantOffer> merchantOffers = offers.stream().filter((offer) -> !(offer instanceof RecordOffer) && !(offer instanceof ImproveOffer)).toList();
        int i = MasterPiece.RANDOM.nextInt(merchantOffers.size());
        this.offerRecordItem = super.getResult().copy();
        VillagerProfession profession = villager.getVillagerData().getProfession();
        UUID uuid = villager.getUUID();
        WorkRecord.PutOffer(offerRecordItem, merchantOffers.get(i) , profession, uuid);
    }

    @Override
    public void appendHoverText(List<Component> p_41423_) {

    }

    @Override
    public ExtraOfferTypes.ExtraOfferType<RecordOffer> getType() {
        return ExtraOfferTypes.RECORD_OFFER_TYPE;
    }


}
