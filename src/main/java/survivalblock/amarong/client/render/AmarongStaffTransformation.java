package survivalblock.amarong.client.render;

import com.mojang.serialization.Codec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datafixer.AtmosphericCodecs;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "UnstableApiUsage", "ClassCanBeRecord"})
public class AmarongStaffTransformation {

    public static final Codec<Transformation> TRANSFORMATION_CODEC = AtmosphericCodecs.RCB.tuple(
            Codecs.VECTOR_3F.optionalFieldOf("rotation")
                    .xmap(
                            optional -> optional.orElse(Transformation.IDENTITY.rotation),
                            Optional::ofNullable
                    ), transformation -> transformation.rotation,
            Codecs.VECTOR_3F.optionalFieldOf("translation")
                    .xmap(
                            optional -> {
                                Vector3f translation = optional.orElse(Transformation.IDENTITY.translation);
                                translation.mul(0.0625F);
                                translation.set(MathHelper.clamp(translation.x, -5.0F, 5.0F), MathHelper.clamp(translation.y, -5.0F, 5.0F), MathHelper.clamp(translation.z, -5.0F, 5.0F));
                                return translation;
                            },
                            Optional::ofNullable
                    ), transformation -> transformation.translation,
            Codecs.VECTOR_3F.optionalFieldOf("scale")
                    .xmap(
                            optional -> {
                                Vector3f scale = optional.orElse(Transformation.IDENTITY.scale);
                                scale.set(MathHelper.clamp(scale.x, -4.0F, 4.0F), MathHelper.clamp(scale.y, -4.0F, 4.0F), MathHelper.clamp(scale.z, -4.0F, 4.0F));
                                return scale;
                                },
                            Optional::ofNullable
                    ), transformation -> transformation.scale,
            Transformation::new
    ).codec();

    // allows for combinations of uppercase and lowercase letters in the mode
    @SuppressWarnings("deprecation")
    public static final Codec<ModelTransformationMode> MODEL_TRANSFORMATION_MODE_CODEC = Codec.STRING.xmap(string ->
            ((StringIdentifiable.EnumCodec<ModelTransformationMode>) ModelTransformationMode.CODEC).byId(string.toLowerCase()), ModelTransformationMode::asString);

    public static final Codec<AmarongStaffTransformation> CODEC = AtmosphericCodecs.RCB.tuple(
            Codec.BOOL.optionalFieldOf("spin"), staffTransformation -> staffTransformation.spin,
            Codec.BOOL.optionalFieldOf("complex_spin"), staffTransformation -> staffTransformation.complexSpin,
            MODEL_TRANSFORMATION_MODE_CODEC.optionalFieldOf("mode", ModelTransformationMode.FIXED),staffTransformation -> staffTransformation.mode,
            TRANSFORMATION_CODEC.optionalFieldOf("transformation", Transformation.IDENTITY),staffTransformation -> staffTransformation.transformation,
            AmarongStaffTransformation::new
    ).codec();

    public static final AmarongStaffTransformation DEFAULT = new AmarongStaffTransformation(Optional.empty(), Optional.empty(), ModelTransformationMode.FIXED, Transformation.IDENTITY);

    private final Optional<Boolean> spin;
    private final Optional<Boolean> complexSpin;
    private final ModelTransformationMode mode;
    public final Transformation transformation;
    /*
    The only reason I'm using a codec and not json/gson is because Transformation.Deserializer is protected
    and I don't want to use an accesswidener
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AmarongStaffTransformation.class, new AmarongStaffTransformation.Deserializer())
            .registerTypeAdapter(ModelTransformationMode.class, new TransformationModeDeserializer())
            .registerTypeAdapter(Transformation.class, new Transformation.Deserializer())
            //.registerTypeAdapter(ModelOverride.class, new ModelOverride.Deserializer())
            .create();
     */

    public AmarongStaffTransformation(Optional<Boolean> spin, Optional<Boolean> complexSpin, ModelTransformationMode mode, Transformation transformation) {
        this.spin = spin;
        this.complexSpin = complexSpin;
        this.mode = mode;
        this.transformation = transformation;
    }

    public ModelTransformationMode getMode() {
        return this.mode;
    }

    public boolean spins() {
        return this.spin.orElse(false);
    }

    public boolean usesComplexSpin(ItemStack stack) {
        return this.usesComplexSpin(stack.getItem());
    }

    public boolean usesComplexSpin(Item item) {
        return this.complexSpin.orElse(item instanceof BlockItem);
    }

    public void apply(boolean leftHanded, MatrixStack matrices) {
        this.transformation.apply(leftHanded, matrices);
    }

    public void applyWithoutRotation(boolean leftHanded, MatrixStack matrices) {
        if (this.transformation == Transformation.IDENTITY) {
            return;
        }
        int i = leftHanded ? -1 : 1;
        matrices.translate((float)i * this.transformation.translation.x(), this.transformation.translation.y(), this.transformation.translation.z());
        matrices.scale(this.transformation.scale.x(), this.transformation.scale.y(), this.transformation.scale.z());
    }
}
