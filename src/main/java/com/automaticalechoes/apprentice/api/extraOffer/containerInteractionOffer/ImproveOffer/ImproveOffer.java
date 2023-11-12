package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ContainerInteractionOffer;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.Improve;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ImproveOffer extends ContainerInteractionOffer<ImproveOffer> implements Improve {
    protected final TagKey<Item> itemTag;
    protected final double extra;

    public ImproveOffer(ItemStack costA, TagKey<Item> itemTag ,double extra, ItemStack showCostB, ItemStack showResult) {
        super(costA, showCostB, showResult, Config.IMPROVE_MAX_USE.get(), 0, 0);
        this.itemTag = itemTag;
        this.extra = extra;
    }

    public ImproveOffer(CompoundTag p_45351_) {
        super(p_45351_);
        ResourceLocation resourcelocation = ResourceLocation.tryParse(p_45351_.getString(ITEM_TAG));
        this.itemTag =  TagKey.create(Registries.ITEM, resourcelocation);
        this.extra = p_45351_.getDouble(EXTRA);
    }


    @Override
    public abstract boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_);

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.putDouble(EXTRA, extra);
        tag.putString(ITEM_TAG, itemTag.location().toString());
        tag.putString(EXTRA_OFFER,getType().getName());
        return tag;
    }


}
