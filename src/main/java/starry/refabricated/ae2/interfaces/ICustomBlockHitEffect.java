package starry.refabricated.ae2.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public interface ICustomBlockHitEffect {
    @Environment(EnvType.CLIENT)
    boolean addHitEffects(BlockState state,
                          Level level,
                          HitResult target,
                          ParticleEngine effectRenderer);
}
