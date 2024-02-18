package com.automaticalechoes.apprentice;

import com.automaticalechoes.apprentice.api.ModelItem;
import com.automaticalechoes.apprentice.api.ProfessionStorages;
import com.automaticalechoes.apprentice.api.extraOffer.containerInteractionOffer.ImproveOffer.VillagerProfessionImproveOffer;
import com.automaticalechoes.apprentice.config.Config;
import com.automaticalechoes.apprentice.register.ItemRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Apprentice.MODID)
public class Apprentice
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "apprentice";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final Random RANDOM = new Random();

    public static final RandomSource RANDOM_SOURCE = RandomSource.create();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> APPRENTICE = CREATIVE_MODE_TABS.register("masterpiece", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("apprentice.itemGroup"))
            .icon(() -> ItemRegister.WORK_RECORD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (DeferredItem<? extends Item> modItem : ItemRegister.MOD_ITEMS) {
                    output.accept(modItem.get()); //
                }
            }).build());

    public Apprentice(IEventBus modEventBus)
    {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so items get registered
        ItemRegister.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
//        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC,"apprentice_common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModelItem.init();
        VillagerProfessionImproveOffer.init();
        ProfessionStorages.init();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
            event.accept(ItemRegister.WORK_RECORD.get());
    }



}
