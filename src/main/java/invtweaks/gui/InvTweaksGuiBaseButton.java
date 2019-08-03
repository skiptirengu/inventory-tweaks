package invtweaks.gui;

import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.jetbrains.annotations.Nullable;

/**
 * Base button used by all button on the mod
 *
 * @author Thiago Oliveira
 */
public class InvTweaksGuiBaseButton extends GuiButtonExt {

    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;

    public final int id;

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, String displayString, IPressable handler) {
        this(id, xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, displayString, handler);
    }

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, String displayString) {
        this(id, xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, displayString, z -> {});
    }

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, int width, int height, String displayString) {
        this(id, xPos, yPos, width, height, displayString, z -> {});
    }

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, int width, int height, String displayString, @Nullable IPressable handler) {
        super(xPos, yPos, width, height, displayString, handler == null ? z -> {} : handler);
        this.id = id;
    }
}
