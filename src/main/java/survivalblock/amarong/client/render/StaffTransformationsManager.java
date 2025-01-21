package survivalblock.amarong.client.render;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryOps;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonReaderUtils;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.Profiler;
import survivalblock.amarong.common.Amarong;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class StaffTransformationsManager implements IdentifiableResourceReloadListener {

    public static final String STAFF_TRANSFORMATIONS_DIRECTORY = "amarong_staff_transformations";
    public static final ResourceFinder TRANSFORMATIONS_FINDER = ResourceFinder.json(STAFF_TRANSFORMATIONS_DIRECTORY);

    private Map<Identifier, AmarongStaffTransformation> transformations = new HashMap<>(256);
    private final StaffTransformations holder;

    public StaffTransformationsManager(StaffTransformations holder) {
        this.holder = holder;
    }

    public AmarongStaffTransformation getDefault() {
        return AmarongStaffTransformation.DEFAULT;
    }

    public AmarongStaffTransformation getTransformation(Item item) {
        Identifier id = Registries.ITEM.getId(item);
        // for some reason, the ids have the format <namespace>:amarong_staff_transformations<path>.json and I don't know how to fix that right now so I'm using this workaround
        // hopefully string operations are fast
        id = Identifier.of(id.getNamespace(), STAFF_TRANSFORMATIONS_DIRECTORY + "/" + id.getPath() + ".json");
        return this.transformations.getOrDefault(id, this.getDefault());
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        prepareProfiler.startTick();
        CompletableFuture<Map<Identifier, AmarongStaffTransformation>> completableFuture = reloadTransformations(manager, prepareExecutor);
        return completableFuture
                .thenCompose(synchronizer::whenPrepared)
                .thenAcceptAsync(result -> this.upload(result, applyProfiler), applyExecutor);
    }

    private void upload(Map<Identifier, AmarongStaffTransformation> staffTransformations, Profiler profiler) {
        profiler.startTick();
        profiler.push("upload");
        this.transformations = staffTransformations;
        this.holder.reload();
        profiler.pop();
        profiler.endTick();
    }

    // what did I just create
    // I love it when I just mash together a bunch of code
    private CompletableFuture<Map<Identifier, AmarongStaffTransformation>> reloadTransformations(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> TRANSFORMATIONS_FINDER.findResources(resourceManager), executor)
                .thenCompose(
                        staffTransformations -> {
                            List<CompletableFuture<Pair<Identifier, AmarongStaffTransformation>>> list = new ArrayList<>(staffTransformations.size());

                            for (Map.Entry<Identifier, Resource> entry : staffTransformations.entrySet()) {
                                list.add(CompletableFuture.supplyAsync(() -> {
                                    try {
                                        JsonReader reader = new JsonReader(entry.getValue().getReader());
                                        reader.setLenient(false);
                                        Pair<Identifier, AmarongStaffTransformation> var2x;
                                        try {
                                            var2x = Pair.of(entry.getKey(), AmarongStaffTransformation.CODEC.parse(JsonOps.INSTANCE, Streams.parse(reader)).getOrThrow(JsonParseException::new));
                                        } catch (Throwable var5) {
                                            try {
                                                reader.close();
                                            } catch (Throwable var4x) {
                                                var5.addSuppressed(var4x);
                                            }

                                            throw var5;
                                        }
                                        reader.close();
                                        return var2x;
                                    } catch (Exception var6) {
                                        Amarong.LOGGER.error("Failed to load staff transformation {}", entry.getKey(), var6);
                                        return null;
                                    }
                                }, executor));
                            }
                            return Util.combineSafe(list)
                                    .thenApply(staffTransformationsx -> staffTransformationsx.stream().filter(Objects::nonNull).collect(Collectors.toUnmodifiableMap(Pair::getFirst, Pair::getSecond)));
                        }
                );
    }

    @Override
    public Identifier getFabricId() {
        return Amarong.id("staff_transformations");
    }
}
