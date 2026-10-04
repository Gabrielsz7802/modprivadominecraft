package br.aetherworld.litrpg.client;

import br.aetherworld.litrpg.AetherWorldMod;
import br.aetherworld.litrpg.entity.ModEntities;
import br.aetherworld.litrpg.network.PerceptionPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class AetherWorldClient implements ClientModInitializer {
    private static final KeyMapping.Category AETHERWORLD_CATEGORY =
            KeyMapping.Category.register(
                    Identifier.fromNamespaceAndPath(AetherWorldMod.MOD_ID, "controls")
            );

    public static final KeyMapping SYSTEM_KEY = new KeyMapping(
            "key.aetherworld_litrpg.system",
            GLFW.GLFW_KEY_O,
            AETHERWORLD_CATEGORY
    );

    public static final KeyMapping PERCEPTION_KEY = new KeyMapping(
            "key.aetherworld_litrpg.perception",
            GLFW.GLFW_KEY_P,
            AETHERWORLD_CATEGORY
    );

    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(SYSTEM_KEY);
        KeyMappingHelper.registerKeyMapping(PERCEPTION_KEY);

        EntityRenderers.register(ModEntities.RIFT_CRAWLER, ZombieRenderer::new);
        EntityRenderers.register(ModEntities.FALLEN_RUNE_SENTINEL, ZombieRenderer::new);
        EntityRenderers.register(ModEntities.CRYPT_GUARDIAN, ZombieRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (SYSTEM_KEY.consumeClick()) {
                client.setScreen(new SystemScreen());
            }

            while (PERCEPTION_KEY.consumeClick()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(new PerceptionPayload(0));
                }
            }
        });
    }
}
