package br.com.aetherworld;
import br.com.aetherworld.block.AetherBlocks;
import br.com.aetherworld.dungeon.CryptGenerator;
import br.com.aetherworld.game.ProgressionService;
import br.com.aetherworld.item.AetherItems;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AetherWorld implements ModInitializer {
    public static final String MOD_ID = "aetherworld";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier id(String path) { return Identifier.fromNamespaceAndPath(MOD_ID, path); }

    @Override
    public void onInitialize() {
        AetherItems.initialize();
        AetherBlocks.initialize();
        ProgressionService.initialize();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("aetherworld")
                .then(Commands.literal("profile").executes(c -> {
                    ServerPlayer p = c.getSource().getPlayerOrException();
                    c.getSource().sendSuccess(() -> Component.literal(ProgressionService.summary(p)), false);
                    return 1;
                }))
                .then(Commands.literal("xp")
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1, 1000000))
                        .executes(c -> {
                            ServerPlayer p = c.getSource().getPlayerOrException();
                            ProgressionService.addExperience(p, IntegerArgumentType.getInteger(c, "amount"));
                            return 1;
                        })))
                .then(Commands.literal("crypt").executes(c -> {
                    ServerPlayer p = c.getSource().getPlayerOrException();
                    CryptGenerator.generate((ServerLevel) p.level(), p.blockPosition());
                    c.getSource().sendSuccess(() -> Component.literal("Cripta Subterrânea Rank E gerada."), true);
                    return 1;
                })));
        });
        LOGGER.info("AetherWorld LitRPG Core initialized");
    }
}
