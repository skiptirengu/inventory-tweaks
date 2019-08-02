package invtweaks.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import invtweaks.InvTweaksConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Icon-size button, which get drawns in a specific way to fit its small size.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiIconButton extends InvTweaksGuiTooltipButton {

    @NotNull
    private static ResourceLocation resourceButtonCustom = new ResourceLocation("inventorytweaks", "textures/gui/button10px.png");
    @NotNull
    private static ResourceLocation resourceButtonDefault = new ResourceLocation("textures/gui/widgets.png");
    protected InvTweaksConfigManager cfgManager;
    private boolean useCustomTexture;

    public InvTweaksGuiIconButton(InvTweaksConfigManager cfgManager_, int id_, int x, int y, int w, int h, String displayString_, String tooltip, boolean useCustomTexture_) {
        super(id_, x, y, w, h, displayString_, tooltip);
        cfgManager = cfgManager_;
        useCustomTexture = useCustomTexture_;
    }

    // TODO check this bad boi
    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        super.renderButton(mouseX, mouseY, partialTicks);

        // Draw background (use the 4 corners of the texture to fit best its small size)
        // TODO what getHoverState returns??
        // int k = getHoverState(isMouseOverButton(mouseX, mouseY));
        int k = isMouseOverButton(mouseX, mouseY) ? 1 : 0;
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        if(useCustomTexture) {
            Minecraft.getInstance().getTextureManager().bindTexture(resourceButtonCustom);
            GuiUtils.drawTexturedModalRect(x, y, (k - 1) * 10, 0, width, height, 0);
            GuiUtils.drawTexturedModalRect(x, y, (k - 1) * 10, 0, width, height, 0);
        } else {
            Minecraft.getInstance().getTextureManager().bindTexture(resourceButtonDefault);
            GuiUtils.drawTexturedModalRect(x, y, 1, 46 + k * 20 + 1, width / 2, height / 2, 0);
            GuiUtils.drawTexturedModalRect(x, y + height / 2, 1, 46 + k * 20 + 20 - height / 2 - 1, width / 2, height / 2, 0);
            GuiUtils.drawTexturedModalRect(x + width / 2, y, 200 - width / 2 - 1, 46 + k * 20 + 1, width / 2, height / 2, 0);
            GuiUtils.drawTexturedModalRect(x + width / 2, y + height / 2, 200 - width / 2 - 1, 46 + k * 20 + 19 - height / 2, width / 2, height / 2, 0);
        }

    }

}