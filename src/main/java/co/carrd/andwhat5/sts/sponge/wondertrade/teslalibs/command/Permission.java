/*
 * Decompiled with CFR 0.145.
 */
package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.command;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Permission {
    public String value();
}

