package dev.biserman.takemybreathaway;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TakeMyBreathAway.MODID)
public class TakeMyBreathAway {
    public static final String MODID = "takemybreathaway";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TakeMyBreathAway(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onBreathe(LivingBreatheEvent event) {
        if (!Config.ENABLED_FOR_MOBS.get() && !(event.getEntity() instanceof Player)) {
            return;
        }

        if (event.getEntity() instanceof Player player && player.isCreative()) {
            return;
        }

        ResourceKey<Level> dim = event.getEntity().level().dimension();
        LOGGER.info("Player is in dimension: {}", dim.location());
        LOGGER.info("Dimensions to remove air from: {}", Config.DIMENSIONS.get());
        if (Config.DIMENSIONS.get().contains(dim.location().toString())) {
            LOGGER.info("Removing air from player");
            event.setCanBreathe(false);
        }
    }
}
