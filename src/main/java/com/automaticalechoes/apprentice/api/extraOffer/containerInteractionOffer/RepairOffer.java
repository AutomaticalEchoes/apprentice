package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer;

import com.automaticalechoes.apprentice.api.OfferUtils;
import com.automaticalechoes.apprentice.api.TagKeyMap;
import com.automaticalechoes.apprentice.api.extraOffer.ExtraOfferTypes;
import com.automaticalechoes.apprentice.api.extraOffer.LevelUpChangeOffer;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.List;
import java.util.Optional;

public class RepairOffer extends ContainerInteractionOffer<RepairOffer> implements LevelUpChangeOffer {
    public static final String REPAIR_VALUE = "repair_value";
    public static final String ITEM_TAG = "item_tag";
    public static final String REWORD_XP_MUT = "reword_xp_mut";
    protected final TagKey<Item> itemTag;
    protected int repairValuePerCost;
    protected int rewordXpMut;
    public RepairOffer(ItemStack costA,TagKey<Item> itemTag, ItemStack showBreak, ItemStack showRepair) {
        super(costA,showBreak, showRepair,
                Config.RECORD_MAX_USE.get(), Config.REPAIR_VILLAGER_XP.get(), 0.04F);
        this.itemTag = itemTag;
        this.repairValuePerCost = Config.REPAIR_BASE_PRE_COST.get();
        this.rewordXpMut = 1;
    }

    public RepairOffer(CompoundTag p_45351_) {
        super(p_45351_);
        this.itemTag = TagKeyMap.getTag(p_45351_.getString(ITEM_TAG));
        this.repairValuePerCost =  p_45351_.getInt(REPAIR_VALUE);
        this.rewordXpMut = p_45351_.getInt(REWORD_XP_MUT);
    }

    @Override
    public Optional<ItemStack> interactionAssemble(MerchantContainer merchantContainer, Merchant merchant) {
        ItemStack copy = (merchantContainer.getItem(1).is(itemTag) ?
                merchantContainer.getItem(1) : merchantContainer.getItem(0)).copy();
        copy.setDamageValue((int) (copy.getDamageValue() - repairValuePerCost * getCostA().getCount()));
        return Optional.of(copy);
    }

    @Override
    public boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_) {
        return p_45356_.is(Items.EMERALD) && p_45356_.getCount() >= this.getCostA().getCount() && p_45357_.is(itemTag) && p_45357_.getCount() ==1;
    }

    @Override
    public int getXp() {
        return super.getXp() * rewordXpMut;
    }

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.putInt(REPAIR_VALUE,repairValuePerCost);
        tag.putInt(REWORD_XP_MUT,rewordXpMut);
        tag.putString(ITEM_TAG, TagKeyMap.getKey(itemTag));
        tag.putString(ExtraOfferTypes.ExtraOfferType.EXTRA_OFFER,getType().getName());
        return tag;
    }

    @Override
    public void appendHoverText(List<Component> p_41423_) {
        p_41423_ .add(Component.translatable("apprentice.offer.cost_a").append(getCostA().getDisplayName()).append("x" + getCostA().getCount()).withStyle(ChatFormatting.DARK_RED));
        p_41423_.add(Component.translatable("apprentice.offer.fix_type").append(Component.translatable(itemTag.location().toShortLanguageKey())).withStyle(ChatFormatting.DARK_GREEN));
        p_41423_.add(Component.translatable("apprentice.offer.fix").append(String.valueOf(repairValuePerCost * getCostA().getCount())).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public ExtraOfferTypes.ExtraOfferType<RepairOffer> getType() {
        return ExtraOfferTypes.REPAIR_OFFER_TYPE;
    }

    @Override
    public void LevelUp(Villager villager, VillagerData villagerData , MerchantOffers merchantOffers) {
        int level = villagerData.getLevel();
        this.repairValuePerCost = (int) (Config.REPAIR_BASE_PRE_COST.get() * (1 +   level * Config.REPAIR_MUT_PRE_LEVEL.get()));
        this.rewordXpMut = OfferUtils.GetXpPreLevel(level);
    }
}
