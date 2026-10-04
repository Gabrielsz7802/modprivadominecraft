package br.com.aetherworld;
import br.com.aetherworld.block.AetherBlocks;
import br.com.aetherworld.dungeon.CryptGenerator;
import br.com.aetherworld.game.ProgressionService;
import br.com.aetherworld.item.AetherItems;
import com.mojang.brigadier.arguments.*;
import net.minecraft.commands.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.*;
public final class AetherWorld implements ModInitializer{
 public static final String MOD_ID="aetherworld"; public static final Logger LOGGER=LoggerFactory.getLogger(MOD_ID);
 public static Identifier id(String p){return Identifier.fromNamespaceAndPath(MOD_ID,p);}
 @Override public void onInitialize(){AetherItems.initialize();AetherBlocks.initialize();ProgressionService.initialize();registerCommands();LOGGER.info("AetherWorld LitRPG Core initialized");}
 private static void registerCommands(){CommandRegistrationCallback.EVENT.register((d,r,e)->d.register(Commands.literal("aetherworld")
  .then(Commands.literal("profile").executes(c->{ServerPlayer p=c.getSource().getPlayerOrException();c.getSource().sendSuccess(()->Component.literal(ProgressionService.summary(p)),false);return 1;}))
  .then(Commands.literal("xp").then(Commands.argument("amount",IntegerArgumentType.integer(1,1000000)).executes(c->{ServerPlayer p=c.getSource().getPlayerOrException();ProgressionService.addExperience(p,IntegerArgumentType.getInteger(c,"amount"));return 1;})))
  .then(Commands.literal("crypt").executes(c->{ServerPlayer p=c.getSource().getPlayerOrException();CryptGenerator.generate(p.serverLevel(),p.blockPosition());c.getSource().sendSuccess(()->Component.literal("Cripta Subterrânea Rank E gerada."),true);return 1;}))));}
}