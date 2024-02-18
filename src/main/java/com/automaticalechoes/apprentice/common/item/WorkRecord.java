package com.automaticalechoes.apprentice.common.item;

import com.automaticalechoes.apprentice.api.extraOffer.ExtraOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.FreshOffer;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.RepairOffer;
import com.automaticalechoes.apprentice.api.extraOffer.interfaces.Extra;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
            String path = BuiltInRegistries.VILLAGER_PROFESSION.getKey(villager.getVillagerData().getProfession()).getPath();
            boolean hasProfessionValid =  !p_41398_.getOrCreateTag().contains(PROFESSION_PATH) || p_41398_.getOrCreateTag().getString(PROFESSION_PATH).equals(path);
            boolean hasUUIDValid = !p_41398_.getOrCreateTag().contains(SOURCE) || !p_41398_.getOrCreateTag().getUUID(SOURCE).equals(p_41400_.getUUID());
            if(merchantOffer != null && hasProfessionValid && hasUUIDValid){
                villager.getOffers().add(merchantOffer);
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
            p_41423_.add(Component.translatable("apprentice.offer.cost_a").append(merchantOffer.getCostA().getDisplayName()).append("x" + merchantOffer.getCostA().getCount()).withStyle(ChatFormatting.DARK_RED));
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
            if(profession != null && !(offer instanceof RepairOffer) && !(offer instanceof FreshOffer)) tag.putString(PROFESSION_PATH, BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession).getPath());
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

    public static Component getTypeName(String Path) {
        return Component.translatable(EntityType.VILLAGER.getDescriptionId() + '.' + Path);
    }
}
