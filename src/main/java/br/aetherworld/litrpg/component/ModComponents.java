package br.aetherworld.litrpg.component;

import net.minecraft.resources.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public final class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<LitRpgComponent> LITRPG =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    Identifier.fromNamespaceAndPath("aetherworld_litrpg", "litrpg"),
                    LitRpgComponent.class
            );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(
                LITRPG,
                LitRpgComponentImpl::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );
    }
}
