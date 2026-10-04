package br.aetherworld.litrpg.progression;

import br.aetherworld.litrpg.component.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class RewardManager {
    private RewardManager() {}

    public static void grant(ServerPlayer player, int systemPoints, int experience, String reason) {
        ModComponents.LITRPG.get(player).addSystemPoints(systemPoints);
        ProgressionManager.addExperience(player, experience);
        player.displayClientMessage(
                Component.literal("+" + systemPoints + " PS  +" + experience + " XP  (" + reason + ")"),
                true
        );
    }
}
