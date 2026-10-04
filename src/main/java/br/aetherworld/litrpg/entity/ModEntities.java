package br.aetherworld.litrpg.entity;

import br.aetherworld.litrpg.AetherWorldMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {
    public static final EntityType<RiftCrawlerEntity> RIFT_CRAWLER = register(
            "rift_crawler",
            EntityType.Builder.of(RiftCrawlerEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(8)
    );

    public static final EntityType<FallenRuneSentinelEntity> FALLEN_RUNE_SENTINEL = register(
            "fallen_rune_sentinel",
            EntityType.Builder.of(FallenRuneSentinelEntity::new, MobCategory.MONSTER)
                    .sized(1.0F, 2.2F)
                    .clientTrackingRange(8)
    );

    public static final EntityType<CryptGuardianEntity> CRYPT_GUARDIAN = register(
            "crypt_guardian",
            EntityType.Builder.of(CryptGuardianEntity::new, MobCategory.MONSTER)
                    .sized(1.6F, 3.4F)
                    .clientTrackingRange(10)
    );

    private ModEntities() {}

    private static <T extends Entity> EntityType<T> register(
            String name,
            EntityType.Builder<T> builder
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(AetherWorldMod.MOD_ID, name);
        ResourceKey<EntityType<T>> key = ResourceKey.create(
                net.minecraft.core.registries.Registries.ENTITY_TYPE,
                id
        );

        return Registry.register(
                net.minecraft.core.registries.Registries.ENTITY_TYPE,
                id,
                builder.build(key)
        );
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(RIFT_CRAWLER, RiftCrawlerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FALLEN_RUNE_SENTINEL, FallenRuneSentinelEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CRYPT_GUARDIAN, CryptGuardianEntity.createAttributes());
    }
}
