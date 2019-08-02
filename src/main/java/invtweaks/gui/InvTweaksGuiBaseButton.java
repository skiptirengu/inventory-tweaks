package invtweaks.gui;

import net.minecraftforge.fml.client.config.GuiButtonExt;

public class InvTweaksGuiBaseButton extends GuiButtonExt {

    public final int id;

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, String displayString) {
        this(id, xPos, yPos, 150, 20, displayString, z -> {});
    }

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, int width, int height, String displayString) {
        this(id, xPos, yPos, width, height, displayString, z -> {});
    }

    public InvTweaksGuiBaseButton(int id, int xPos, int yPos, int width, int height, String displayString, IPressable handler) {
        super(xPos, yPos, width, height, displayString, handler);
        this.id = id;
    }
}
