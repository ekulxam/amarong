package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.init.AmarongTags;

import java.util.concurrent.CompletableFuture;

public class AmarongEnUsLangGenerator extends FabricLanguageProvider {

    public AmarongEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // item
        translationBuilder.add(AmarongItems.AMARONG_CHUNK, "Amarong Chunk");
        translationBuilder.add(AmarongItems.AMARONG_SHEET, "Amarong Sheet");
        translationBuilder.add(AmarongItems.KALEIDOSCOPE, "Amarong Kaleidoscope");
        translationBuilder.add(AmarongItems.AMARONG_VERYLONGSWORD, "Amarong Verylongsword");
        translationBuilder.add(AmarongItems.SOMEWHAT_A_DUCK, "Handheld Duck");
        translationBuilder.add(AmarongItems.TICKET_LAUNCHER, "Amarong Ticket Launcher");
        translationBuilder.add(AmarongItems.AMARONG_HAMMER, "Amarong Hammer");
        translationBuilder.add(AmarongBlocks.AMARONG_CORE, "Amarong Core");
        translationBuilder.add(AmarongItems.AMARONG_BOOMERANG, "Amarong Boomerang");
        translationBuilder.add(AmarongItems.AMARONG_STAFF, "Amarong Staff");
        translationBuilder.add("amarong.itemGroup.amarong_group", "Amarong");

        // entity types
        translationBuilder.add(AmarongEntityTypes.FLYING_TICKET, "Flying Ticket");
        translationBuilder.add(AmarongEntityTypes.WATER_STREAM, "Water Stream");
        translationBuilder.add(AmarongEntityTypes.RAILGUN, "Railcannon");
        translationBuilder.add(AmarongEntityTypes.BOOMERANG, "Amarong Boomerang");

        // gamerules
        translationBuilder.add("gamerule.amarong:flyingTicketsDrop", "Amarong - Flying Tickets drop themselves on being discarded");
        translationBuilder.add("gamerule.amarong:boomerangDamage", "Amarong - Boomerang Damage");

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
        translationBuilder.add("resourcePack.amarong.oldchunkandsheet.name", "Old Chunk and Sheet Textures");

        translationBuilder.add("dataPack.amarong.easy_core_duplication_pack.name", "Easy Amarong Core Duplication");

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
        translationBuilder.add("advancements.amarong.spider.description", "Look at a spider with a kaleidoscope");
        translationBuilder.add("advancements.amarong.creeper.title", "§aCreeper");
        translationBuilder.add("advancements.amarong.creeper.description", "Look at a creeper with a kaleidoscope");
        translationBuilder.add("advancements.amarong.invert.title", "§0Invert");
        translationBuilder.add("advancements.amarong.invert.description", "Look at an enderman with a kaleidoscope");
        translationBuilder.add("advancements.amarong.hammer_time.title", "It's Hammer Time!");
        translationBuilder.add("advancements.amarong.hammer_time.description", "Obtain an Amarong Hammer");

        // enchantments and enchantments descriptions
        translationBuilder.add("enchantment.amarong.pneumatic", "Pneumatic");
        translationBuilder.add("enchantment.amarong.pneumatic.desc", "Allows the Ticket Launcher to launch wind charges from the offhand.");
        translationBuilder.add("enchantment.amarong.particle_accelerator", "Particle Accelerator");
        translationBuilder.add("enchantment.amarong.particle_accelerator.desc", "Allows the Ticket Launcher to launch splash and lingering potions from the offhand.");
        translationBuilder.add("enchantment.amarong.obscure", "Obscure");
        translationBuilder.add("enchantment.amarong.obscure.desc", "Use the Amarong Verylongsword when fully charged to enter total invisibility temporarily.");
        translationBuilder.add("enchantment.amarong.railgun", "Railcannon");
        translationBuilder.add("enchantment.amarong.railgun.desc", "Use the Amarong Verylongsword when fully charged to fire off a railcannon.");
        translationBuilder.add("enchantment.amarong.capacity", "Capacity");
        translationBuilder.add("enchantment.amarong.capacity.desc", "Increases the amount of water a handheld duck can hold.");
        translationBuilder.add("enchantment.amarong.vault", "Vault");
        translationBuilder.add("enchantment.amarong.vault.desc", "Use the hammer to gain a boost in upwards velocity.");

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
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.VAULT_EFFECT, "Vault Effect");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD, "Exclusive Set - Verylongsword");
        translationBuilder.add(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER, "Exclusive Set - Ticket Launcher");

        // shaders
        translationBuilder.add("minecraft.shaders/post/notch.json", "Minecraft - Notch");
        translationBuilder.add("minecraft.shaders/post/fxaa.json", "Minecraft - FXAA");
        translationBuilder.add("minecraft.shaders/post/art.json", "Minecraft - Art");
        translationBuilder.add("minecraft.shaders/post/bumpy.json", "Minecraft - Bumpy");
        translationBuilder.add("minecraft.shaders/post/blobs2.json", "Minecraft - Blobs2");
        translationBuilder.add("minecraft.shaders/post/pencil.json", "Minecraft - Pencil");
        translationBuilder.add("minecraft.shaders/post/color_convolve.json", "Minecraft - Color Convolve");
        translationBuilder.add("minecraft.shaders/post/deconverge.json", "Minecraft - Deconverge");
        translationBuilder.add("minecraft.shaders/post/flip.json", "Minecraft - Flip");
        translationBuilder.add("minecraft.shaders/post/invert.json", "Minecraft - Invert");
        translationBuilder.add("minecraft.shaders/post/ntsc.json", "Minecraft - NTSC");
        translationBuilder.add("minecraft.shaders/post/outline.json", "Minecraft - Outline");
        translationBuilder.add("minecraft.shaders/post/phosphor.json", "Minecraft - Phosphor");
        translationBuilder.add("minecraft.shaders/post/scan_pincushion.json", "Minecraft - Scan Pincushion");
        translationBuilder.add("minecraft.shaders/post/sobel.json", "Minecraft - Sobel");
        translationBuilder.add("minecraft.shaders/post/bits.json", "Minecraft - Bits");
        translationBuilder.add("minecraft.shaders/post/desaturate.json", "Minecraft - Desaturate");
        translationBuilder.add("minecraft.shaders/post/green.json", "Minecraft - Green");
        translationBuilder.add("minecraft.shaders/post/blur.json", "Minecraft - Blur");
        translationBuilder.add("minecraft.shaders/post/wobble.json", "Minecraft - Wobble");
        translationBuilder.add("minecraft.shaders/post/blobs.json", "Minecraft - Blobs");
        translationBuilder.add("minecraft.shaders/post/antialias.json", "Minecraft - Antialias");
        translationBuilder.add("minecraft.shaders/post/creeper.json", "Minecraft - Creeper"); // aw man
        translationBuilder.add("minecraft.shaders/post/spider.json", "Minecraft - Spider");

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
        translationBuilder.add("death.attack.amarong.flying_ticket_hit", "%1$s was shot by %2$s with flying tickets");
        translationBuilder.add("death.attack.amarong.flying_ticket_hit.player", "%1$s was shot by %2$s with flying tickets");
        translationBuilder.add("death.attack.amarong.flying_ticket_hit.item", "%2$s used %3$s to shoot %1$s to death with flying tickets");
        translationBuilder.add("death.attack.amarong.water_stream_hit", "%1$s was sprinkled to death by %2$s");
        translationBuilder.add("death.attack.amarong.water_stream_hit.player", "%1$s was sprinkled to death by %2$s");
        translationBuilder.add("death.attack.amarong.water_stream_hit.item", "%1$s was sprinkled to death by %2$s using %3$s");
        translationBuilder.add("death.attack.amarong.railgun_hit", "%1$s was shot by a railcannon from %2$s");
        translationBuilder.add("death.attack.amarong.railgun_hit.player", "%1$s was shot by a railcannon from %2$s");
        translationBuilder.add("death.attack.amarong.railgun_hit.item", "%1$s was shot by a railcannon from %2$s using %3$s");
        translationBuilder.add("death.attack.amarong.boomerang_hit", "%1$s was shot by %2$s with an Amarong Boomerang");
        translationBuilder.add("death.attack.amarong.boomerang_hit.player", "%1$s was shot by %2$s with an Amarong Boomerang");
        translationBuilder.add("death.attack.amarong.boomerang_hit.item", "%2$s used %3$s to shot %1$s with an Amarong Boomerang");

        // command
        translationBuilder.add("commands.amarongconfig.noyacl", "Unable to generate Amarong YACL config screen. Do you have YACL installed?");
        translationBuilder.add("commands.amarongconfig.fail", "Unable to open Amarong YACL config screen");
    }
}
