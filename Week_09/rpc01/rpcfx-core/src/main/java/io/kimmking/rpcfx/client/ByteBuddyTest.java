package io.kimmking.rpcfx.client;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyTest {
    public static void main(String[] args) {
        new ByteBuddyTest().a();
    }

    private void a() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        try {
            System.out.println(dynamicType.newInstance().toString());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // assertThat(dynamicType.newInstance().toString(), is("Hello World!"));
    }

}
