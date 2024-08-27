package tw.iehow.howitem;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.fabricmc.loader.impl.util.log.LogLevel;
import tw.iehow.howitem.items.BaseHowItem;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.*;

public class ItemManager {

    private static final Map<Integer,BaseHowItem> items = new HashMap<>();

    static {
        String packageName = "tw.iehow.howitem.items";
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File directory = new File(Objects.requireNonNull(classLoader.getResource(packagePath)).getFile());

        if (directory.exists()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
            if (files != null) {
                for (File file : files) {
                    String className = packageName + '.' + file.getName().replace(".class", "");
                    Class<?> clazz = null;

                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    final boolean assignable = BaseHowItem.class.isAssignableFrom(clazz);
                    final boolean notInterface = !clazz.isInterface();
                    final boolean notAbstract = !Modifier.isAbstract(clazz.getModifiers());
                    if ( assignable && notInterface && notAbstract) {
                        try {
                            BaseHowItem instance = (BaseHowItem) clazz.getDeclaredConstructor().newInstance();
                            items.put(instance.getHash(),instance);
                        } catch (Exception e) {
                            Log.log(LogLevel.ERROR, LogCategory.LOG, "failed to create instance for item");
                            Log.log(LogLevel.ERROR, LogCategory.LOG, e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static Map<Integer,BaseHowItem> getItems() {
        return items;
    }
}
