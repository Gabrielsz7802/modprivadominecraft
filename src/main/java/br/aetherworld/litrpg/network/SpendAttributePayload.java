package br.aetherworld.litrpg.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SpendAttributePayload(String attribute) implements CustomPacketPayload {
    public static final Type<SpendAttributePayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("aetherworld_litrpg", "spend_attribute")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SpendAttributePayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    SpendAttributePayload::attribute,
                    SpendAttributePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
