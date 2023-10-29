package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.apprentice.api.TagKeyMap;
import com.automaticalechoes.apprentice.api.extraOffer.ExtraOfferTypes;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ContainerInteractionOffer;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;

import java.util.List;

public abstract class ImproveOffer extends ContainerInteractionOffer<ImproveOffer> {

    public static final String ITEM_TAG = "item_tag";
    public static final String EXTRA = "extra";
    public static final String MASTER_PIECE = "master_piece";
    public static final String IMPROVED = "improved";
    protected final TagKey<Item> itemTag;
    protected final double extra;

    public ImproveOffer(ItemStack costA, TagKey<Item> itemTag ,double extra, ItemStack showCostB, ItemStack showResult) {
        super(costA, showCostB, showResult, Config.IMPROVE_MAX_USE.get(), 0, 0);
        this.itemTag = itemTag;
        this.extra = extra;
    }

    public ImproveOffer(CompoundTag p_45351_) {
        super(p_45351_);
        this.itemTag = TagKeyMap.getTag(p_45351_.getString(ITEM_TAG));
        this.extra = p_45351_.getDouble(EXTRA);
    }


    @Override
    public abstract boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_);

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.putDouble(EXTRA, extra);
        tag.putString(ITEM_TAG, TagKeyMap.getKey(itemTag));
        tag.putString(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER,getType().getName());
        return tag;
    }

    @Override
    public abstract ExtraOfferTypes.ExtraOfferType<ImproveOffer> getType();

    @Override
    public void appendHoverText(List<Component> p_41423_) {

    }

    public boolean shouldExtra(Merchant merchant){
        return merchant instanceof Villager abstractVillager && abstractVillager.hasCustomName() && ((Villager) merchant).getVillagerData().getLevel() >= 5;
    }


}
