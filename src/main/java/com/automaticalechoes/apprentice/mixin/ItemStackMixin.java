package com.automaticalechoes.apprentice.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Redirect(method = "getTooltipLines",  at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;"))
    private UUID injected(AttributeModifier instance) {
        if(instance.getId().equals(ItemMixin.getBaseAttackDamageUUID())){
            return ItemMixin.getBaseAttackDamageUUID();
        }else if(instance.getId().equals(ItemMixin.getBaseAttackSpeedUUID())){
            return ItemMixin.getBaseAttackSpeedUUID();
        }
        return instance.getId();
    }
}
