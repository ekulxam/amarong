package survivalblock.amarong.common.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Set;

@FunctionalInterface
public interface RailgunRender {

    void invoke(World world, Set<PlayerEntity> encountered, Vec3d pitchYaw, Vec3d currentRaycastPosition, double x, double y, double z, int iterations);
}
