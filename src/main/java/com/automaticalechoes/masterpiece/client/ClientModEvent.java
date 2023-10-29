package com.automaticalechoes.masterpiece.client;

import com.automaticalechoes.masterpiece.MasterPiece;
import com.automaticalechoes.masterpiece.common.item.ItemRegister;
import com.automaticalechoes.masterpiece.common.item.WorkRecord;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT,modid = MasterPiece.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvent {

    @SubscribeEvent
    public static void RegisterItemColor(RegisterColorHandlersEvent.Item event){
        event.register((p_92672_, p_92673_) -> p_92673_ != 0? -1 : WorkRecord.GetColor(p_92672_), ItemRegister.WORK_RECORD.get());
    }


}
