package br.com.aetherworld.game;
import br.com.aetherworld.data.AetherData;
import br.com.aetherworld.data.PlayerProfile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public final class ProgressionService {
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer p : server.getPlayerList().getPlayers()) tick(p);
        });
    }

    private static void tick(ServerPlayer p) {
        PlayerProfile x = AetherData.get(p).normalize();
        double mana = Math.min(x.manaMax(), x.mana() + x.intelligence() * 0.0025);
        AetherData.set(p, new PlayerProfile(x.level(), x.expCurrent(), x.expMax(), x.freeAttributePoints(), x.ps(), mana, x.manaMax(), x.forStat(), x.agi(), x.vit(), x.intelligence(), x.per()));
    }

    public static void addExperience(ServerPlayer p, int amount) {
        PlayerProfile x = AetherData.get(p);
        int level = x.level(), exp = x.expCurrent() + amount, free = x.freeAttributePoints(), ps = x.ps();
        int f = x.forStat(), a = x.agi(), v = x.vit(), in = x.intelligence(), per = x.per();
        boolean levelUp = false;
        while (exp >= level * 150) {
            exp -= level * 150;
            level++;
            f += 2; a += 2; v += 2; in += 2; per += 2;
            free += 20; ps += 5; levelUp = true;
        }
        AetherData.set(p, new PlayerProfile(level, exp, level * 150, free, ps, x.mana(), 100 + in * 10.0, f, a, v, in, per));
        if (levelUp) {
            ServerLevel sl = (ServerLevel) p.level();
            sl.playSound(null, p.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1, 1);
            sl.sendParticles(ParticleTypes.WAX_ON, p.getX(), p.getY() + 1, p.getZ(), 35, .7, 1, .7, .05);
        }
    }

    public static String summary(ServerPlayer p) {
        PlayerProfile x = AetherData.get(p);
        return "Level " + x.level() + " | EXP " + x.expCurrent() + "/" + x.expMax() + " | Mana " + x.mana() + "/" + x.manaMax() + " | FOR " + x.forStat() + " AGI " + x.agi() + " VIT " + x.vit() + " INT " + x.intelligence() + " PER " + x.per();
    }

    public static boolean buy(ServerPlayer p, Item item, int price) {
        PlayerProfile x = AetherData.get(p);
        if (x.ps() < price) {
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("Saldo de PS Insuficiente"));
            return false;
        }
        if (!p.getInventory().add(new ItemStack(item))) return false;
        AetherData.set(p, new PlayerProfile(x.level(), x.expCurrent(), x.expMax(), x.freeAttributePoints(), x.ps() - price, x.mana(), x.manaMax(), x.forStat(), x.agi(), x.vit(), x.intelligence(), x.per()));
        return true;
    }
}
