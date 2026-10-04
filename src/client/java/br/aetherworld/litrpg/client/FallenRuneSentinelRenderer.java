package br.aetherworld.litrpg.client;

import br.aetherworld.litrpg.entity.FallenRuneSentinelEntity;
import net.minecraft.client.model.monster.ghast.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.GhastRenderState;
import net.minecraft.resources.Identifier;

public final class FallenRuneSentinelRenderer extends MobRenderer<FallenRuneSentinelEntity, GhastRenderState, GhastModel> {
    public FallenRuneSentinelRenderer(EntityRendererProvider.Context context) {
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
