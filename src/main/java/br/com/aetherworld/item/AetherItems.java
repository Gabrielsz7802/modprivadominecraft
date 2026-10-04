package br.com.aetherworld.item;
import br.com.aetherworld.AetherWorld;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.network.chat.Component;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import java.util.function.Function;
public final class AetherItems{
 public static final Item MANA_CORE=register("mana_core",ManaCoreItem::new,new Item.Properties().stacksTo(16));
 public static final Item BLACK_GOLD_CARD=register("black_gold_card",Item::new,new Item.Properties().stacksTo(1));
 public static final Item AETHER_FRAGMENT=register("aether_fragment",Item::new,new Item.Properties().stacksTo(64));
 public static final ResourceKey<CreativeModeTab> TAB_KEY=ResourceKey.create(Registries.CREATIVE_MODE_TAB,AetherWorld.id("system"));
 public static final CreativeModeTab TAB=CreativeModeTab.builder().title(Component.translatable("itemGroup.aetherworld.system")).icon(()->new ItemStack(BLACK_GOLD_CARD)).displayItems((p,o)->{o.accept(MANA_CORE);o.accept(BLACK_GOLD_CARD);o.accept(AETHER_FRAGMENT);}).build();
 public static <T extends Item>T register(String n,Function<Item.Properties,T> f,Item.Properties p){ResourceKey<Item> k=ResourceKey.create(Registries.ITEM,AetherWorld.id(n));return Registry.register(BuiltInRegistries.ITEM,k,f.apply(p.setId(k)));}
 public static void initialize(){Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,TAB_KEY,TAB);ItemGroupEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(t->{t.accept(MANA_CORE);t.accept(BLACK_GOLD_CARD);t.accept(AETHER_FRAGMENT);});}
}