package br.aetherworld.litrpg.shop;

import br.aetherworld.litrpg.item.ModItems;
import java.util.List;

public final class ShopCatalog {
    private static final List<ShopEntry> ENTRIES = List.of(
            new ShopEntry("mana_core", ModItems.MANA_CORE, 3),
            new ShopEntry("black_golden_card", ModItems.BLACK_GOLDEN_CARD, 25)
    );

    private ShopCatalog() {}

    public static List<ShopEntry> all() {
        return ENTRIES;
    }

    public static ShopEntry find(String id) {
        return ENTRIES.stream()
                .filter(entry -> entry.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
