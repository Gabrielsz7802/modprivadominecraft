package br.aetherworld.litrpg.progression;

import br.aetherworld.litrpg.component.LitRpgComponent;
import br.aetherworld.litrpg.component.ModComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class ProgressionManager {
    private ProgressionManager() {}

    public static void tick(Player player) {
        LitRpgComponent data = ModComponents.LITRPG.get(player);

        if (!player.level().isClientSide()) {
            if (player.tickCount % 20 == 0) {
                applyAttributeEffects(player, data);

                data.restoreMana(data.getIntelligence() * 0.05);

                if (data.getMana() <= 0.0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 40, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 40, 0));
                }
            }
        }
    }

    public static void addExperience(ServerPlayer player, int amount) {
        if (amount <= 0) return;

        LitRpgComponent data = ModComponents.LITRPG.get(player);
        data.addExperience(amount);

        while (data.getExperience() >= data.getExperienceMax()) {
            int required = data.getExperienceMax();
            data.setExperience(data.getExperience() - required);

            data.setLevel(data.getLevel() + 1);
            data.addLevelUpStats();
            data.setFreePoints(data.getFreePoints() + 20);
            data.addSystemPoints(5);
            data.setExperienceMax(Math.max(
                    100,
                    (int) Math.floor(data.getLevel() * 100.0 * 1.5)
            ));

            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.PLAYER_LEVELUP,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            ServerLevel level = (ServerLevel) player.level();
            double x = player.getX();
            double y = player.getY() + 0.5;
            double z = player.getZ();

            for (int i = 0; i < 32; i++) {
                double angle = Math.PI * 2.0 * i / 32.0;
                double radius = 1.15;
                level.sendParticles(
                        ParticleTypes.END_ROD,
                        x + Math.cos(angle) * radius,
                        y,
                        z + Math.sin(angle) * radius,
                        1,
                        0.0, 0.0, 0.0, 0.0
                );
            }
        }

        applyAttributeEffects(player, data);
    }

    public static boolean spendAttribute(ServerPlayer player, String attribute) {
        LitRpgComponent data = ModComponents.LITRPG.get(player);
        boolean success = data.spendAttributePoint(attribute);
        if (success) {
            applyAttributeEffects(player, data);
        }
        return success;
    }

    public static void applyAttributeEffects(Player player, LitRpgComponent data) {
        int strengthSteps = Math.max(0, data.getStrength() - 10) / 2;
        int agilitySteps = Math.max(0, data.getAgility() - 10) / 5;
        int vitalitySteps = Math.max(0, data.getVitality() - 10);

        setBase(player, Attributes.ATTACK_DAMAGE, 1.0 + strengthSteps * 1.0);
        setBase(player, Attributes.MOVEMENT_SPEED, 0.1 + agilitySteps * 0.02);
        setBase(player, Attributes.ATTACK_SPEED, 4.0 + agilitySteps * 0.1);
        setBase(player, Attributes.MAX_HEALTH, 20.0 + vitalitySteps * 2.0);

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    private static void setBase(Player player, Holder<Attribute> attribute, double value) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }
}
