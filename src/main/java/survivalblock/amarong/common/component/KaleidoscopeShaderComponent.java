package survivalblock.amarong.common.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import survivalblock.amarong.common.init.AmarongItems;

public class KaleidoscopeShaderComponent implements ClientTickingComponent {

    private final PlayerEntity obj;
    private boolean isShaderApplied = false;

    public KaleidoscopeShaderComponent(PlayerEntity player) {
        this.obj = player;
    }

    public boolean isShaderApplied() {
        return isShaderApplied;
    }

    @Override
    public void clientTick() {
        if (this.obj.isUsingItem()) {
            if (this.obj.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE) && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                if (!isShaderApplied) { // if the shader is not applied, then I refresh shader
                    isShaderApplied = true;
                    setShader();
                }
            }
        } else {
            if (isShaderApplied) {
                isShaderApplied = false;
                setShader();
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }

    private void setShader() {
        if (!this.obj.getWorld().isClient()) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }
        GameRenderer gameRenderer = client.gameRenderer;
        if (gameRenderer == null) {
            return;
        }
        try {
            gameRenderer.onCameraEntitySet(client.cameraEntity);
        } catch (Exception ignored) {

        }
    }
}
