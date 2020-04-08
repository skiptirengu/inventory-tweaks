package invtweaks.gui;

import invtweaks.InvTweaks;
import invtweaks.InvTweaksConfigManager;
import invtweaks.InvTweaksHandlerSorting;
import invtweaks.api.SortingMethod;
import invtweaks.api.container.ContainerSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

/**
 * Chest sorting button
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSortingButton extends InvTweaksGuiIconButton {

    private final ContainerSection section = ContainerSection.CHEST;

    private SortingMethod algorithm;
    private int rowSize;

    public InvTweaksGuiSortingButton(InvTweaksConfigManager cfgManager_, int x, int y, int w, int h, String displayString_, String tooltip, SortingMethod algorithm_, int rowSize_, boolean useCustomTexture) {
        super(cfgManager_, x, y, w, h, displayString_, tooltip, useCustomTexture);
        algorithm = algorithm_;
        rowSize = rowSize_;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        super.renderButton(mouseX, mouseY, partialTicks);

        // Display symbol
        int textColor = getTextColor(mouseX, mouseY);
        switch(getMessage()) {
            case "h":
                // TODO drawRect stuff
                Screen.fill(x + 3, y + 3, x + width - 3, y + 4, textColor);
                Screen.fill(x + 3, y + 6, x + width - 3, y + 7, textColor);
                break;
            case "v":
                Screen.fill(x + 3, y + 3, x + 4, y + height - 3, textColor);
                Screen.fill(x + 6, y + 3, x + 7, y + height - 3, textColor);
                break;
            default:
                Screen.fill(x + 3, y + 3, x + width - 3, y + 4, textColor);
                Screen.fill(x + 5, y + 4, x + 6, y + 5, textColor);
                Screen.fill(x + 4, y + 5, x + 5, y + 6, textColor);
                Screen.fill(x + 3, y + 6, x + width - 3, y + 7, textColor);
                break;
        }
    }

    /**
     * Sort container
     */
    @Override
    public boolean clicked(double i, double j) {
        Minecraft minecraft = Minecraft.getInstance();

        if(minecraft.playerController.isSpectatorMode()) {
            return false;
        }

        if(super.clicked(i, j)) {
            try {
                new InvTweaksHandlerSorting(minecraft, cfgManager.getConfig(), section, algorithm, rowSize).sort();
            } catch(Exception e) {
                InvTweaks.logInGameErrorStatic("invtweaks.sort.chest.error", e);
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }

    }

}
