package br.aetherworld.litrpg.client;

import br.aetherworld.litrpg.entity.RiftCrawlerEntity;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.GhastRenderState;
import net.minecraft.resources.Identifier;

public final class RiftCrawlerRenderer extends MobRenderer<RiftCrawlerEntity, GhastRenderState, GhastModel> {
    public RiftCrawlerRenderer(EntityRendererProvider.Context context) {
        super(context, new GhastModel(context.bakeLayer(ModelLayers.GHAST)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(GhastRenderState state) {
        return Identifier.withDefaultNamespace("textures/entity/ghast/ghast.png");
    }

    @Override
    public GhastRenderState createRenderState() {
        return new GhastRenderState();
    }
}
