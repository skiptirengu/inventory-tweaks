package invtweaks.gui;

import invtweaks.InvTweaks;
import invtweaks.InvTweaksConfig;
import invtweaks.InvTweaksConst;
import invtweaks.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URL;

/**
 * The inventory and chest settings menu.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettings extends InvTweaksGuiSettingsAbstract {
    private final static int ID_MIDDLE_CLICK = 1;
    private final static int ID_BEFORE_BREAK = 2;
    private final static int ID_SHORTCUTS = 3;
    private final static int ID_SHORTCUTS_HELP = 4;
    private final static int ID_AUTO_REFILL = 5;
    private final static int ID_MORE_OPTIONS = 6;
    private final static int ID_BUG_SORTING = 7;
    private final static int ID_EDITRULES = 100;
    private final static int ID_EDITTREE = 101;
    private final static int ID_MODDEDTREE = 103;
    private final static int ID_HELP = 102;

    private static String labelMiddleClick;
    private static String labelShortcuts;
    private static String labelAutoRefill;
    private static String labelAutoRefillBeforeBreak;
    private static String labelMoreOptions;
    private static String labelBugSorting;

    @SuppressWarnings("unused")
    public InvTweaksGuiSettings(Screen parentScreen_) {
        this(Minecraft.getInstance(), parentScreen_, InvTweaks.getConfigManager().getConfig());
    }

    public InvTweaksGuiSettings(Minecraft mc_, Screen parentScreen_, InvTweaksConfig config_) {
        super(mc_, parentScreen_, config_);

        labelMiddleClick = I18n.format("invtweaks.settings.middleclick");
        labelShortcuts = I18n.format("invtweaks.settings.shortcuts");
        labelAutoRefill = I18n.format("invtweaks.settings.autorefill");
        labelAutoRefillBeforeBreak = I18n.format("invtweaks.settings.beforebreak");
        labelMoreOptions = I18n.format("invtweaks.settings.moreoptions");
        labelBugSorting = I18n.format("invtweaks.help.bugsorting");
    }

    @Override
    public void init() {
        super.init();

        @NotNull InvTweaksGuiPoint p = new InvTweaksGuiPoint();
        int i = 0;

        // Create large buttons
        moveToButtonCoords(1, p);
        addButton(new InvTweaksGuiBaseButton(ID_EDITRULES, p.getX() + 55, height / 6 + 96, I18n.format("invtweaks.settings.rulesfile"), this::actionPerformed));
        addButton(new InvTweaksGuiBaseButton(ID_HELP, p.getX() + 55, height / 6 + 144, I18n.format("invtweaks.settings.onlinehelp"), this::actionPerformed));

        moveToButtonCoords(11, p);
        addButton(new InvTweaksGuiBaseButton(ID_EDITTREE, p.getX(), p.getY(), 150, 20, I18n.format("invtweaks.settings.treefile"), this::actionPerformed));
        moveToButtonCoords(10, p);
        addButton(new InvTweaksGuiBaseButton(ID_MODDEDTREE, p.getX(), p.getY(), 150, 20, I18n.format("invtweaks.settings.moddedtreefile"), this::actionPerformed));


        // Create settings buttons
        moveToButtonCoords(i++, p);
        addButton(new InvTweaksGuiTooltipButton(ID_SHORTCUTS_HELP, p.getX() + 130, p.getY(), 20, 20, "?", "Shortcuts help", this::actionPerformed));
        @NotNull InvTweaksGuiTooltipButton shortcutsBtn = new InvTweaksGuiTooltipButton(ID_SHORTCUTS, p.getX(), p.getY(), 130, 20, computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_SHORTCUTS, labelShortcuts), I18n.format("invtweaks.settings.shortcuts.tooltip"), this::actionPerformed);
        addButton(shortcutsBtn);

        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton beforeBreakBtn = new InvTweaksGuiTooltipButton(ID_BEFORE_BREAK, p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK, labelAutoRefillBeforeBreak), I18n.format("invtweaks.settings.beforebreak.tooltip"), this::actionPerformed);
        addButton(beforeBreakBtn);

        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton autoRefillBtn = new InvTweaksGuiTooltipButton(ID_AUTO_REFILL, p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_AUTO_REFILL, labelAutoRefill), I18n.format("invtweaks.settings.autorefill.tooltip"), this::actionPerformed);
        addButton(autoRefillBtn);

        moveToButtonCoords(i++, p);
        addButton(new InvTweaksGuiTooltipButton(ID_MORE_OPTIONS, p.getX(), p.getY(), labelMoreOptions, I18n.format("invtweaks.settings.moreoptions.tooltip"), this::actionPerformed));

        addButton(new InvTweaksGuiTooltipButton(ID_BUG_SORTING, 5, this.height - 20, 100, 20, labelBugSorting, null, false, this::actionPerformed));

        //noinspection UnusedAssignment
        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton middleClickBtn = new InvTweaksGuiTooltipButton(ID_MIDDLE_CLICK, p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK, labelMiddleClick), I18n.format("invtweaks.settings.middleclick.tooltip"), this::actionPerformed);
        addButton(middleClickBtn);

        // Check if links to files are supported, if not disable the buttons
        if(!Desktop.isDesktopSupported()) {
            buttons.stream().filter(InvTweaksObfuscation::isGuiButton).forEach(button -> {
                InvTweaksGuiBaseButton baseButton = (InvTweaksGuiBaseButton) button;
                if(baseButton.id >= ID_EDITRULES && baseButton.id <= ID_HELP) {
                    baseButton.active = false;
                }
            });
        }
    }

    @Override
    protected void actionPerformed(@NotNull Button guiButton) {
        super.actionPerformed(guiButton);

        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }

        // GuiButton
        switch(((InvTweaksGuiBaseButton) guiButton).id) {
            // Toggle middle click shortcut
            case ID_MIDDLE_CLICK:
                toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK, labelMiddleClick);
                break;

            // Toggle auto-refill
            case ID_AUTO_REFILL:
                toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_AUTO_REFILL, labelAutoRefill);
                break;

            // Toggle auto-refill before tool break
            case ID_BEFORE_BREAK:
                toggleBooleanButton(guiButton, InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK, labelAutoRefillBeforeBreak);
                break;

            // Toggle shortcuts
            case ID_SHORTCUTS:
                toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_SHORTCUTS, labelShortcuts);
                break;

            // Shortcuts help
            case ID_SHORTCUTS_HELP:
                obf.displayGuiScreen(new InvTweaksGuiShortcutsHelp(minecraft, this, config));
                break;

            // More options screen
            case ID_MORE_OPTIONS:
                obf.displayGuiScreen(new InvTweaksGuiSettingsAdvanced(minecraft, parentScreen, config));
                break;

            // Sorting bug help screen
            case ID_BUG_SORTING:
                obf.displayGuiScreen(new InvTweaksGuiModNotWorking(minecraft, parentScreen, config));
                break;

            // Open rules configuration in external editor
            case ID_EDITRULES:
                try {
                    Desktop.getDesktop().open(InvTweaksConst.CONFIG_RULES_FILE);
                } catch(Exception e) {
                    InvTweaks.logInGameErrorStatic("invtweaks.settings.rulesfile.error", e);
                }
                break;

            // Open tree configuration in external editor
            case ID_EDITTREE:
                try {
                    Desktop.getDesktop().open(InvTweaksConst.CONFIG_TREE_FILE);
                } catch(Exception e) {
                    InvTweaks.logInGameErrorStatic("invtweaks.settings.treefile.error", e);
                }
                break;

            //Replace the current tree file with the modded tree file.
            case ID_MODDEDTREE:
                try {
                    Desktop.getDesktop().browse(new URL(InvTweaksConst.TREE_URL).toURI());
                } catch(Exception e) {
                    InvTweaks.logInGameErrorStatic("invtweaks.settings.moddedtreefile.error", e);
                }
                break;
            // Open help in browser
            case ID_HELP:
                try {
                    Desktop.getDesktop().browse(new URL(InvTweaksConst.HELP_URL).toURI());
                } catch(Exception e) {
                    InvTweaks.logInGameErrorStatic("invtweaks.settings.onlinehelp.error", e);
                }
                break;
        }
    }
}
