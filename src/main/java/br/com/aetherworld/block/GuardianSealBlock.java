package br.com.aetherworld.block;
import br.com.aetherworld.data.*;
import br.com.aetherworld.game.ProgressionService;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
public class GuardianSealBlock extends Block{
 public GuardianSealBlock(Properties p){super(p);}
 @Override protected InteractionResult useWithoutItem(BlockState s,Level l,BlockPos pos,Player p,net.minecraft.core.Direction f,net.minecraft.world.phys.BlockHitResult h){if(!l.isClientSide()&&p instanceof ServerPlayer sp){ProgressionService.addExperience(sp,150);var x=AetherData.get(sp);AetherData.set(sp,new PlayerProfile(x.level(),x.expCurrent(),x.expMax(),x.freeAttributePoints(),x.ps()+6,x.mana(),x.manaMax(),x.forStat(),x.agi(),x.vit(),x.intelligence(),x.per()));l.destroyBlock(pos,false);sp.sendSystemMessage(Component.literal("Selo da Cripta concluído: +150 EXP e +6 PS."));}return InteractionResult.sidedSuccess(l.isClientSide());}
}