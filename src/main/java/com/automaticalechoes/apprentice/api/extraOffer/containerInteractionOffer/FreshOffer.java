package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.api.ModelItem;
import com.automaticalechoes.apprentice.api.OfferUtils;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.ChangeAfterTrade;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.LevelUpChangeOffer;
import com.automaticalechoes.apprentice.config.Config;
import com.google.common.collect.Sets;
import io.netty.handler.ipfilter.IpSubnetFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class FreshOffer extends ContainerInteractionOffer<FreshOffer> implements ChangeAfterTrade, LevelUpChangeOffer {
    public static final String RANDOM = "random";
    public static final String LEVEL_PERCENT = "level_percent";
    protected int random = 0;
    protected int rewordXpMut = 1;
    protected double leveLPercent = 0.2;

    public FreshOffer(ItemStack costA) {
        super(costA, ModelItem.ENCHANTED_ITEM,ModelItem.ENCHANTED_CHANGE_ITEM, Config.FRESH_MAX_USE.get(), 1, 0.04F);
        random = Apprentice.RANDOM.nextInt(32);
    }

    public FreshOffer(CompoundTag p_45351_) {
        super(p_45351_);
        this.random = p_45351_.getInt(RANDOM);
        this.rewordXpMut = p_45351_.getInt(REWORD_XP_MUT);
        this.leveLPercent = p_45351_.getDouble(LEVEL_PERCENT);
    }

    @Override
    public Optional<ItemStack> interactionAssemble(MerchantContainer merchantContainer, Merchant merchant) {
        ItemStack itemStack = merchantContainer.getItem(0).is(Items.ENCHANTED_BOOK) ? merchantContainer.getItem(0) : merchantContainer.getItem(1);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
        if(enchantments.size() == 0)
            return Optional.empty();

        int i = random % enchantments.size();
        Enchantment[] array = enchantments.keySet().toArray(Enchantment[]::new);
        List<Enchantment> enchantments1 = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(enchantment -> array[i].isCompatibleWith(enchantment)).toList();
        if(enchantments1.size() == 0)
            return Optional.empty();

        Enchantment enchantment = enchantments1.get(random % enchantments1.size());
        long round = Math.round(enchantment.getMaxLevel() * leveLPercent);
        int min1 = Math.min((int) Math.max(1,round), enchantments.get(array[i]));
        enchantments.remove(array[i]);
        enchantments.put(enchantment,min1);
        ItemStack copy = itemStack.copy();
        copy.removeTagKey("Enchantments");
        copy.removeTagKey("StoredEnchantments");
        EnchantmentHelper.setEnchantments(enchantments,copy);
        return Optional.of(copy);
    }

    @Override
    public int getXp() {
        return super.getXp() * this.rewordXpMut;
    }

    @Override
    public CompoundTag createTag() {
        CompoundTag tag = super.createTag();
        tag.putInt(RANDOM,this.random);
        tag.putInt(REWORD_XP_MUT,rewordXpMut);
        tag.putDouble(LEVEL_PERCENT,leveLPercent);
        return tag;
    }

    @Override
    public boolean satisfiedBy(ItemStack p_45356_, ItemStack p_45357_) {
        return p_45356_.getItem() == this.getCostA().getItem() && p_45356_.getCount() >= this.getCostA().getCount()
                && p_45357_.is(Items.ENCHANTED_BOOK)  && EnchantedBookItem.getEnchantments(p_45357_).size() > 0;
    }

    @Override
    public Type<FreshOffer> getType() {
        return Types.FRESH_OFFER_TYPE;
    }

    @Override
    public void change(MerchantOffers offers, Villager villager) {
        this.random = Apprentice.RANDOM.nextInt(32);
    }

    @Override
    public void LevelUp(Villager villager, VillagerData villagerData, MerchantOffers merchantOffers) {
        int level = villagerData.getLevel();
        this.rewordXpMut = OfferUtils.GetXpPreLevel(level);
        this.leveLPercent = level / 5.0F;
    }
}
