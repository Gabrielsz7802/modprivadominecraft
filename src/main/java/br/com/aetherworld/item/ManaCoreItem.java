package br.com.aetherworld.item;
import br.com.aetherworld.data.AetherData;
import br.com.aetherworld.data.PlayerProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaCoreItem extends Item {
    public ManaCoreItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        PlayerProfile x = AetherData.get(player);
        if (x.mana() < x.manaMax()) {
            double mana = Math.min(x.manaMax(), x.mana() + 50.0);
            AetherData.set(player, new PlayerProfile(x.level(), x.expCurrent(), x.expMax(), x.freeAttributePoints(), x.ps(), mana, x.manaMax(), x.forStat(), x.agi(), x.vit(), x.intelligence(), x.per()));
            if (!player.getAbilities().instabuild) stack.consume(1, player);
            player.sendSystemMessage(Component.literal("+50 Mana"));
            return InteractionResult.SUCCESS;
        }
        player.sendSystemMessage(Component.literal("Mana já está cheia."));
        return InteractionResult.PASS;
    }
}
