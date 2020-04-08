package invtweaks.gui;

import invtweaks.InvTweaks;
import invtweaks.InvTweaksConfig;
import invtweaks.InvTweaksConst;
import invtweaks.forge.InvTweaksMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * The inventory and chest advanced settings menu.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettingsAdvanced extends InvTweaksGuiSettingsAbstract {
    private static String labelChestButtons;
    private static String labelSortOnPickup;
    private static String labelEquipArmor;
    private static String labelEnableSounds;
    private static String labelServerAssist;
    private static String labelDisplayTooltip;

    public InvTweaksGuiSettingsAdvanced(Minecraft mc_, Screen parentScreen_, InvTweaksConfig config_) {
        super(mc_, parentScreen_, config_);

        labelSortOnPickup = I18n.format("invtweaks.settings.advanced.sortonpickup");
        labelEquipArmor = I18n.format("invtweaks.settings.advanced.autoequip");
        labelEnableSounds = I18n.format("invtweaks.settings.advanced.sounds");
        labelChestButtons = I18n.format("invtweaks.settings.chestbuttons");
        labelServerAssist = I18n.format("invtweaks.settings.advanced.serverassist");
        labelDisplayTooltip = I18n.format("invtweaks.settings.displaytooltip");
    }

    @Override
    public void init() {
        super.init();

        @NotNull InvTweaksGuiPoint p = new InvTweaksGuiPoint();
        int i = 0;

        // Create settings buttons
        i += 2;
        moveToButtonCoords(i++, p);
        InvTweaksGuiTooltipButton sortOnPickupBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_SORTING_ON_PICKUP, labelSortOnPickup), I18n.format("invtweaks.settings.advanced.sortonpickup.tooltip"), this::pickupSortToggleActionPerformed);
        addButton(sortOnPickupBtn);

        moveToButtonCoords(i++, p);
        InvTweaksGuiTooltipButton enableSoundsBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_SOUNDS, labelEnableSounds), I18n.format("invtweaks.settings.advanced.sounds.tooltip"), this::soundsToggleActionPerformed);
        addButton(enableSoundsBtn);

        moveToButtonCoords(i++, p);
        InvTweaksGuiTooltipButton chestBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_SHOW_CHEST_BUTTONS, labelChestButtons), I18n.format("invtweaks.settings.chestbuttons.tooltip"), this::chestButtonsToggleActionPerformed);
        addButton(chestBtn);

        moveToButtonCoords(i++, p);
        InvTweaksGuiTooltipButton autoEquipArmorBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_AUTO_EQUIP_ARMOR, labelEquipArmor), I18n.format("invtweaks.settings.advanced.autoequip.tooltip"), this::autoArmorToggleActionPerformed);
        addButton(autoEquipArmorBtn);

        moveToButtonCoords(i++, p);
        InvTweaksGuiTooltipButton serverAssistBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP, labelServerAssist), I18n.format("invtweaks.settings.advanced.serverassist.tooltip"), this::serversideToggleActionPerformed);
        addButton(serverAssistBtn);

        moveToButtonCoords(i, p);
        InvTweaksGuiTooltipButton displayTooltipBtn = new InvTweaksGuiTooltipButton(p.getX(), p.getY(), computeBooleanButtonLabel(InvTweaksConfig.PROP_TOOLTIP_PATH, labelDisplayTooltip), I18n.format("invtweaks.settings.displaytooltip.tooltip"), this::tooltipToggleActionPerformed);
        addButton(displayTooltipBtn);

        // Only create the button for mappings files if files are allowed
        if(Desktop.isDesktopSupported()) {
            moveToButtonCoords(1, p);
            addButton(new InvTweaksGuiTooltipButton((int) p.getX() + 55, height / 6 + 144, I18n.format("invtweaks.settings.advanced.mappingsfile"), null, this::editShortcutsActionPerformed));
        }
    }

    @Override
    public void render(int i, int j, float f) {
        super.render(i, j, f);
        int x = width / 2;
        drawCenteredString(obf.getFontRenderer(), I18n.format("invtweaks.settings.pvpwarning.pt1"), x, 40, 0x999999);
        drawCenteredString(obf.getFontRenderer(), I18n.format("invtweaks.settings.pvpwarning.pt2"), x, 50, 0x999999);
    }

    protected void tooltipToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_TOOLTIP_PATH, labelDisplayTooltip);
	}
	
	protected void pickupSortToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_SORTING_ON_PICKUP, labelSortOnPickup);
	}
	
	protected void autoArmorToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_AUTO_EQUIP_ARMOR, labelEquipArmor);
	}
	
	protected void soundsToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_SOUNDS, labelEnableSounds);
	}
	
	protected void chestButtonsToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_SHOW_CHEST_BUTTONS, labelChestButtons);
	}
         
	protected void serversideToggleActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		toggleBooleanButton(guiButton, InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP, labelServerAssist);
        InvTweaksMod.proxy.setServerAssistEnabled(!InvTweaks.getConfigManager().getConfig().getProperty(InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP).equals(InvTweaksConfig.VALUE_FALSE));
	}	
	
    protected void editShortcutsActionPerformed(@NotNull Button guiButton) {
        if(!(guiButton instanceof InvTweaksGuiBaseButton)) {
            return;
        }
		try {
            Desktop.getDesktop().open(InvTweaksConst.CONFIG_PROPS_FILE);
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.settings.advanced.mappingsfile.error", e);
        }
	}	            

}