package br.com.aetherworld.item;
import br.com.aetherworld.AetherWorld;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import java.util.function.Function;

public final class AetherItems {
    public static final Item MANA_CORE = register("mana_core", ManaCoreItem::new, new Item.Properties().stacksTo(16));
    public static final Item BLACK_GOLD_CARD = register("black_gold_card", Item::new, new Item.Properties().stacksTo(1));
    public static final Item AETHER_FRAGMENT = register("aether_fragment", Item::new, new Item.Properties().stacksTo(64));

    public static <T extends Item> T register(String name, Function<Item.Properties,T> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, AetherWorld.id(name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.setId(key)));
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(tab -> {
            tab.accept(MANA_CORE);
            tab.accept(BLACK_GOLD_CARD);
            tab.accept(AETHER_FRAGMENT);
        });
    }
}
