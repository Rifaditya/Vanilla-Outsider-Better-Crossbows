import java.lang.reflect.Method;
public class TestLevel {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("net.minecraft.world.level.Level");
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals("playSound")) {
                System.out.println(m);
            }
        }
    }
}
