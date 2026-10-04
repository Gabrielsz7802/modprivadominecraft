package br.aetherworld.litrpg.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ShopPurchasePayload(String id) implements CustomPacketPayload {
    public static final Type<ShopPurchasePayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("aetherworld_litrpg", "shop_purchase")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ShopPurchasePayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    ShopPurchasePayload::id,
                    ShopPurchasePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
