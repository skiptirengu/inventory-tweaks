package invtweaks;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jimeo Wan
 */
public class InvTweaksShortcutMapping {

    @NotNull
    private List<Integer> keysToHold = new ArrayList<>();

    public InvTweaksShortcutMapping(int keyCode) {
        keysToHold.add(keyCode);
    }

    public InvTweaksShortcutMapping(@NotNull String... keyNames) {
        for(String keyName : keyNames) {
            // TODO Fix this
            // - Accept both KEY_### and ###, in case someone
            //   takes the LWJGL Javadoc at face value
            // - Accept LALT & RALT instead of LMENU & RMENU
            // keyName = keyName.trim().replace("KEY_", "").replace("ALT", "MENU");
            // keysToHold.add(Keyboard.getKeyIndex(keyName));
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
