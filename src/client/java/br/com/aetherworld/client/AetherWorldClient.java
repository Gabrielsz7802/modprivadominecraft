package br.com.aetherworld.client;
import br.com.aetherworld.AetherWorld;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
public final class AetherWorldClient implements ClientModInitializer{
 private static final KeyMapping OPEN_SYSTEM=KeyMappingHelper.registerKeyMapping(new KeyMapping("key.aetherworld.open_system",InputConstants.Type.KEYSYM,InputConstants.KEY_O,KeyMapping.Category.register(AetherWorld.id("controls"))));
 public void onInitializeClient(){ClientTickEvents.END_CLIENT_TICK.register(c->{while(OPEN_SYSTEM.consumeClick()&&c.player!=null)c.setScreen(null);});}
}