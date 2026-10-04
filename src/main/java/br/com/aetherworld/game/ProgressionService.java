package br.com.aetherworld.game;
import br.com.aetherworld.data.*;
import br.com.aetherworld.item.AetherItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
public final class ProgressionService{
 public static void initialize(){ServerTickEvents.END_SERVER_TICK.register(s->{for(ServerPlayer p:s.getPlayerList().getPlayers())tick(p);});}
 private static void tick(ServerPlayer p){PlayerProfile x=AetherData.get(p).normalize();double m=Math.min(x.manaMax(),x.mana()+x.intelligence()*0.0025);AetherData.set(p,new PlayerProfile(x.level(),x.expCurrent(),x.expMax(),x.freeAttributePoints(),x.ps(),m,x.manaMax(),x.forStat(),x.agi(),x.vit(),x.intelligence(),x.per()));}
 public static void addExperience(ServerPlayer p,int amount){PlayerProfile x=AetherData.get(p);int l=x.level(),e=x.expCurrent()+amount,free=x.freeAttributePoints(),ps=x.ps(),f=x.forStat(),a=x.agi(),v=x.vit(),i=x.intelligence(),per=x.per();boolean up=false;while(e>=l*150){e-=l*150;l++;f+=2;a+=2;v+=2;i+=2;per+=2;free+=20;ps+=5;up=true;}AetherData.set(p,new PlayerProfile(l,e,l*150,free,ps,x.mana(),100+i*10.0,f,a,v,i,per));if(up){ServerLevel sl=p.serverLevel();sl.playSound(null,p.blockPosition(),SoundEvents.PLAYER_LEVELUP,SoundSource.PLAYERS,1,1);sl.sendParticles(ParticleTypes.WAX_ON,p.getX(),p.getY()+1,p.getZ(),35,.7,1,.7,.05);}}
 public static String summary(ServerPlayer p){PlayerProfile x=AetherData.get(p);return "Level "+x.level()+" | EXP "+x.expCurrent()+"/"+x.expMax()+" | Mana "+x.mana()+"/"+x.manaMax()+" | FOR "+x.forStat()+" AGI "+x.agi()+" VIT "+x.vit()+" INT "+x.intelligence()+" PER "+x.per();}
 public static boolean buy(ServerPlayer p,Item item,int price){PlayerProfile x=AetherData.get(p);if(x.ps()<price){p.sendSystemMessage(net.minecraft.network.chat.Component.literal("Saldo de PS Insuficiente"));return false;}if(!p.getInventory().add(new ItemStack(item)))return false;AetherData.set(p,new PlayerProfile(x.level(),x.expCurrent(),x.expMax(),x.freeAttributePoints(),x.ps()-price,x.mana(),x.manaMax(),x.forStat(),x.agi(),x.vit(),x.intelligence(),x.per()));return true;}
}