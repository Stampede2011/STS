/*
 * Decompiled with CFR 0.145.
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.animation;

@Deprecated
public class Frame<T> {
    private T frame;
    private int length;

    private Frame(T frame, int length) {
        this.frame = frame;
        this.length = length;
    }

    public static <T> Frame<T> of(T frame, int length) {
        return new Frame<T>(frame, length);
    }

    public T getFrame() {
        return this.frame;
    }

    public int getLength() {
        return this.length;
    }
}

