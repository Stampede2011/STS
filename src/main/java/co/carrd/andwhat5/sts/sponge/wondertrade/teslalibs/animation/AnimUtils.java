/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.flowpowered.math.TrigMath
 *  com.flowpowered.math.imaginary.Quaternionf
 *  com.flowpowered.math.vector.Vector3d
 *  com.flowpowered.math.vector.Vector3f
 *  org.spongepowered.api.effect.particle.ParticleEffect
 *  org.spongepowered.api.effect.particle.ParticleEffect$Builder
 *  org.spongepowered.api.effect.particle.ParticleOption
 *  org.spongepowered.api.effect.particle.ParticleOptions
 *  org.spongepowered.api.effect.particle.ParticleType
 *  org.spongepowered.api.effect.particle.ParticleTypes
 *  org.spongepowered.api.util.Color
 *  org.spongepowered.api.world.Location
 *  org.spongepowered.api.world.World
 *  org.spongepowered.api.world.extent.Extent
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaternionf;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class AnimUtils {
    public static final float TAU = 6.2831855f;

    public static void spawn(Location<World> location, ParticleEffect particle, Vector3f offset) {
        ((World)location.getExtent()).spawnParticles(particle, location.getPosition().add(offset.getX(), offset.getY(), offset.getZ()));
    }

    public static ParticleEffect particle(Color color) {
        return ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST).option(ParticleOptions.COLOR, color).build();
    }

    public static Color rainbow(float radians) {
        return Color.ofRgb((int)((int)AnimUtils.wave(radians, AnimUtils.shift(2), 127.5f, 127.5f)), (int)((int)AnimUtils.wave(radians, AnimUtils.shift(4), 127.5f, 127.5f)), (int)((int)AnimUtils.wave(radians, 0.0f, 127.5f, 127.5f)));
    }

    public static float wave(float radians, float shift, float center, float amplitude) {
        return AnimUtils.sin(radians + shift) * amplitude + center;
    }

    public static float sin(float radians) {
        return TrigMath.sin((double)(radians % 6.2831855f));
    }

    public static float cos(float radians) {
        return TrigMath.cos((double)(radians % 6.2831855f));
    }

    public static float shift(int segments) {
        return segments != 0 ? 6.2831855f / (float)segments : 0.0f;
    }

    public static float[] shift(float radians, float shift) {
        float[] shifts = new float[shift != 0.0f ? (int)Math.abs(6.2831855f / shift) : 0];
        if (shifts.length > 0) {
            shifts[0] = radians;
            for (int i = 1; i < shifts.length; ++i) {
                shifts[i] = shifts[i - 1] + shift;
            }
        }
        return shifts;
    }

    public static Vector3f circle(float radians) {
        return Vector3f.from((float)AnimUtils.cos(radians), (float)0.0f, (float)(-AnimUtils.sin(radians)));
    }

    public static Vector3f circle(float radians, Vector3f axis) {
        return Quaternionf.fromAngleRadAxis((float)radians, (Vector3f)axis).rotate(axis.equals((Object)Vector3f.UNIT_Y) ? Vector3f.UNIT_X : Vector3f.from((float)(-axis.getZ()), (float)0.0f, (float)axis.getX()).normalize());
    }

    public static Vector3f[] parametric(float radians, int segments) {
        Vector3f[] vecs = new Vector3f[Math.abs(segments)];
        if (vecs.length > 0) {
            float sin = AnimUtils.sin(radians);
            float[] shifts = AnimUtils.shift(radians + radians / (float)segments, 6.2831855f / (float)segments);
            for (int i = 0; i < vecs.length; ++i) {
                vecs[i] = Vector3f.from((float)AnimUtils.cos(shifts[i]), (float)sin, (float)AnimUtils.sin(shifts[i]));
            }
        }
        return vecs;
    }
}

