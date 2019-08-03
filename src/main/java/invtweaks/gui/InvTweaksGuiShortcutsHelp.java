package invtweaks.gui;

import invtweaks.InvTweaksConfig;
import invtweaks.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;


public class InvTweaksGuiShortcutsHelp extends Screen {

    private final static int ID_DONE = 0;

    private InvTweaksObfuscation obf;
    private Screen parentScreen;
    private InvTweaksConfig config;

    public InvTweaksGuiShortcutsHelp(Minecraft mc_, Screen parentScreen_, InvTweaksConfig config_) {
        super(new StringTextComponent(I18n.format("invtweaks.help.shortcuts.title")));
        obf = new InvTweaksObfuscation(mc_);
        parentScreen = parentScreen_;
        config = config_;
    }

    // TODO initGui
    public void initGui() {
        addButton(new InvTweaksGuiBaseButton(ID_DONE, width / 2 - 100, height / 6 + 168, width, height, "Done"));
    }

    @Override
    public void render(int i, int j, float f) {
        // Note: 0x0000EEFF = blue color (currently unused)

        renderBackground();
        drawCenteredString(obf.getFontRenderer(), "WARNING: Since 1.3.1, shortcuts won't work as expected. Looking for a workaround...", width / 2, 5, 0xff0000);
        drawCenteredString(obf.getFontRenderer(), I18n.format("invtweaks.help.shortcuts.title"), width / 2, 20, 0xffffff); // Gui.drawCenteredString
        @NotNull String clickLabel = I18n.format("invtweaks.help.shortcuts.click");

        int y = height / 6 - 2;

        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.onestack"), "LSHIFT " + I18n.format("invtweaks.help.shortcuts.or") + " RSHIFT + " + clickLabel, 0x00FFFF00, y);
        y += 12;
        drawShortcutLine("", buildUpOrDownLabel(InvTweaksConfig.PROP_SHORTCUT_UP, obf.getKeyBindingForwardKeyCode(), I18n.format("invtweaks.help.shortcuts.forward")) + " + " + clickLabel, 0x00FFFF00, y);
        y += 12;
        drawShortcutLine("", buildUpOrDownLabel(InvTweaksConfig.PROP_SHORTCUT_DOWN, obf.getKeyBindingBackKeyCode(), I18n.format("invtweaks.help.shortcuts.backwards")) + " + " + clickLabel, 0x00FFFF00, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.oneitem"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ONE_ITEM) + " + " + clickLabel, 0x00FFFF00, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.allitems"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ALL_ITEMS) + " + " + clickLabel, 0x00FFFF00, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.everything"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_EVERYTHING) + " + " + clickLabel, 0x00FFFF00, y);
        y += 19;

        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.hotbar"), "0-9 + " + clickLabel, 0x0000FF33, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.emptyslot"), I18n.format("invtweaks.help.shortcuts.rightclick"), 0x0000FF33, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.drop"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_DROP) + " + " + clickLabel, 0x0000FF33, y);
        y += 19;

        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.craftall"), "LSHIFT, RSHIFT + " + clickLabel, 0x00FF8800, y);
        y += 12;
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.craftone"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ONE_ITEM) + " + " + clickLabel, 0x00FF8800, y);
        y += 19;

        String sortKeyName = getKeyName(config.getSortKeyCode(), "(Sort Key)");
        drawShortcutLine(I18n.format("invtweaks.help.shortcuts.selectconfig"), "0-9 + " + sortKeyName, 0x0088FFFF, y);

        super.render(i, j, f);
    }

    protected void actionPerformed(@NotNull Button guiButton) {
        // GuiButton
        if(guiButton instanceof InvTweaksGuiBaseButton && ((InvTweaksGuiBaseButton) guiButton).id == ID_DONE) {
            obf.displayGuiScreen(parentScreen);
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

    private String buildUpOrDownLabel(@NotNull String shortcutProp, int keyCode, String defaultKeyName) {
        @NotNull String shortcutLabel = config.getProperty(shortcutProp);
        String keyLabel = getKeyName(keyCode, defaultKeyName);
        if(keyLabel.equals(shortcutLabel)) {
            return keyLabel;
        } else {
            return keyLabel + "/" + shortcutLabel;
        }
    }

    private String getKeyName(int keyCode, String defaultValue) {
        try {
            return GLFW.glfwGetKeyName(keyCode, 0);
        } catch(Exception e) {
            return defaultValue;
        }
    }

    private void drawShortcutLine(@NotNull String label, @Nullable String value, int color, int y) {
        drawString(obf.getFontRenderer(), label, 30, y, -1); // drawString
        if(value != null) {
            drawString(obf.getFontRenderer(), value.contains("DEFAULT") ? "-" : value.replaceAll(", ", " " + I18n.format("invtweaks.help.shortcuts.or") + " "), width / 2 - 30, y, color); // drawString
        }
    }

}
