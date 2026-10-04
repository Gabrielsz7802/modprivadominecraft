package br.aetherworld.litrpg;

import br.aetherworld.litrpg.entity.ModEntities;
import br.aetherworld.litrpg.item.ModItems;
import br.aetherworld.litrpg.network.ModNetworking;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AetherWorldMod implements ModInitializer {
    public static final String MOD_ID = "aetherworld_litrpg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.init();
        ModEntities.init();
        ModNetworking.init();
        LOGGER.info("AetherWorld LiteRPG carregado.");
    }
}
