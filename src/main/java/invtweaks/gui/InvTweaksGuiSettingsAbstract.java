package invtweaks.gui;

import invtweaks.InvTweaks;
import invtweaks.InvTweaksConfig;
import invtweaks.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

/**
 * The inventory and chest settings menu.
 *
 * @author Jimeo Wan
 */
public abstract class InvTweaksGuiSettingsAbstract extends Screen {

    protected static final Logger log = InvTweaks.log;
    protected final static int ID_DONE = 200;
    protected static String ON;
    protected static String OFF;
    protected static String LABEL_DONE;
    protected InvTweaksObfuscation obf;
    protected InvTweaksConfig config;
    protected Screen parentScreen;

    public InvTweaksGuiSettingsAbstract(Minecraft mc_, Screen parentScreen_, InvTweaksConfig config_) {
        super(new StringTextComponent(I18n.format("invtweaks.settings.title")));

        LABEL_DONE = I18n.format("invtweaks.settings.exit");
        ON = ": " + I18n.format("invtweaks.settings.on");
        OFF = ": " + I18n.format("invtweaks.settings.off");

        minecraft = mc_;
        obf = new InvTweaksObfuscation(mc_);
        parentScreen = parentScreen_;
        config = config_;
    }

    @Override
    protected void init() {
        @NotNull InvTweaksGuiPoint p = new InvTweaksGuiPoint();
        moveToButtonCoords(1, p);
        addButton(new InvTweaksGuiBaseButton(ID_DONE, p.getX() + 55, height / 6 + 168, LABEL_DONE, this::actionPerformed)); // GuiButton
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        drawCenteredString(obf.getFontRenderer(), I18n.format("invtweaks.settings.title"), width / 2, 20, 0xffffff);
        super.render(i, j, f);
    }

    protected void actionPerformed(@NotNull Button guiButton) {
        if(guiButton instanceof InvTweaksGuiBaseButton) {
            InvTweaksGuiBaseButton baseButton = (InvTweaksGuiBaseButton) guiButton;
            // GuiButton
            if(baseButton.id == ID_DONE) {
                obf.displayGuiScreen(parentScreen);
            }
        }
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        boolean ret = super.keyPressed(key1, key2, key3);
        if(key1 == GLFW.GLFW_KEY_ESCAPE) {
            obf.displayGuiScreen(parentScreen);
        }
        return ret;
    }

    protected void moveToButtonCoords(int buttonOrder, @NotNull InvTweaksGuiPoint p) {
        p.setX(width / 2 - 155 + ((buttonOrder + 1) % 2) * 160);
        p.setY(height / 6 + (buttonOrder / 2) * 24);
    }

    protected void toggleBooleanButton(@NotNull Button guibutton, @NotNull String property, String label) {
        boolean enabled = !Boolean.parseBoolean(config.getProperty(property));
        config.setProperty(property, Boolean.toString(enabled));
        guibutton.setMessage(computeBooleanButtonLabel(property, label));
    }

    @NotNull
    protected String computeBooleanButtonLabel(@NotNull String property, String label) {
        @NotNull String propertyValue = config.getProperty(property);
        Boolean enabled = Boolean.valueOf(propertyValue);
        return label + ((enabled) ? ON : OFF);
    }

}
