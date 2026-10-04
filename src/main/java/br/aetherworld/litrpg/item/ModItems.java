package br.aetherworld.litrpg.item;

import br.aetherworld.litrpg.AetherWorldMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class ModItems {
    public static final Item MANA_CORE = register(
            "mana_core",
            new ManaCoreItem(properties("mana_core").stacksTo(16))
    );

    public static final Item BLACK_GOLDEN_CARD = register(
            "black_golden_card",
            new GoldenBlackCardItem(properties("black_golden_card").stacksTo(1))
    );

    private ModItems() {}

    private static Item.Properties properties(String name) {
        ResourceKey<Item> key = ResourceKey.create(
                net.minecraft.core.registries.Registries.ITEM,
                Identifier.fromNamespaceAndPath(AetherWorldMod.MOD_ID, name)
        );
        return new Item.Properties().setId(key);
    }

    private static Item register(String name, Item item) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(AetherWorldMod.MOD_ID, name),
                item
        );
    }

    public static void init() {
        // Items are registered by the static fields above.
        // They can be obtained through the system shop or /give.
    }
}
