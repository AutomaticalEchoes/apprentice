package com.automaticalechoes.masterpiece.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.masterpiece.api.extraOffer.ExtraOfferTypes;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;

import java.util.Optional;

public class ModifierImprove extends ImproveOffer {
    public ModifierImprove(ItemStack costA, TagKey<Item> itemTag , double extra, ItemStack showCostB, ItemStack showResult) {
        super(costA,itemTag,extra,showCostB,showResult);
    }

    public ModifierImprove(CompoundTag p_45351_) {
        super(p_45351_);
    }

    @Override
    public Optional<ItemStack> interactionAssemble(MerchantContainer merchantContainer, Merchant merchant) {
        ItemStack copy = (merchantContainer.getItem(1).is(itemTag) ? merchantContainer.getItem(1) : merchantContainer.getItem(0)).copy();
        EquipmentSlot equipmentSlot = getEquipType(copy);
        Multimap<Attribute, AttributeModifier> attributeModifiers = copy.getAttributeModifiers(equipmentSlot);
        boolean shouldDoubleExtra = shouldExtra(merchant);
        double amountMut = shouldDoubleExtra ? 2 * extra : extra;
        attributeModifiers.forEach((attribute, attributeModifier) -> {
            double amount = attributeModifier.getAmount();
            double mutAmount = amount * (amount < 0 ? 1 - amountMut / 2 : 1 + amountMut);
            AttributeModifier attributeModifier1 = new AttributeModifier(attributeModifier.getId(),attributeModifier.getName(), mutAmount, attributeModifier.getOperation());
            copy.addAttributeModifier(attribute, attributeModifier1,equipmentSlot);
        });

        String workerName = shouldDoubleExtra ? ((AbstractVillager) merchant).getCustomName().getString() : "offer.unknown_worker";
        copy.getOrCreateTag().putString(MASTER_PIECE,workerName);
        copy.getOrCreateTag().putBoolean(IMPROVED , true);
        return Optional.of(copy);
    }

    @Override
    public boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_) {
        return p_45356_.getItem() == this.getCostA().getItem() && p_45356_.getCount() >= this.getCostA().getCount()
                && p_45357_.is(itemTag) && p_45357_.getCount() == 1  && !p_45357_.getOrCreateTag().contains(IMPROVED);
    }

    @Override
    public ExtraOfferTypes.ExtraOfferType<ImproveOffer> getType() {
        return ExtraOfferTypes.MODIFIER_IMPROVE_OFFER_TYPE;
    }

    public EquipmentSlot getEquipType(ItemStack itemStack){
        if (itemStack.getItem() instanceof Equipable equipable){
            return equipable.getEquipmentSlot();
        }else {
            return itemStack.getEquipmentSlot() != null ? itemStack.getEquipmentSlot() : EquipmentSlot.MAINHAND;
        }
    }


}
