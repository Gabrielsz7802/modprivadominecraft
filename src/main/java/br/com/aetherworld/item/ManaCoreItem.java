package br.com.aetherworld.item;
import br.com.aetherworld.data.AetherData;
import br.com.aetherworld.data.PlayerProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
public class ManaCoreItem extends Item{
 public ManaCoreItem(Properties p){super(p);}
 @Override public InteractionResultHolder<ItemStack> use(Level l,Player p,InteractionHand h){ItemStack s=p.getItemInHand(h);if(!l.isClientSide()){PlayerProfile x=AetherData.get(p);if(x.mana()<x.manaMax()){double m=Math.min(x.manaMax(),x.mana()+50);AetherData.set(p,new PlayerProfile(x.level(),x.expCurrent(),x.expMax(),x.freeAttributePoints(),x.ps(),m,x.manaMax(),x.forStat(),x.agi(),x.vit(),x.intelligence(),x.per()));if(!p.getAbilities().instabuild)s.shrink(1);p.sendSystemMessage(Component.literal("+50 Mana"));return InteractionResultHolder.success(s);}p.sendSystemMessage(Component.literal("Mana já está cheia."));}return InteractionResultHolder.pass(s);}
}