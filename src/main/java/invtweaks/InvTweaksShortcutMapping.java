package invtweaks;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jimeo Wan
 */
public class InvTweaksShortcutMapping {

    private static Map<String, Integer> GLFW_KEYMAP;

    @NotNull
    private List<Integer> keysToHold = new ArrayList<>();

    public InvTweaksShortcutMapping(int keyCode) {
        keysToHold.add(keyCode);
    }

    public InvTweaksShortcutMapping(@NotNull String... keyNames) {
        mapKeysIfNotMapped();

        for(String keyName : keyNames) {
            // TODO Fix this
            // - Accept both KEY_### and ###, in case someone
            //   takes the LWJGL Javadoc at face value
            // - Accept LALT & RALT instead of LMENU & RMENU
            keyName = keyName.trim();
            keysToHold.add(GLFW_KEYMAP.get(keyName));
        }
    }

    private static void mapKeysIfNotMapped() {
        // TODO PROBABLY WON'T WORK
        if(GLFW_KEYMAP != null) { return; }

        GLFW_KEYMAP = new HashMap<>();
        Field[] fields = GLFW.class.getFields();

        for(Field field : fields) {
            final int modifiers = field.getModifiers();
            String fieldName = field.getName();
            if(field.getType().equals(int.class) && Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers) && fieldName.startsWith("GLFW_KEY_")) {
                try {
                    GLFW_KEYMAP.put(fieldName.replace("GLFW_KEY_", ""), field.getInt(null));
                } catch(IllegalAccessException e) {
                    // TODO log message
                }
            }
        }
    }

    public boolean isTriggered(@NotNull Map<Integer, Boolean> pressedKeys) {
        for(Integer keyToHold : keysToHold) {
            if(keyToHold != GLFW.GLFW_KEY_LEFT_CONTROL) {
                if(!pressedKeys.get(keyToHold)) {
                    return false;
                }
            }
            // AltGr also activates LCtrl, make sure the real LCtrl has been pressed
            else if(!pressedKeys.get(keyToHold) || GLFW.glfwGetKey(Minecraft.getInstance().mainWindow.getHandle(), GLFW.GLFW_KEY_MENU) == 1) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public List<Integer> getKeyCodes() {
        return this.keysToHold;
    }
}
