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
        boolean filesSupported = Desktop.isDesktopSupported();
        // Create large buttons
        moveToButtonCoords(1, p);
        InvTweaksGuiBaseButton rulesbutton = new InvTweaksGuiBaseButton(p.getX() + 55, height / 6 + 96, I18n.format("invtweaks.settings.rulesfile"), this::editRulesActionPerformed);
        addButton(rulesbutton);
        rulesbutton.active = filesSupported;
        addButton(new InvTweaksGuiBaseButton(p.getX() + 55, height / 6 + 144, I18n.format("invtweaks.settings.onlinehelp"), btn->{try {
            Desktop.getDesktop().browse(new URL(InvTweaksConst.HELP_URL).toURI());
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.settings.onlinehelp.error", e);
        }}));

        moveToButtonCoords(11, p);
        InvTweaksGuiBaseButton treefilebutton = new InvTweaksGuiBaseButton(p.getX(), p.getY(), 150, 20, I18n.format("invtweaks.settings.treefile"), this::editTreeActionPerformed);
        addButton(treefilebutton);
        treefilebutton.active = filesSupported;
        moveToButtonCoords(10, p);
        InvTweaksGuiBaseButton moddedtreefilebutton = new InvTweaksGuiBaseButton(p.getX(), p.getY(), 150, 20, I18n.format("invtweaks.settings.moddedtreefile"), this::editModTreeActionPerformed);
        addButton(moddedtreefilebutton);
        moddedtreefilebutton.active = filesSupported;

        // Create settings buttons
        moveToButtonCoords(i++, p);
        addButton(new InvTweaksGuiTooltipButton(p.getX() + 130, p.getY(), 20, 20, "?", "Shortcuts help", btn-> {obf.displayGuiScreen(new InvTweaksGuiShortcutsHelp(minecraft, this, config));}));
        @NotNull InvTweaksGuiTooltipButton shortcutsBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), 130, 20, computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_SHORTCUTS, labelShortcuts), I18n.format("invtweaks.settings.shortcuts.tooltip"), this::shortcutsToggleActionPerformed);
        addButton(shortcutsBtn);

        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton beforeBreakBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK, labelAutoRefillBeforeBreak), I18n.format("invtweaks.settings.beforebreak.tooltip"), this::autoRefillBreakToggleActionPerformed);
        addButton(beforeBreakBtn);

        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton autoRefillBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_AUTO_REFILL, labelAutoRefill), I18n.format("invtweaks.settings.autorefill.tooltip"), this::autoRefillToggleActionPerformed);
        addButton(autoRefillBtn);

        moveToButtonCoords(i++, p);
        addButton(new InvTweaksGuiTooltipButton(p.getX(), p.getY(), labelMoreOptions, I18n.format("invtweaks.settings.moreoptions.tooltip"), btn->{obf.displayGuiScreen(new InvTweaksGuiSettingsAdvanced(minecraft, parentScreen, config));}));

        addButton(new InvTweaksGuiTooltipButton(5, this.height - 20, 100, 20, labelBugSorting, null, false, btn->{obf.displayGuiScreen(new InvTweaksGuiModNotWorking(minecraft, parentScreen, config));}));

        //noinspection UnusedAssignment
        moveToButtonCoords(i++, p);
        @NotNull InvTweaksGuiTooltipButton middleClickBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK, labelMiddleClick), I18n.format("invtweaks.settings.middleclick.tooltip"), this::middleToggleActionPerformed);
        addButton(middleClickBtn);
        // Check if links to files are supported, if not disable the buttons

    }
    
    protected void middleToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK, labelMiddleClick);
    }
    
    protected void autoRefillToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_AUTO_REFILL, labelAutoRefill);
    }
    
    protected void autoRefillBreakToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        toggleBooleanButton(guiButton, InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK, labelAutoRefillBeforeBreak);
    }
    
    protected void shortcutsToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_SHORTCUTS, labelShortcuts);
    }
    
    protected void editRulesActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        try {
            Desktop.getDesktop().open(InvTweaksConst.CONFIG_RULES_FILE);
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.settings.rulesfile.error", e);
        }
    }
    protected void editTreeActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        try {
            Desktop.getDesktop().open(InvTweaksConst.CONFIG_TREE_FILE);
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.settings.treefile.error", e);
        }
    }
    protected void editModTreeActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
        try {
            Desktop.getDesktop().browse(new URL(InvTweaksConst.TREE_URL).toURI());
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.settings.moddedtreefile.error", e);
        }
    }
}
