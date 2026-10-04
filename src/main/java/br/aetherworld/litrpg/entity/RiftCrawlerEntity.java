package br.aetherworld.litrpg.entity;

import br.aetherworld.litrpg.item.ModItems;
import br.aetherworld.litrpg.progression.RewardManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class RiftCrawlerEntity extends Monster {
    public RiftCrawlerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!level().isClientSide()
                && getLastHurtByMob() instanceof ServerPlayer serverPlayer) {
            RewardManager.grant(serverPlayer, 3, 25, "Rift Crawler E");
            if (random.nextFloat() < 0.50F) {
                spawnAtLocation((ServerLevel) level(), ModItems.MANA_CORE);
            }
        }
        super.die(damageSource);
    }
}
