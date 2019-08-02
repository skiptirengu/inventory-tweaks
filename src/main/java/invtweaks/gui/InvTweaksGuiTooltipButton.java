package invtweaks.gui;

import invtweaks.InvTweaksConst;
import invtweaks.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.config.HoverChecker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Icon-size button, which get drawns in a specific way to fit its small size.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiTooltipButton extends InvTweaksGuiBaseButton {
    public final static int LINE_HEIGHT = 11;

    private int hoverTime = 0;
    private long prevSystemTime = 0;

    @Nullable
    private String tooltip = null;
    @Nullable
    private String[] tooltipLines = null;
    private int tooltipWidth = -1;
    private boolean drawBackground = true;
    private HoverChecker hoverChecker;

    /**
     * Default size is 150, the common "GuiSmallButton" button size.
     */
    public InvTweaksGuiTooltipButton(int id_, int x, int y, @NotNull String displayString_, String tooltip_) {
        this(id_, x, y, 150, 20, displayString_, tooltip_);
    }

    public InvTweaksGuiTooltipButton(int id_, int x, int y, int w, int h, @NotNull String displayString_, @Nullable String tooltip_) {
        this(id_, x, y, w, h, displayString_, tooltip_, true);
    }

    public InvTweaksGuiTooltipButton(int id_, int x, int y, int w, int h, @NotNull String displayString_, @Nullable String tooltip_, boolean drawBackground_) {
        super(id_, x, y, w, h, displayString_);
        if(tooltip_ != null) {
            setTooltip(tooltip_);
        }
        drawBackground = drawBackground_;
        hoverChecker = new HoverChecker(this, InvTweaksConst.TOOLTIP_DELAY);
    }

    // TODO geez
    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        if(this.drawBackground) {
            super.renderButton(mouseX, mouseY, partialTicks);
        } else {
            this.drawString(mc.fontRenderer, this.getMessage(), this.x, this.y + (this.height - 8) / 2, 0x999999);
        }

        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(mc);

        if(tooltipLines != null) {

            // Draw tooltip if hover time is long enough
            if(hoverChecker.checkHover(mouseX, mouseY) && tooltipLines != null) {
                FontRenderer fontRenderer = obf.getFontRenderer();

                // Compute tooltip params
                int x = mouseX + 12, y = mouseY - LINE_HEIGHT * tooltipLines.length;
                if(tooltipWidth == -1) {
                    for(String line : tooltipLines) {
                        tooltipWidth = Math.max(fontRenderer.getStringWidth(line), tooltipWidth);
                    }
                }
                if(x + tooltipWidth > obf.getCurrentScreen().width) {
                    x = obf.getCurrentScreen().width - tooltipWidth;
                }

                // Draw background
                // TODO does this solve our issue
                GuiUtils.drawGradientRect(400, x - 3, y - 3, x + tooltipWidth + 3, y + LINE_HEIGHT * tooltipLines.length, 0xc0000000, 0xc0000000);
                // drawGradientRect(x - 3, y - 3, x + tooltipWidth + 3, y + LINE_HEIGHT * tooltipLines.length, 0xc0000000, 0xc0000000);

                // Draw lines
                int lineCount = 0;
                for(@NotNull String line : tooltipLines) {
                    int j1 = y + (lineCount++) * LINE_HEIGHT;
                    int k = -1;
                    fontRenderer.drawStringWithShadow(line, x, j1, k);
                }
            }
        }

    }

    protected boolean isMouseOverButton(int i, int j) {
        return hoverChecker.checkHover(i, j);
        // return i >= x && j >= y && i < (x + width) && j < (y + height);
    }

    protected int getTextColor(int i, int j) {

        int textColor = 0xffe0e0e0;
        if(!active) {
            textColor = 0xffa0a0a0;
        } else if(isMouseOverButton(i, j)) {
            textColor = 0xffffffa0;
        }
        return textColor;

    }

    @Nullable
    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(@NotNull String tooltip_) {
        tooltip_ = tooltip_.replace("\\n", "\n");
        tooltip = tooltip_;
        tooltipLines = tooltip.split("\n");
    }
}
