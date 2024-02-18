package com.automaticalechoes.apprentice.client;

import com.automaticalechoes.apprentice.Apprentice;
import com.automaticalechoes.apprentice.common.item.WorkRecord;
import com.automaticalechoes.apprentice.register.ItemRegister;
import net.minecraft.client.color.item.ItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT,modid = Apprentice.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvent {

    @SubscribeEvent
    public static void RegisterItemColor(RegisterColorHandlersEvent.Item event){
        event.register((ItemColor) (p_92672_, p_92673_) -> p_92673_ != 0? -1 : WorkRecord.GetColor(p_92672_), ItemRegister.WORK_RECORD.get());
    }


}
