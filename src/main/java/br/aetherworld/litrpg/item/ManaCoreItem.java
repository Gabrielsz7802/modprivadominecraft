package br.aetherworld.litrpg.item;

import br.aetherworld.litrpg.component.ModComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public final class ManaCoreItem extends Item {
    public ManaCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ModComponents.LITRPG.get(player).restoreMana(50.0);

        if (!player.getAbilities().instabuild) {
            player.getItemInHand(hand).shrink(1);
        }

        return InteractionResult.SUCCESS;
    }
}
