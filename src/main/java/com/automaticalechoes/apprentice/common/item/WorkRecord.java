package com.automaticalechoes.apprentice.common.item;

import com.automaticalechoes.apprentice.api.extraOffer.ExtraOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.FreshOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.RepairOffer;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.Extra;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public class WorkRecord extends Item {
    public static final String OFFER = "offer";
    public static final String COLOR = "color";
    public static final String MARK = "mark";
    public static final String PROFESSION_PATH = "profession";
    public static final String SOURCE = "source";
    public WorkRecord(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        if(p_41400_ instanceof Villager villager){
            MerchantOffer merchantOffer = GetOffer(p_41398_);
            String path = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(villager.getVillagerData().getProfession()).getPath();
            boolean hasProfessionValid =  !p_41398_.getOrCreateTag().contains(PROFESSION_PATH) || p_41398_.getOrCreateTag().getString(PROFESSION_PATH).equals(path);
            boolean hasUUIDValid = !p_41398_.getOrCreateTag().contains(SOURCE) || !p_41398_.getOrCreateTag().getUUID(SOURCE).equals(p_41400_.getUUID());
            if(merchantOffer != null && hasProfessionValid && hasUUIDValid){
                AddOrMerge(villager.getOffers(), merchantOffer);
                p_41398_.shrink(1);
                villager.handleEntityEvent((byte) 14);
                villager.playSound(SoundEvents.VILLAGER_YES);
                villager.playSound(SoundEvents.PLAYER_LEVELUP);
                return InteractionResult.SUCCESS;
            }
            villager.playSound(SoundEvents.VILLAGER_AMBIENT);
            villager.setUnhappyCounter(40);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(p_41398_, p_41399_, p_41400_, p_41401_);
    }



    @Override
    public void appendHoverText(ItemStack p_41421_, @org.jetbrains.annotations.Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        MerchantOffer merchantOffer = GetOffer(p_41421_);
        if(!p_41421_.getOrCreateTag().contains(MARK)) {
            p_41423_.add(Component.translatable("apprentice.offer.unknown").withStyle(ChatFormatting.GRAY));
        }else if(merchantOffer instanceof ExtraOffer extraOffer && extraOffer.appendHoverText(p_41423_)){
           return;
        }else if(merchantOffer != null){
            p_41423_.add(Component.translatable("apprentice.offer.cost_a").append(merchantOffer.getBaseCostA().getDisplayName()).append("x" + merchantOffer.getBaseCostA().getCount()).withStyle(ChatFormatting.DARK_RED));
            if(!merchantOffer.getCostB().isEmpty())
                p_41423_.add(Component.translatable("apprentice.offer.empty").append(merchantOffer.getCostB().getDisplayName()).append("x" + merchantOffer.getCostB().getCount()).withStyle(ChatFormatting.DARK_RED));
            ItemStack result = merchantOffer.getResult();
            p_41423_.add(Component.translatable("apprentice.offer.result").append(result.getDisplayName()).append("x" + result.getCount()).withStyle(ChatFormatting.GREEN));
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(result);
            if(enchantments.size() > 0){
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Component fullname = entry.getKey().getFullname(entry.getValue());
                    p_41423_.add(Component.translatable("apprentice.offer.empty").append("|").append(fullname).withStyle(ChatFormatting.DARK_PURPLE));
                }
            }

            if(p_41421_.getOrCreateTag().contains(PROFESSION_PATH))
                p_41423_.add(Component.translatable("apprentice.offer.profession").append(getTypeName(p_41421_.getOrCreateTag().getString(PROFESSION_PATH))).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }

    public static boolean PutOffer(ItemStack itemStack, MerchantOffer offer, @Nullable VillagerProfession profession , @Nullable UUID uuid){
        if(itemStack.getItem() instanceof WorkRecord && !itemStack.getOrCreateTag().contains(OFFER)){
            CompoundTag tag = itemStack.getOrCreateTag();
            tag.put(OFFER,offer.createTag());
            tag.putInt(COLOR,offer.hashCode());
            tag.putBoolean(MARK,true);
            if(profession != null && !(offer instanceof RepairOffer) && !(offer instanceof FreshOffer)) tag.putString(PROFESSION_PATH, ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession).getPath());
            if(uuid != null) tag.putUUID(SOURCE,uuid);
            return true;
        }
        return false;
    }

    public static int GetColor(ItemStack itemStack){
        return itemStack.getOrCreateTag().contains(COLOR) ? itemStack.getOrCreateTag().getInt(COLOR) : -1;
    }

    @Nullable
    public static MerchantOffer GetOffer(ItemStack itemStack){
        if(itemStack.getItem() instanceof WorkRecord && itemStack.getOrCreateTag().contains(OFFER)){
            CompoundTag compound = itemStack.getOrCreateTag().getCompound(OFFER);
            if(compound.contains(Extra.EXTRA_OFFER)){
                Extra.Type<? extends ExtraOffer<?>> offerType = ExtraOffer.Types.getOfferType(compound.getString(ExtraOffer.EXTRA_OFFER));
                if(offerType != null) {
                    return offerType.build(compound);
                }
            }else {
                return new MerchantOffer(compound);
            }
        }
        return null;
    }

    public static void AddOrMerge(MerchantOffers offers, MerchantOffer merchantOffer){
        MerchantOffer offer = merchantOffer;
        Optional<MerchantOffer> sameMerchant = getSameMerchant(offers, merchantOffer);
        if(sameMerchant.isEmpty()) {
            offers.add(offer);
            return;
        }
        MerchantOffer recipeFor = sameMerchant.get();
        offers.remove(recipeFor);
        int numA = Math.min(merchantOffer.getBaseCostA().getCount(), recipeFor.getBaseCostA().getCount());
        int numB = Math.min(merchantOffer.getCostB().getCount(), recipeFor.getCostB().getCount());
        int numResult = Math.max(merchantOffer.getResult().getCount(), recipeFor.getResult().getCount());
        CompoundTag tag = recipeFor.createTag();
        int maxUses = tag.contains("maxUses", 99) ? (int) Math.ceil(tag.getInt("maxUses") * 1.2) : 4;
        tag.put("buy",recipeFor.getCostA().copyWithCount(numA).save(new CompoundTag()));
        tag.put("buyB", recipeFor.getCostB().copyWithCount(numB).save(new CompoundTag()));
        tag.put("sell", recipeFor.getResult().copyWithCount(numResult).save(new CompoundTag()));
        tag.putInt("maxUses", Math.min(maxUses, 30));
        offer = recipeFor instanceof Extra extra ? extra.getType().build(tag) : new MerchantOffer(tag);
        offers.add(offer);
    }

    public static Optional<MerchantOffer> getSameMerchant(MerchantOffers offers, MerchantOffer merchantOffer){
        ItemStack costA = merchantOffer.getBaseCostA();
        ItemStack costB = merchantOffer.getCostB();
        ItemStack result = merchantOffer.getResult();
        Extra.Type type = merchantOffer instanceof Extra extra? extra.getType() : null;
        return offers.stream().filter(merchantOffer1 -> {
            if((type == null && merchantOffer1 instanceof Extra) ^ (type != null && !(merchantOffer1 instanceof Extra))) return false;
            if(type != null && !type.equals(((Extra<?>) merchantOffer1).getType())) return false;
            if(!costA.copyWithCount(1).equals(merchantOffer1.getBaseCostA().copyWithCount(1), true)) return false;
            if(!costB.copyWithCount(1).equals(merchantOffer1.getCostB().copyWithCount(1), true)) return false;
            return result.copyWithCount(1).equals(merchantOffer1.getResult().copyWithCount(1), true);
        }).findFirst();
    }

    public static Component getTypeName(String Path) {
        return Component.translatable(EntityType.VILLAGER.getDescriptionId() + '.' + Path);
    }
}
