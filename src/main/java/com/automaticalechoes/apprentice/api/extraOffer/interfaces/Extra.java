package com.automaticalechoes.apprentice.api.extraOffer.interfaces;

import com.automaticalechoes.apprentice.api.extraOffer.ExtraOffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public interface Extra<T extends ExtraOffer<?>> {
    String EXTRA_OFFER = "extra_offer";
    Type<T> getType();
    CompoundTag createTag();
    default boolean appendHoverText(List<Component> p_41423_){
        return false;
    }

    record Type<T extends ExtraOffer<?>>(String name, Function< CompoundTag, T> supplier) {

        public T build(CompoundTag compoundTag) {
            return supplier.apply(compoundTag);
        }
        public String getName() {
            return name;
        }
    }
}
