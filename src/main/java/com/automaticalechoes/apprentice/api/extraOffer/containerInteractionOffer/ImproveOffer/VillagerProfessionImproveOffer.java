package com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer;

import com.automaticalechoes.apprentice.api.ModelItem;
import com.automaticalechoes.apprentice.api.OfferUtils;
import com.automaticalechoes.apprentice.config.Config;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.HashMap;

public class VillagerProfessionImproveOffer {
    public static final HashMap<VillagerProfession,ImproveOffer> OFFERS = new HashMap<>();
    public static final ModifierImprove ARMOR = new ModifierImprove(new ItemStack(Items.EMERALD, OfferUtils.RandomImproveCost()), Tags.Items.ARMORS, OfferUtils.RandomModifier(), ModelItem.ARMOR,ModelItem.ARMOR_IMPROVE);
    public static final ModifierImprove TOOLS = new ModifierImprove(new ItemStack(Items.EMERALD, OfferUtils.RandomImproveCost()), Tags.Items.TOOLS, OfferUtils.RandomModifier(),ModelItem.TOOL,ModelItem.TOOL_IMPROVE);
    public static final ModifierImprove WEAPONS = new ModifierImprove(new ItemStack(Items.EMERALD, OfferUtils.RandomImproveCost()), OfferUtils.WEAPONS, OfferUtils.RandomModifier(), ModelItem.TOOL,ModelItem.TOOL_IMPROVE);
    public static final EnchantmentImprove ENCHANTMENT_IMPROVE = new EnchantmentImprove(new ItemStack(Items.EMERALD, OfferUtils.RandomImproveCost()), Tags.Items.ENCHANTING_FUELS, OfferUtils.RandomNumber(),ModelItem.ENCHANTABLE,ModelItem.ENCHANTED_ITEM);

    public static void init(){
        OFFERS.put(VillagerProfession.ARMORER, ARMOR);
        OFFERS.put(VillagerProfession.TOOLSMITH, TOOLS);
        OFFERS.put(VillagerProfession.WEAPONSMITH, WEAPONS);
        OFFERS.put(VillagerProfession.LIBRARIAN,ENCHANTMENT_IMPROVE);
    }
}
