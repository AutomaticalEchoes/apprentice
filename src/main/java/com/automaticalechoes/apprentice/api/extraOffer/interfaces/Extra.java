package com.automaticalechoes.apprentice.api.extraOffer.interfaces;

import com.automaticalechoes.apprentice.api.extraOffer.ExtraOffer;
import com.automaticalechoes.apprentice.api.extraOffer.ExtraOfferTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface Extra<T extends ExtraOffer<?>> {
    ExtraOfferTypes.ExtraOfferType<T> getType();
    CompoundTag createTag();
    void appendHoverText(List<Component> p_41423_);
}
