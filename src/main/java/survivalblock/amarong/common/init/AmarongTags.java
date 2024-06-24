package survivalblock.amarong.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.amarong.common.Amarong;

public class AmarongTags {

    public static class AmarongItemTags {
        public static final TagKey<Item> RAINBOW_CORE_GENERATORS = TagKey.of(RegistryKeys.ITEM, Amarong.id("rainbow_core_generators"));

    }
}
