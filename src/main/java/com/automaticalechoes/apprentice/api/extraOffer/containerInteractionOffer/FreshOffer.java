package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.api.ModelItem;
import com.automaticalechoes.apprentice.api.OfferUtils;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.ChangeAfterTrade;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.LevelUpChangeOffer;
import com.automaticalechoes.apprentice.config.Config;
import com.google.common.collect.Sets;
import io.netty.handler.ipfilter.IpSubnetFilter;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
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
import java.util.stream.Collectors;

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
        ItemStack itemStack = canUse(merchantContainer.getItem(0)) ? merchantContainer.getItem(0) : merchantContainer.getItem(1);
        Map<Enchantment, Integer> selections = EnchantmentHelper.getEnchantments(itemStack);
        if(selections.size() == 0) return Optional.empty();

        List<Map.Entry<Enchantment, Integer>> filterSelection = selections.entrySet().stream().filter(entry ->
                entry.getValue().doubleValue() / entry.getKey().getMaxLevel() <= this.leveLPercent).toList();
        if(filterSelection.size() == 0) return Optional.empty();

        int i = random % filterSelection.size();
        Enchantment finalSelection = filterSelection.get(i).getKey();

        Collection<Enchantment> values = ForgeRegistries.ENCHANTMENTS.getValues();
//        if(!itemStack.is(Items.ENCHANTED_BOOK)){
//            values = values.stream().filter(enchantment -> enchantment.canEnchant(itemStack)).toList();
//        }
        List<Enchantment> resultPool = values.stream().filter(enchantment -> !finalSelection.isCompatibleWith(enchantment)).toList();
        if(resultPool.size() == 0)
            return Optional.empty();

        Enchantment resultEnchantment = resultPool.get(random % resultPool.size());
        double present = selections.get(finalSelection).doubleValue() / finalSelection.getMaxLevel();
        int level = (int) Math.ceil(resultEnchantment.getMaxLevel() * present);
        selections.remove(finalSelection);
        selections.put(resultEnchantment,level);
        ItemStack copy = itemStack.copy();
        copy.removeTagKey("Enchantments");
        copy.removeTagKey("StoredEnchantments");
        EnchantmentHelper.setEnchantments(selections,copy);
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
                && canUse(p_45357_);
    }


    @Override
    public boolean appendHoverText(List<Component> p_41423_) {
        p_41423_.add(Component.translatable("apprentice.offer.cost_a").append(getCostA().getDisplayName()).append("x" + getCostA().getCount()).withStyle(ChatFormatting.DARK_RED));
        p_41423_.add(Component.translatable("apprentice.offer.fresh").append("LvMax." + (int)(leveLPercent * 5)).withStyle(ChatFormatting.DARK_GREEN));
        return true;
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
        this.getBaseCostA().setCount(OfferUtils.RandomFreshCost() + Config.FRESH_COST_LEVEL_BONUS.get() * level);
    }

    public boolean canUse(ItemStack itemStack){
        return EnchantmentHelper.getEnchantments(itemStack).size() > 0;
    }

}
