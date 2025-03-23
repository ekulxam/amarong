Amarite but I'm braindead and bad at modding</br>
I am not affiliated with the creators of [Amarite]([https://modrinth.com/mod/amarite](https://modrinth.com/mod/amarite)) in any way.

Originally created for ModFest</br>
[![Made for ModFest: Carnival](https://raw.githubusercontent.com/ModFest/art/v2/badge/svg/carnival/cozy.svg)](https://modfest.net/carnival)

## Mod Overview:

### Items:
- Amarong Core : First thing that you'll craft in the mod. Made of 4 copper ingots, 4 amethyst shards, and 1 nether star. Placing it on a beacon make the beacon beam change colors. Use any item in the Rainbow Core Generators tag (by default, contains glow ink sacs and light blocks) on the amarong core block to change the core's cycling mode. Modes are jeb_, rainbow, and trans.
- Amarong Chunk : Put an amarong core in a stonecutter to obtain 4 amarong chunks. Can be recrafted into an amarong core. Used as ingredients.
- Amarong Sheet : Put an amarong chunk in the crafting table to obtain 2 amarong sheets. Used as ingredients.
- Amarong Verylongsword : Amarite Longsword, but less cool. Does big damage but has very slow attack speed. Biped models hold this with two hands. Charges by hitting entities (but the attack cooldown progress must be complete or almost complete).
- Handheld Duck : Shoots water. Right-click on water to fill up the duck. Right-click anywhere else to shoot.
- Amarong Ticket Launcher : Uses tickets from the Terrific Tickets mod as ammunition. Flying Tickets have less gravity than usual and will drop themselves upon being discarded. Right-click in inventory with tickets to load.
- Amarong Kaleidoscope : Extension of the spyglass, and the main reason this mod exists. In Random mode, applies a random shader on-use. Put in a crafting table (special recipe) to get a version that only applies one shader. Repeating this recipe multiple times results in cycling through the different shaders.
- Amarong Hammer : A mace, but it's made with an Amarong Core. It currently has no crafting recipe. Doubles as a pickaxe, and can be enchanted with all Mace enchantments. When the [Twirling](https://modrinth.com/mod/twirl) enchantment is on this item, and it is being used, the hammer will hit entities within two blocks of the user. Damage scales based on the level of Twirling.
- Amarong Boomerang : A throwable sword that will return to the user. The boomerang spawned on use will travel for a few seconds and then reverse direction. It can phase through walls (has noClip) and damage entities while traveling or returning. The boomerang will attempt to return to the inventory slot where it was thrown from.
- Amarong Staff : Increases reach and retains the attributes of the equipped item. Right-click in inventory to take/put the item on the staff. Allows blocks to be equipped and placed from further away.

Comes with EMI integration (for the Kaleidoscope recipes) because I couldn't be bothered to add support for JEI and REI (I might get around to it later, who knows?)

### Enchantments:
- Pneumatic : Ticket Launcher enchantment. Allows the ticket launcher to shoot wind charges when wind charges are in the offhand. Does not consume any tickets.
- Particle Accelerator : Ticket Launcher enchantment. Allows the ticket launcher to shoot throwable potions (splash / lingering) when they are in the offhand. Does not consume any tickets.
- Obscure : Verylongsword enchantment. Using the power of the nether star, the verylongsword bends light around the user for a few seconds so that they can no longer be seen at all. Requires a charge of 8.
- Railcannon : Verylongsword enchantment. Allows the verylongsword to shoot a beam of particles on right-click. Requires a charge of 20. The railcannon will fire a warning laser (end rod particles), then fire the actual shot a few seconds later.
- Capacity : Duck enchantment. Allows the duck to store more water.
- Vault : Hammer enchantment. Right-click to get flung into the air. (Almost) Pretty useless, and usually results in death by fall damage.

### Configuration (Options):
- Gamerules
  - amarong:flyingTicketsDrop - Controls whether Flying Tickets drop themselves upon impact
  - amarong:boomerangDamage - Controls the damage per hit of an Amarong Boomerang
  - amarong:verylongswordPassiveCharge - Controls whether verylongswords passively gain charge in a player's inventory
- YACL (YetAnotherConfigLib) Client Config
  - verboseLogging
  - twoHandedVerylongsword
  - noKaleidoscopeZoom
  - boomerangSpinMultiplier
  - staffRotationMultiplier
  - debugBeaconBeams
  - maxBeaconBeamIterations
- Resource Packs
  - nokaleidoscopeoverlay - Removes the rendering of the scope from kaleidoscopes.
  - smolverylongsword - Makes the Verylongsword always use the inventory texture.
  - oldticketlauncher - Uses the old Ticket Launcher texture/model.
  - amethysthandleticketlauncher - Makes the Ticket Launcher use an amethyst handle instead of a copper one (or the old solid black one). Loaded by default.
  - oldchunkandsheet - Uses the old Amarong Chunk and Amarong Sheet textures

### Attribution/Credits:
- Duck Squeak Sound: https://freesound.org/people/DANMITCH3LL/sounds/232017/