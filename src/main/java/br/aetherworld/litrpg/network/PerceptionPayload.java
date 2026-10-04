package br.aetherworld.litrpg.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PerceptionPayload(int marker) implements CustomPacketPayload {
    public static final Type<PerceptionPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("aetherworld_litrpg", "perception")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PerceptionPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    PerceptionPayload::marker,
                    PerceptionPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
