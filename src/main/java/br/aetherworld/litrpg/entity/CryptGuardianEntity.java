package br.aetherworld.litrpg.entity;

import br.aetherworld.litrpg.progression.RewardManager;
import br.aetherworld.litrpg.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.UUID;

public final class CryptGuardianEntity extends Monster {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            UUID.randomUUID(),
            Component.literal("Crypt Guardian E"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS
    );

    private boolean phaseTriggered;
    private boolean rewarded;

    public CryptGuardianEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 250.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.ARMOR, 10.0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            return;
        }

        updateBossBar();
        bossEvent.setProgress(Math.max(0.0F, Math.min(1.0F, getHealth() / getMaxHealth())));

        if (tickCount % 160 == 0) {
            groundImpact();
        }

        if (!phaseTriggered && getHealth() <= getMaxHealth() * 0.50F) {
            phaseTriggered = true;
            summonCrawlers();
        }
    }

    private void updateBossBar() {
        ServerLevel serverLevel = (ServerLevel) level();

        for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
            boolean nearby = player.level() == level()
                    && player.distanceToSqr(this) <= 900.0;

            if (nearby) {
                bossEvent.addPlayer(player);
            } else {
                bossEvent.removePlayer(player);
            }
        }
    }

    private void groundImpact() {
        for (Player player : level().getEntitiesOfClass(
                Player.class,
                getBoundingBox().inflate(8.0),
                target -> target.isAlive()
        )) {
            double dx = player.getX() - getX();
            double dz = player.getZ() - getZ();
            double length = Math.max(0.001, Math.sqrt(dx * dx + dz * dz));

            player.setDeltaMovement(
                    player.getDeltaMovement().add(
                            dx / length * 0.9,
                            0.35,
                            dz / length * 0.9
                    )
            );
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 1));
        }
    }

    private void summonCrawlers() {
        for (int i = 0; i < 2; i++) {
            RiftCrawlerEntity crawler = ModEntities.RIFT_CRAWLER.create((ServerLevel) level(), EntitySpawnReason.TRIGGERED);
            if (crawler != null) {
                double angle = i * Math.PI;
                crawler.moveTo(
                        getX() + Math.cos(angle) * 2.5,
                        getY(),
                        getZ() + Math.sin(angle) * 2.5,
                        getYRot(),
                        0.0F
                );
                level().addFreshEntity(crawler);
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!rewarded && !level().isClientSide()
                && getLastHurtByMob() instanceof ServerPlayer serverPlayer) {
            rewarded = true;
            RewardManager.grant(serverPlayer, 6, 150, "Crypt Guardian E");

            for (int i = 0; i < 3; i++) {
                ItemStack core = new ItemStack(ModItems.MANA_CORE);
                if (!serverPlayer.getInventory().add(core)) {
                    serverPlayer.drop(core, false);
                }
            }
        }

        bossEvent.removeAllPlayers();
        super.die(damageSource);
    }

    @Override
    public void remove(net.minecraft.world.entity.Entity.RemovalReason reason) {
        bossEvent.removeAllPlayers();
        super.remove(reason);
    }
}
