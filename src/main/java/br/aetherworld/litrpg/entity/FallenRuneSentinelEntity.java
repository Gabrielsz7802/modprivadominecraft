package br.aetherworld.litrpg.entity;

import br.aetherworld.litrpg.progression.RewardManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class FallenRuneSentinelEntity extends Monster {
    public FallenRuneSentinelEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.ARMOR, 8.0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide()
                && getHealth() <= getMaxHealth() * 0.30F
                && tickCount % 20 == 0) {
            addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1));
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!level().isClientSide()
                && getLastHurtByMob() instanceof ServerPlayer serverPlayer) {
            RewardManager.grant(serverPlayer, 4, 45, "Fallen Rune Sentinel E");
        }
        super.die(damageSource);
    }
}
