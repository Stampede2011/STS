/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.plugin.PluginContainer
 *  org.spongepowered.api.scheduler.Task
 *  org.spongepowered.api.scheduler.Task$Builder
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation;

import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation.Animatable;
import co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation.Frame;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

@Deprecated
public class Animator<T extends Animatable<U>, U> {
    private T handler;
    private List<Frame<U>> frames;
    private PluginContainer container;
    private Runnable completion;
    private Task runner;

    private Animator(T handler, List<Frame<U>> frames, PluginContainer container) {
        this.handler = handler;
        this.frames = frames;
        this.container = container;
    }

    public static <T extends Animatable<U>, U> Animator<T, U> of(T handler, List<Frame<U>> frames, PluginContainer container) {
        return new Animator<T, U>(handler, frames, container);
    }

    public void onCompletion(Runnable completion) {
        this.completion = completion;
    }

    public void start(int delay, boolean loop) {
        this.runner = Task.builder().execute(task -> this.run(0, loop)).delay((long)delay, TimeUnit.MILLISECONDS).submit((Object)this.container);
    }

    private void run(int index, boolean loop) {
        int nextIndex;
        Frame<U> frame = this.frames.get(index++);
        this.handler.nextFrame(frame.getFrame());
        int n = nextIndex = loop ? index % this.frames.size() : index;
        if (nextIndex < this.frames.size()) {
            this.runner = Task.builder().execute(task -> this.run(nextIndex, loop)).delay((long)frame.getLength(), TimeUnit.MILLISECONDS).submit((Object)this.container);
        } else {
            if (this.completion != null) {
                this.completion.run();
            }
            this.stop();
        }
    }

    public void stop() {
        if (this.runner != null) {
            this.runner.cancel();
        }
    }
}

