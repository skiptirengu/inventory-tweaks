package invtweaks;

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
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);

        // Display string
        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(mc);
        drawCenteredString(obf.getFontRenderer(), displayString, x + 5, y - 1, getTextColor(mouseX, mouseY));
    }

    /**
     * Displays inventory settings GUI
     */
    @Override
    public boolean mousePressed(Minecraft minecraft, int i, int j) {

        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(minecraft);
        @Nullable InvTweaksConfig config = cfgManager.getConfig();

        if(minecraft.playerController.isSpectatorMode()) {
            return false;
        }

        if(super.mousePressed(minecraft, i, j)) {
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
