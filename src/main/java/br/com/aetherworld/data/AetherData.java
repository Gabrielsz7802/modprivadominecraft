package br.com.aetherworld.data;
import br.com.aetherworld.AetherWorld;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.player.Player;
public final class AetherData {
 public static final AttachmentType<PlayerProfile> PROFILE=AttachmentRegistry.create(Identifier.fromNamespaceAndPath(AetherWorld.MOD_ID,"player_profile"),b->b.initializer(PlayerProfile::defaults).persistent(PlayerProfile.CODEC).copyOnDeath().syncWith(PlayerProfile.STREAM_CODEC,AttachmentSyncPredicate.all()));
 public static PlayerProfile get(Player p){return p.getAttachedOrCreate(PROFILE);}
 public static void set(Player p,PlayerProfile x){p.setAttached(PROFILE,x.normalize());}
}