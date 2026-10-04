package br.aetherworld.litrpg.mixin;

import br.aetherworld.litrpg.progression.ProgressionManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void aetherworld$playerTick(CallbackInfo ci) {
        ProgressionManager.tick((Player) (Object) this);
    }
}
