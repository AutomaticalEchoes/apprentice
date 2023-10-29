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
    public static final ModifierImprove ARMOR = new ModifierImprove(new ItemStack(Items.EMERALD, Config.IMPROVE_COST.get()), Tags.Items.ARMORS, OfferUtils.getRandomModifier(), ModelItem.ARMOR,ModelItem.ARMOR_IMPROVE);
    public static final ModifierImprove TOOL_OR_WEAPON = new ModifierImprove(new ItemStack(Items.EMERALD,Config.IMPROVE_COST.get()), Tags.Items.TOOLS,OfferUtils.getRandomModifier(),ModelItem.TOOL,ModelItem.TOOL_IMPROVE);
    public static final EnchantmentImprove ENCHANTMENT_IMPROVE = new EnchantmentImprove(new ItemStack(Items.EMERALD,Config.IMPROVE_COST.get()), Tags.Items.ENCHANTING_FUELS,OfferUtils.getRandomNumber(),ModelItem.ENCHANTABLE_EQUIP,ModelItem.ENCHANTED_ITEM);

    public static void init(){
        OFFERS.put(VillagerProfession.ARMORER, ARMOR);
        OFFERS.put(VillagerProfession.TOOLSMITH, TOOL_OR_WEAPON);
        OFFERS.put(VillagerProfession.WEAPONSMITH, TOOL_OR_WEAPON);
        OFFERS.put(VillagerProfession.LIBRARIAN,ENCHANTMENT_IMPROVE);
    }
}
