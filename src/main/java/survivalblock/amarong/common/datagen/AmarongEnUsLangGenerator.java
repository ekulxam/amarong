package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongItems;

import java.util.concurrent.CompletableFuture;

public class AmarongEnUsLangGenerator extends FabricLanguageProvider {

    public AmarongEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(AmarongItems.AMARONG_CHUNK, "Amarong Chunk");
        translationBuilder.add(AmarongItems.AMARONG_SHEET, "Amarong Sheet");
        translationBuilder.add(AmarongItems.KALEIDOSCOPE, "Amarong Kaleidoscope");
        translationBuilder.add(AmarongItems.AMARONG_VERYLONGSWORD, "Amarong Verylongsword");
        translationBuilder.add(AmarongItems.SOMEWHAT_A_DUCK, "Handheld Duck");
        translationBuilder.add(AmarongItems.TICKET_GUN, "Amarong Ticket Dispenser");
        translationBuilder.add(AmarongBlocks.AMARONG_CORE, "Amarong Core");
        translationBuilder.add("amarong.itemGroup.amarong_group", "Amarong");
        translationBuilder.add("sound.amarong.duck_squeezed", "Duck Squeaks");
        translationBuilder.add("item.amarong.somewhat_a_duck.water", "Water: %s / %s");
        translationBuilder.add("item.amarong.amarong_kaleidoscope.headaches", "Epilepsy Warning");
        translationBuilder.add("resourcePack.amarong.nokaleidoscopeoverlay.name", "No Kaleidoscope Overlay");
        translationBuilder.add("advancements.amarong.use_kaleidoscope.title", "A World of Color");
        translationBuilder.add("advancements.amarong.use_kaleidoscope.description", "Use an Amarong Kaleidoscope");
        translationBuilder.add("advancements.amarong.obtain_verylongsword.title", "Comically Large Sword");
        translationBuilder.add("advancements.amarong.obtain_verylongsword.description", "Obtain an Amarong Verylongsword");
        translationBuilder.add("advancements.amarong.obtain_core.title", "Prismatic");
        translationBuilder.add("advancements.amarong.obtain_core.description", "Obtain an Amarong Core");

    }
}
