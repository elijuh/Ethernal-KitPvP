package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ReflectionUtil {

    public void unregisterCommands(CommandMap map, String command, List<String> aliases) {
        try {
            Field field = hasField(map.getClass(), "knownCommands") ? getField(map.getClass(), "knownCommands")
                    : getField(map.getClass().getSuperclass(), "knownCommands");
            Map<String, Command> commands = (Map<String, Command>) field.get(map);

            commands.remove(command);

            for (String alias : aliases) {
                commands.remove(alias);
            }

            field.set(map, commands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean hasField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
