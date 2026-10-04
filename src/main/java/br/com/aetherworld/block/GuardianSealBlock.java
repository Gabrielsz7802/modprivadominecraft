package br.com.aetherworld.block;
import br.com.aetherworld.data.AetherData;
import br.com.aetherworld.data.PlayerProfile;
import br.com.aetherworld.game.ProgressionService;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GuardianSealBlock extends Block {
    public GuardianSealBlock(Properties properties) { super(properties); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ProgressionService.addExperience(serverPlayer, 150);
            PlayerProfile x = AetherData.get(serverPlayer);
            AetherData.set(serverPlayer, new PlayerProfile(x.level(), x.expCurrent(), x.expMax(), x.freeAttributePoints(), x.ps() + 6, x.mana(), x.manaMax(), x.forStat(), x.agi(), x.vit(), x.intelligence(), x.per()));
            level.destroyBlock(pos, false);
            serverPlayer.sendSystemMessage(Component.literal("Selo da Cripta concluído: +150 EXP e +6 PS."));
        }
        return InteractionResult.SUCCESS;
    }
}
