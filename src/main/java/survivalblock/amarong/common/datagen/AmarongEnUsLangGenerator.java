package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEnchantments;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongGameRules;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.init.AmarongTags;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.language.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AmarongEnUsLangGenerator extends AtmosphericLanguageGenerator {

    public AmarongEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, AtmosphericTranslationBuilder translationBuilder) {
        // item
        translationBuilder.add(AmarongItems.AMARONG_CHUNK, "Amarong Chunk");
        translationBuilder.add(AmarongItems.AMARONG_SHEET, "Amarong Sheet");
        translationBuilder.add(AmarongItems.KALEIDOSCOPE, "Amarong Kaleidoscope");
        translationBuilder.add(AmarongItems.AMARONG_VERYLONGSWORD, "Amarong Verylongsword");
        translationBuilder.add(AmarongItems.SOMEWHAT_A_DUCK, "Handheld Duck");
        translationBuilder.add(AmarongItems.TICKET_LAUNCHER, "Amarong Ticket Launcher");
        translationBuilder.add(AmarongItems.AMARONG_HAMMER, "Amarong Hammer");
        translationBuilder.add(AmarongItems.AMARONG_BOOMERANG, "Amarong Boomerang");
        translationBuilder.add(AmarongItems.AMARONG_STAFF, "Amarong Staff");
        translationBuilder.add(AmarongItems.AMARONG_GROUP, "Amarong");

        // block
        translationBuilder.add(AmarongBlocks.AMARONG_CORE, "Amarong Core");

        // entity types
        translationBuilder.add(AmarongEntityTypes.FLYING_TICKET, "Flying Ticket");
        translationBuilder.add(AmarongEntityTypes.WATER_STREAM, "Water Stream");
        translationBuilder.add(AmarongEntityTypes.RAILGUN, "Railcannon");
        translationBuilder.add(AmarongEntityTypes.BOOMERANG, "Amarong Boomerang");

        // gamerules
        translationBuilder.add(AmarongGameRules.FLYING_TICKETS_DROP, "Amarong - Flying Tickets drop themselves on being discarded");
        translationBuilder.add(AmarongGameRules.BOOMERANG_DAMAGE, "Amarong - Boomerang Damage");
        translationBuilder.add(AmarongGameRules.VERYLONGSWORD_PASSIVE_CHARGE, "Amarong - Allow Verylongswords to passively build charge in inventory");
        translationBuilder.add(AmarongGameRules.OBSCURE_SPAWNS_PARTICLES, "Amarong - Entering Obscure Spawns Particles");

        // subtitles
        translationBuilder.add("subtitles.amarong.item.duck_squeezed", "Duck squeaks");
        translationBuilder.add("subtitles.amarong.entity.flying_ticket.hit", "Flying Ticket hits");
        translationBuilder.add("subtitles.amarong.entity.water_stream.hit", "Water Stream hits");
        translationBuilder.add("subtitles.amarong.entity.railgun.charge", "Railcannon charges");

        // components
        translationBuilder.add("item.amarong.somewhat_a_duck.water", "Water: %s / %s");
        translationBuilder.add("item.amarong.amarong_kaleidoscope.random", "Random");

        // packs
        translationBuilder.add("resourcePack.amarong.nokaleidoscopeoverlay.name", "No Kaleidoscope Overlay");
        translationBuilder.add("resourcePack.amarong.nokaleidoscopezoom.name", "No Kaleidoscope Zoom");
        translationBuilder.add("resourcePack.amarong.smolverylongsword.name", "Smol Verylongsword");
        translationBuilder.add("resourcePack.amarong.oldticketlauncher.name", "Old Ticket Launcher");
        translationBuilder.add("resourcePack.amarong.amethysthandleticketlauncher.name", "Amethyst Handle Ticket Launcher");

        translationBuilder.add("dataPack.amarong.easy_core_duplication_pack.name", "Easy Amarong Core Duplication");
        translationBuilder.add("dataPack.amarong.amarong_hamer_recipe_pack.name", "Amarong Hammer Recipe");

        // advancements
        translationBuilder.add("advancements.amarong.use_kaleidoscope.title", "A World of Color");
        translationBuilder.add("advancements.amarong.use_kaleidoscope.description", "Use an Amarong Kaleidoscope");
        translationBuilder.add("advancements.amarong.obtain_verylongsword.title", "Comically Large Sword");
        translationBuilder.add("advancements.amarong.obtain_verylongsword.description", "Obtain an Amarong Verylongsword");
        translationBuilder.add("advancements.amarong.obtain_core.title", "Prismatic");
        translationBuilder.add("advancements.amarong.obtain_core.description", "Obtain an Amarong Core");
        translationBuilder.add("advancements.amarong.when_tickets_fly.title", "When Tickets Fly");
        translationBuilder.add("advancements.amarong.when_tickets_fly.description", "Fire a Flying Ticket from an Amarong Ticket Launcher");
        translationBuilder.add("advancements.amarong.spider.title", "S p i d e r");
        translationBuilder.add("advancements.amarong.spider.description", "Look at a spider with a spider shader kaleidoscope");
        translationBuilder.add("advancements.amarong.creeper.title", "Creeper");
        translationBuilder.add("advancements.amarong.creeper.description", "Look at a creeper with a creeper shader kaleidoscope");
        translationBuilder.add("advancements.amarong.invert.title", "Invert");
        translationBuilder.add("advancements.amarong.invert.description", "Look at an enderman with an invert shader kaleidoscope");
        translationBuilder.add("advancements.amarong.hammer_time.title", "It's Hammer Time!");
        translationBuilder.add("advancements.amarong.hammer_time.description", "Obtain an Amarong Hammer");

        // enchantments and enchantments descriptions
        translationBuilder.addEnchantment(AmarongEnchantments.PNEUMATIC, "Pneumatic");
        translationBuilder.add("enchantment.amarong.pneumatic.desc", "Allows the Ticket Launcher to launch wind charges from the offhand.");
        translationBuilder.addEnchantment(AmarongEnchantments.PARTICLE_ACCELERATOR, "Particle Accelerator");
        translationBuilder.add("enchantment.amarong.particle_accelerator.desc", "Allows the Ticket Launcher to launch splash and lingering potions from the offhand.");
        translationBuilder.addEnchantment(AmarongEnchantments.OBSCURE, "Obscure");
        translationBuilder.add("enchantment.amarong.obscure.desc", "Use the Amarong Verylongsword when fully charged to enter total invisibility temporarily.");
        translationBuilder.addEnchantment(AmarongEnchantments.RAILGUN, "Railcannon");
        translationBuilder.add("enchantment.amarong.railgun.desc", "Use the Amarong Verylongsword when fully charged to fire off a railcannon.");
        translationBuilder.addEnchantment(AmarongEnchantments.CAPACITY, "Capacity");
        translationBuilder.add("enchantment.amarong.capacity.desc", "Increases the amount of water a handheld duck can hold.");

        // item tags
        translationBuilder.add(AmarongTags.AmarongItemTags.RAINBOW_CORE_GENERATORS, "Rainbow Core Generators");
        translationBuilder.add(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE, "Enchantable - Ticket Launcher");
        translationBuilder.add(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE, "Enchantable - Verylongsword");
        translationBuilder.add(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE, "Enchantable - Handheld Duck");
        translationBuilder.add(AmarongTags.AmarongItemTags.HAMMER_ENCHANTABLE, "Enchantable - Hammer");
        translationBuilder.add(AmarongTags.AmarongItemTags.STICKS, "Sticks");
        translationBuilder.add(AmarongTags.AmarongItemTags.TWIRL_DAMAGE, "Deals damage with Twirl");
        translationBuilder.add(AmarongTags.AmarongItemTags.STAFF_UNUSABLE, "Unusable - Staff");

        // enchantment tags
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT, "Pnuematic Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT, "Particle Accelerator Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT, "Obscure Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT, "Railgun Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.CAPACITY_EFFECT, "Capacity Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD, "Exclusive Set - Verylongsword");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER, "Exclusive Set - Ticket Launcher");

        // shaders
        Function<String, String> shaderKey = (name) -> "minecraft.shaders/post/" + name + ".json";
        translationBuilder.add(shaderKey.apply("notch"), "Minecraft - Notch");
        translationBuilder.add(shaderKey.apply("fxaa"), "Minecraft - FXAA");
        translationBuilder.add(shaderKey.apply("art"), "Minecraft - Art");
        translationBuilder.add(shaderKey.apply("bumpy"), "Minecraft - Bumpy");
        translationBuilder.add(shaderKey.apply("blobs2"), "Minecraft - Blobs2");
        translationBuilder.add(shaderKey.apply("pencil"), "Minecraft - Pencil");
        translationBuilder.add(shaderKey.apply("color_convolve"), "Minecraft - Color Convolve");
        translationBuilder.add(shaderKey.apply("deconverge"), "Minecraft - Deconverge");
        translationBuilder.add(shaderKey.apply("flip"), "Minecraft - Flip");
        translationBuilder.add(shaderKey.apply("invert"), "Minecraft - Invert");
        translationBuilder.add(shaderKey.apply("ntsc"), "Minecraft - NTSC");
        translationBuilder.add(shaderKey.apply("outline"), "Minecraft - Outline");
        translationBuilder.add(shaderKey.apply("phosphor"), "Minecraft - Phosphor");
        translationBuilder.add(shaderKey.apply("scan_pincushion"), "Minecraft - Scan Pincushion");
        translationBuilder.add(shaderKey.apply("sobel"), "Minecraft - Sobel");
        translationBuilder.add(shaderKey.apply("bits"), "Minecraft - Bits");
        translationBuilder.add(shaderKey.apply("desaturate"), "Minecraft - Desaturate");
        translationBuilder.add(shaderKey.apply("green"), "Minecraft - Green");
        translationBuilder.add(shaderKey.apply("blur"), "Minecraft - Blur");
        translationBuilder.add(shaderKey.apply("wobble"), "Minecraft - Wobble");
        translationBuilder.add(shaderKey.apply("blobs"), "Minecraft - Blobs");
        translationBuilder.add(shaderKey.apply("antialias"), "Minecraft - Antialias");
        translationBuilder.add(shaderKey.apply("creeper"), "Minecraft - Creeper"); // aw man
        translationBuilder.add(shaderKey.apply("spider"), "Minecraft - Spider");

        // config
        translationBuilder.add("amarong.config.title", "Amarong Config");
        translationBuilder.add("amarong.config.resourcepack.title", "Amarong Resource Pack Config");

        translationBuilder.add("amarong.yacl.category.main", "Amarong Config (Powered by YACL)");
        translationBuilder.add("amarong.yacl.category.main.tooltip", "Config");
        translationBuilder.add("amarong.yacl.group.client", "Client");
        translationBuilder.add("amarong.yacl.option.boolean.verboseLogging", "Verbose Logging");
        translationBuilder.add("amarong.yacl.option.boolean.verboseLogging.desc", "Logs more information, such as kaleidoscope shaders.");
        translationBuilder.add("amarong.yacl.option.boolean.twoHandedVerylongsword", "Two-Handed Verylongsword");
        translationBuilder.add("amarong.yacl.option.boolean.twoHandedVerylongsword.desc", "Amarong Verylongsword is held with both hands.");
        translationBuilder.add("amarong.yacl.option.boolean.noKaleidoscopeZoom", "No Kaleidoscope Zoom");
        translationBuilder.add("amarong.yacl.option.boolean.noKaleidoscopeZoom.desc", "Don't zoom in while using a kaleidoscope.");
        translationBuilder.add("amarong.yacl.option.float.boomerangSpinMultiplier", "Boomerang Spin Multiplier");
        translationBuilder.add("amarong.yacl.option.float.boomerangSpinMultiplier.desc", "This value controls how fast an Amarong Boomerang should spin.");
        translationBuilder.add("amarong.yacl.option.float.staffRotationMultiplier", "Staff Rotation Multiplier");
        translationBuilder.add("amarong.yacl.option.float.staffRotationMultiplier.desc", "This value controls how fast the item rendered in an Amarong Staff should spin.");
        translationBuilder.add("amarong.yacl.option.enum.debugBeaconBeams", "Beacon Beam Debug Mode");
        translationBuilder.add("amarong.yacl.option.enum.debugBeaconBeams.desc", "Controls when Amarong should log the world time when a beacon's beam segments are set for rendering.");
        translationBuilder.add("amarong.yacl.option.enum.debugBeaconBeams.never", "NEVER");
        translationBuilder.add("amarong.yacl.option.enum.debugBeaconBeams.always", "ALWAYS");
        translationBuilder.add("amarong.yacl.option.enum.debugBeaconBeams.abnormal_only", "ONLY FOR ABNORMALITIES");
        translationBuilder.add("amarong.yacl.option.integer.maxBeaconBeamIterations", "Max Beacon Beam Iterations");
        translationBuilder.add("amarong.yacl.option.integer.maxBeaconBeamIterations.desc", "Controls the maximum number of iterations in the loop in the tick method of the beacon block entity. Setting this value too low may affect the smoothness of the color change of the rainbow beacon beam created by the Amarong Core if the beam intersects (transparent) blocks.");

        // damage types
        translationBuilder.addDamageType(
                registryLookup,
                AmarongDamageTypes.FLYING_TICKET_HIT,
                "%1$s was shot by %2$s with flying tickets",
                "%1$s was shot by %2$s with flying tickets",
                "%2$s used %3$s to shoot %1$s to death with flying tickets"
        );
        translationBuilder.addDamageType(
                registryLookup,
                AmarongDamageTypes.WATER_STREAM_HIT,
                "%1$s was sprinkled to death by %2$s",
                "%1$s was sprinkled to death by %2$s",
                "%1$s was sprinkled to death by %2$s using %3$s"
        );
        translationBuilder.addDamageType(
                registryLookup,
                AmarongDamageTypes.RAILGUN_HIT,
                "%1$s was shot by a railcannon from %2$s",
                "%1$s was shot by a railcannon from %2$s",
                "%1$s was shot by a railcannon from %2$s using %3$s"
        );
        translationBuilder.addDamageType(
                registryLookup,
                AmarongDamageTypes.BOOMERANG_HIT,
                "%1$s was shot by %2$s with an Amarong Boomerang",
                "%1$s was shot by %2$s with an Amarong Boomerang",
                "%2$s used %3$s to shot %1$s with an Amarong Boomerang"
        );

        // command
        translationBuilder.add("commands.amarongconfig.noyacl", "Unable to generate Amarong YACL config screen. Do you have YACL installed?");
        translationBuilder.add("commands.amarongconfig.fail", "Unable to open Amarong YACL config screen");
    }
}
