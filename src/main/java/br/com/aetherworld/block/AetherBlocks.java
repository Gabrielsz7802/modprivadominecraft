package br.com.aetherworld.block;
import br.com.aetherworld.AetherWorld;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class AetherBlocks {
    public static final Block GUARDIAN_SEAL = register("guardian_seal",
        new GuardianSealBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).strength(50.0f).noLootTable()));

    private static Block register(String name, Block block) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, AetherWorld.id(name));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static void initialize() {}
}
