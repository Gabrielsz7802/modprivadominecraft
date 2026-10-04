package br.aetherworld.litrpg.client;

import br.aetherworld.litrpg.component.LitRpgComponent;
import br.aetherworld.litrpg.component.ModComponents;
import br.aetherworld.litrpg.network.ShopPurchasePayload;
import br.aetherworld.litrpg.network.SpendAttributePayload;
import br.aetherworld.litrpg.shop.ShopCatalog;
import br.aetherworld.litrpg.shop.ShopEntry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class SystemScreen extends Screen {
    private boolean shopTab;

    public SystemScreen() {
        super(Component.literal("AetherWorld System"));
    }

    @Override
    protected void init() {
        int left = (width - 290) / 2;
        int top = (height - 220) / 2;

        addRenderableWidget(Button.builder(Component.literal("STATUS"), button -> shopTab = false)
                .bounds(left + 18, top + 30, 115, 20)
                .build());

        addRenderableWidget(Button.builder(Component.literal("LOJA"), button -> shopTab = true)
                .bounds(left + 145, top + 30, 115, 20)
                .build());

        String[] stats = {"forca", "agilidade", "vitalidade", "inteligencia", "percepcao"};
        for (int i = 0; i < stats.length; i++) {
            final String id = stats[i];
            addRenderableWidget(Button.builder(Component.literal("+"), button ->
                    ClientPlayNetworking.send(new SpendAttributePayload(id)))
                    .bounds(left + 230, top + 105 + i * 20, 20, 18)
                    .build());
        }

        int y = top + 105;
        for (ShopEntry entry : ShopCatalog.all()) {
            addRenderableWidget(Button.builder(
                            Component.literal("COMPRAR " + entry.price() + " PS"),
                            button -> ClientPlayNetworking.send(new ShopPurchasePayload(entry.id())))
                    .bounds(left + 145, y, 115, 20)
                    .build());
            y += 34;
        }
    }

    @Override
    public void extractRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int panelW = 290;
        int panelH = 220;
        int left = (width - panelW) / 2;
        int top = (height - panelH) / 2;

        graphics.fill(left, top, left + panelW, top + panelH, 0xE90A0C10);
        graphics.fill(left, top, left + panelW, top + 3, 0xFFFFC84A);
        graphics.fill(left, top + panelH - 3, left + panelW, top + panelH, 0xFF8A5A00);
        graphics.fill(left, top, left + 3, top + panelH, 0xFFFFC84A);
        graphics.fill(left + panelW - 3, top, left + panelW, top + panelH, 0xFF8A5A00);

        graphics.text(font, "AETHERWORLD SYSTEM", left + 78, top + 11, 0xFFFFD76A, true);

        if (minecraft == null || minecraft.player == null) {
            return;
        }

        LitRpgComponent data = ModComponents.LITRPG.get(minecraft.player);

        graphics.text(font, "NÍVEL: " + data.getLevel(), left + 18, top + 57, 0xFFFFFFFF, true);
        graphics.text(font, "XP: " + data.getExperience() + " / " + data.getExperienceMax(),
                left + 18, top + 73, 0xFFD7D7D7, false);
        graphics.text(font, "HP: " + Math.ceil(minecraft.player.getHealth()) + " / "
                + Math.ceil(minecraft.player.getMaxHealth()), left + 18, top + 90, 0xFFFF8080, false);
        graphics.text(font, "MP: " + Math.ceil(data.getMana()) + " / "
                + Math.ceil(100 + data.getIntelligence() * 10.0), left + 18, top + 107, 0xFF74D9FF, false);

        if (!shopTab) {
            String[] names = {"FOR", "AGI", "VIT", "INT", "PER"};
            int[] values = {
                    data.getStrength(),
                    data.getAgility(),
                    data.getVitality(),
                    data.getIntelligence(),
                    data.getPerception()
            };

            for (int i = 0; i < names.length; i++) {
                graphics.text(font,
                        names[i] + ": " + values[i],
                        left + 18,
                        top + 120 + i * 20,
                        0xFFFFFFFF,
                        false);
            }

            graphics.text(font, "Pontos: " + data.getFreePoints(),
                    left + 160, top + 79, 0xFFFFD76A, true);
            graphics.text(font, "PS: " + data.getSystemPoints(),
                    left + 160, top + 95, 0xFFFFD76A, true);
        } else {
            graphics.text(font, "LOJA DO SISTEMA", left + 18, top + 125, 0xFFFFD76A, true);

            int y = top + 146;
            for (ShopEntry entry : ShopCatalog.all()) {
                graphics.text(font,
                        entry.id() + " — " + entry.price() + " PS",
                        left + 18,
                        y,
                        0xFFFFFFFF,
                        false);
                y += 34;
            }
        }

        graphics.text(font, "O = Sistema | P = Percepção",
                left + 18, top + 202, 0xFF8A8A8A, false);
    }
}
