import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
public class Inspector {
    public static void main(String[] args) throws Exception {
        URL[] urls = {new URL("file:///E:/Minecraft Project/Vanilla Outsider Collections/Better Crossbows/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-a26c9a9f3c/26.1.2/minecraft-merged-a26c9a9f3c-26.1.2.jar")};
        URLClassLoader cl = new URLClassLoader(urls);
        Class<?> clazz = cl.loadClass("net.minecraft.world.item.CrossbowItem"); // CrossbowItem
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.print(m.getName() + "(");
            for (Class<?> p : m.getParameterTypes()) {
                System.out.print(p.getSimpleName() + ", ");
            }
            System.out.println(")");
        }
    }
}
