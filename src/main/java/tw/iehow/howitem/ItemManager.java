package tw.iehow.howitem;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.fabricmc.loader.impl.util.log.LogLevel;
import tw.iehow.howitem.items.BaseHowItem;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static final Map<Integer, BaseHowItem> items = new HashMap<>();

    static {
        String packageName = "tw.iehow.howitem.items";
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // Get the URL of the package directory
        URL packageURL = classLoader.getResource(packagePath);
        if (packageURL != null) {
            File directory = new File(URLDecoder.decode(packageURL.getFile(), StandardCharsets.UTF_8));
            if (directory.exists() && directory.isDirectory()) {
                // Recursively search for .class files
                searchForClasses(directory, packageName);
            }
        }
    }

    private static void searchForClasses(File directory, String packageName) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursive call for subdirectories
                searchForClasses(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                processClassFile(file, packageName);
            }
        }
    }

    private static void processClassFile(File file, String packageName) {
        String className = packageName + '.' + file.getName().replace(".class", "");
        Class<?> clazz;

        try {
            clazz = Class.forName(className);

            if (BaseHowItem.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                BaseHowItem instance = (BaseHowItem) clazz.getDeclaredConstructor().newInstance();
                Log.log(LogLevel.INFO, LogCategory.LOG, "HowItemLoaded: " + className);

                items.put(instance.getHash(), instance);
            }
        } catch (Exception e) {
            Log.log(LogLevel.ERROR, LogCategory.LOG, "Failed to process class file: " + className);
            Log.log(LogLevel.ERROR, LogCategory.LOG, e.getMessage());
        }
    }

    public static Map<Integer, BaseHowItem> getItems() {
        return items;
    }
}
