package br.aetherworld.litrpg.network;

import br.aetherworld.litrpg.component.ModComponents;
import br.aetherworld.litrpg.item.ModItems;
import br.aetherworld.litrpg.progression.ProgressionManager;
import br.aetherworld.litrpg.shop.ShopCatalog;
import br.aetherworld.litrpg.shop.ShopEntry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerPlayer;

public final class ModNetworking {
    private ModNetworking() {}

    public static void init() {
        PayloadTypeRegistry.serverboundPlay().register(
                SpendAttributePayload.TYPE,
                SpendAttributePayload.CODEC
        );
        PayloadTypeRegistry.serverboundPlay().register(
                ShopPurchasePayload.TYPE,
                ShopPurchasePayload.CODEC
        );
        PayloadTypeRegistry.serverboundPlay().register(
                PerceptionPayload.TYPE,
                PerceptionPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                SpendAttributePayload.TYPE,
                (payload, context) -> context.server().execute(() -> {
                    ServerPlayer player = context.player();
                    boolean success = ProgressionManager.spendAttribute(player, payload.attribute());
                    actionbar(player, success
                            ? "Ponto de atributo aplicado."
                            : "Não foi possível gastar o ponto.");
                })
        );

        ServerPlayNetworking.registerGlobalReceiver(
                ShopPurchasePayload.TYPE,
                (payload, context) -> context.server().execute(() ->
                        purchase(context.player(), payload.id()))
        );

        ServerPlayNetworking.registerGlobalReceiver(
                PerceptionPayload.TYPE,
                (payload, context) -> context.server().execute(() ->
                        usePerception(context.player()))
        );
    }

    private static void purchase(ServerPlayer player, String id) {
        ShopEntry entry = ShopCatalog.find(id);

        if (entry == null) {
            actionbar(player, "Item não encontrado.");
            return;
        }

        int cost = entry.price();
        if (player.getOffhandItem().is(ModItems.BLACK_GOLDEN_CARD)) {
            cost = Math.max(1, (int) Math.ceil(cost * 0.90));
        }

        var data = ModComponents.LITRPG.get(player);
        if (!data.spendSystemPoints(cost)) {
            actionbar(player, "PS insuficientes.");
            return;
        }

        ItemStack stack = new ItemStack(entry.item());
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }

        actionbar(player, "Compra concluída: " + entry.id() + " por " + cost + " PS.");
    }

    private static void usePerception(ServerPlayer player) {
        var data = ModComponents.LITRPG.get(player);

        if (!data.consumeMana(10.0)) {
            actionbar(player, "Mana insuficiente.");
            return;
        }

        AABB box = player.getBoundingBox().inflate(15.0);
        int revealed = 0;

        for (Monster monster : player.level().getEntitiesOfClass(
                Monster.class,
                box,
                entity -> entity.isAlive()
        )) {
            double dx = monster.getX() - player.getX();
            double dy = monster.getY() - player.getY();
            double dz = monster.getZ() - player.getZ();

            if (dx * dx + dy * dy + dz * dz <= 225.0) {
                monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0));
                revealed++;
            }
        }

        actionbar(player, "Percepção ativada: " + revealed + " ameaças reveladas.");
    }

    private static void actionbar(ServerPlayer player, String message) {
        player.connection.send(new ClientboundSetActionBarTextPacket(Component.literal(message)));
    }
}
