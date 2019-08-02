package invtweaks.gui;

import invtweaks.InvTweaks;
import invtweaks.InvTweaksConfig;
import invtweaks.InvTweaksConfigManager;
import invtweaks.InvTweaksObfuscation;
import invtweaks.api.container.ContainerSection;
import invtweaks.container.ContainerSectionManager;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Button that opens the inventory & chest settings screen.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettingsButton extends InvTweaksGuiIconButton {

    private static final Logger log = InvTweaks.log;

    public InvTweaksGuiSettingsButton(InvTweaksConfigManager cfgManager_, int id_, int x, int y, int w, int h, String displayString_, String tooltip, boolean useCustomTexture) {
        super(cfgManager_, id_, x, y, w, h, displayString_, tooltip, useCustomTexture);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        super.renderButton(mouseX, mouseY, partialTicks);

        // Display string
        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(Minecraft.getInstance());
        drawCenteredString(obf.getFontRenderer(), getMessage(), x + 5, y - 1, getTextColor(mouseX, mouseY));
    }

    /**
     * Displays inventory settings GUI
     */
    @Override
    public boolean mouseClicked(double i, double j, int l) {
        Minecraft minecraft = Minecraft.getInstance();

        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(minecraft);
        @Nullable InvTweaksConfig config = cfgManager.getConfig();

        if(minecraft.playerController.isSpectatorMode()) {
            return false;
        }

        if(super.mouseClicked(i, j, l)) {
            // Put hold item down if necessary
            ContainerSectionManager containerMgr;

            try {
                containerMgr = new ContainerSectionManager(ContainerSection.INVENTORY);
                if(!obf.getHeldStack().isEmpty()) {
                    // Put hold item down
                    for(int k = containerMgr.getSize() - 1; k >= 0; k--) {
                        if(containerMgr.getItemStack(k).isEmpty()) {
                            containerMgr.leftClick(k);
                            break;
                        }
                    }
                }
            } catch(Exception e) {
                log.error("mousePressed", e);
            }

            // Refresh config
            cfgManager.makeSureConfigurationIsLoaded();

            // Display menu
            obf.displayGuiScreen(new InvTweaksGuiSettings(minecraft, obf.getCurrentScreen(), config));
            return true;
        } else {
            return false;
        }
    }

}
