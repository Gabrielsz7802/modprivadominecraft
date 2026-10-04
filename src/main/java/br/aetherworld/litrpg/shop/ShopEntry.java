package br.aetherworld.litrpg.shop;

import net.minecraft.world.item.Item;

public record ShopEntry(String id, Item item, int price) {
}
