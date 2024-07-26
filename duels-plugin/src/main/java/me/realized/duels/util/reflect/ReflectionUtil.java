package me.realized.duels.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.realized.duels.util.Log;
import me.realized.duels.util.NumberUtil;
import org.bukkit.Bukkit;

public final class ReflectionUtil {

    private static final String PACKAGE_VERSION;

    static {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        PACKAGE_VERSION = packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public static int getMajorVersion() {
        String bukkitVersion = Bukkit.getServer().getBukkitVersion();
        // Extract the major version from the version string
        String[] versionParts = bukkitVersion.split("\.");
        if (versionParts.length > 0) {
            try {
                return Integer.parseInt(versionParts[0].replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                // Log error or handle it appropriately
                return 0;
            }
        }
        return 0;
    }

    public static Class<?> getClassUnsafe(final String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static Method getMethodUnsafe(final Class<?> clazz, final String name, final Class<?>... parameters) {
        try {
            return clazz.getMethod(name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Class<?> getNMSClass(final String name, final boolean logError) {
        try {
            return Class.forName("net.minecraft" + (getMajorVersion() < 17 ? (".server." + PACKAGE_VERSION) : "") + "." + name);
        } catch (ClassNotFoundException ex) {
            if (logError) {
                Log.error(ex.getMessage(), ex);
            }

            return null;
        }
    }

    public static Class<?> getNMSClass(final String name) {
        return getNMSClass(name, true);
    }

    private static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();

    public static String cbClass(String className) {
        return CRAFTBUKKIT_PACKAGE + "." + className;
    }

    public static Class<?> getCBClass(final String path) {
        return cbClass(path).getClass();
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... parameters) {
        try {
            return clazz.getMethod(name, parameters);
        } catch (NoSuchMethodException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static Method findDeclaredMethod(final Class<?> clazz, final String name, final Class<?>... parameters) throws NoSuchMethodException {
        final Method method = clazz.getDeclaredMethod(name, parameters);
        method.setAccessible(true);
        return method;
    }

    public static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?>... parameters) {
        try {
            return findDeclaredMethod(clazz, name, parameters);
        } catch (NoSuchMethodException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Method getDeclaredMethodUnsafe(final Class<?> clazz, final String name, final Class<?>... parameters) {
        try {
            return findDeclaredMethod(clazz, name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Field getDeclaredField(final Class<?> clazz, final String name) {
        try {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static Constructor<?> getConstructor(final Class<?> clazz, final Class<?>... parameters) {
        try {
            return clazz.getConstructor(parameters);
        } catch (NoSuchMethodException ex) {
            Log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private ReflectionUtil() {}
}
