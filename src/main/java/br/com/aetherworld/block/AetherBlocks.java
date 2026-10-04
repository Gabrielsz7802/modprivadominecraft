package br.com.aetherworld.block;
import br.com.aetherworld.AetherWorld;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
public final class AetherBlocks{
 public static final Block GUARDIAN_SEAL=register("guardian_seal",new GuardianSealBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).strength(50.0f).noLootTable()));
 private static Block register(String n,Block b){ResourceKey<Block> k=ResourceKey.create(Registries.BLOCK,AetherWorld.id(n));return Registry.register(BuiltInRegistries.BLOCK,k,b);}
 public static void initialize(){}
}