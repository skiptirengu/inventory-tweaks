package invtweaks;

import invtweaks.api.container.ContainerSection;
import invtweaks.container.VanillaSlotMaps;
import invtweaks.gui.InvTweaksGuiBaseButton;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Minecraft 1.3 Obfuscation layer
 * De-obfuscated to some extent during port to 1.14.4. 
 * Does not rely on ContainerInfo/ContainerTransformer anymore, but may use more overhead.
 * @author Jimeo Wan, airrice
 */
public class InvTweaksObfuscation {

    private static final Logger log = InvTweaks.log;
    public Minecraft mc;

    public InvTweaksObfuscation(Minecraft mc_) {
        mc = mc_;
    }

	// Minecraft members

    @Nullable
    public static String getNamespacedID(@Nullable String id) {
        if(id == null) {
            return null;
        } else if(id.indexOf(':') == -1) {
            return "minecraft:" + id;
        }
        return id;
    }

    public static int getDisplayWidth() {
        // TODO Check
        // return FMLClientHandler.instance().getClient().displayWidth;
        return Minecraft.getInstance().getMainWindow().getWidth();
    }

    public static int getDisplayHeight() {
        // TODO Check
        // return FMLClientHandler.instance().getClient().displayHeight;
        return Minecraft.getInstance().getMainWindow().getHeight();
    }

    public static boolean areItemStacksEqual(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return itemStack1.isItemEqual(itemStack2) && itemStack1.getCount() == itemStack2.getCount();
    }

    @NotNull
    public static ItemStack getSlotStack(@NotNull Container container, int i) {
        // Slot
        Slot slot = container.inventorySlots.get(i);
        return (slot == null) ? ItemStack.EMPTY : slot.getStack(); // getStack
    }

    public static int getSlotNumber(Slot slot) {
        return slot.slotNumber;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static Slot getSlotAtMousePosition(@Nullable ContainerScreen guiContainer) {
        // Copied from GuiContainer
        if(guiContainer != null) {
            Container container = guiContainer.getContainer();

            int x = getMouseX(guiContainer);
            int y = getMouseY(guiContainer);
            for(int k = 0; k < container.inventorySlots.size(); k++) {
                Slot slot = container.inventorySlots.get(k);
                if(getIsMouseOverSlot(guiContainer, slot, x, y)) {
                    return slot;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean getIsMouseOverSlot(@Nullable ContainerScreen guiContainer, @NotNull Slot slot, int x, int y) {
        // Copied from GuiContainer
        if(guiContainer != null) {
            x -= guiContainer.getGuiLeft();
            y -= guiContainer.getGuiTop();
            return x >= slot.xPos - 1 && x < slot.xPos + 16 + 1 && y >= slot.yPos - 1 && y < slot.yPos + 16 + 1;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static int getMouseX(@NotNull ContainerScreen guiContainer) {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        GLFW.glfwGetCursorPos(guiContainer.getMinecraft().getMainWindow().getHandle(), xPos, yPos);
        return ((int) xPos[0] * guiContainer.width) / getDisplayWidth();
    }

    @OnlyIn(Dist.CLIENT)
    private static int getMouseY(@NotNull ContainerScreen guiContainer) {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        GLFW.glfwGetCursorPos(guiContainer.getMinecraft().getMainWindow().getHandle(), xPos, yPos);
        return guiContainer.height - ((int) yPos[0] * guiContainer.height) / getDisplayHeight() - 1;
    }

    public static short getSpecialChestRowSize(Container container) {
    	short toReturn = VanillaSlotMaps.getChestRowSize(container.getClass().getName(),container);
        return toReturn;
    }

    // EntityPlayer members

    // Static access
    public static boolean isValidChest(Container container) {
    	boolean isChest = VanillaSlotMaps.getIsChest(container.getClass().getName(),container,false);
        return isChest;
    }

    public static boolean isLargeChest(Container container) {
    	if (isValidChest(container) && container.getInventory().size() > InvTweaksConst.INVENTORY_SIZE) {
    		return true;
    	}
    	return false;
    }

    // InventoryPlayer members

    public static boolean isValidInventory(Container container) {
    	return true;
    	//boolean valid = VanillaSlotMaps.getValidInventory(container.getClass().getName(),container,false);
        //return valid;
    }

    public static boolean showButtons(Container container) {
    	return true;
        //boolean shouldShow = VanillaSlotMaps.getShouldShowButtons(container.getClass().getName(),container,false);
        //return shouldShow;
    }

    public static Map<ContainerSection, List<Slot>> getContainerSlotMap(Container container) {
    	Map<ContainerSection, List<Slot>> toReturn = new HashMap<ContainerSection, List<Slot>>();
        toReturn = VanillaSlotMaps.getSlotMapFromContainerClass(container.getClass().getName(),container);
        return toReturn;
    }
    
    public static boolean isGuiContainer(@Nullable Object o) { // GuiContainer (abstract class)
        return o != null && o instanceof ContainerScreen;
    }

    public static boolean isGuiInventoryCreative(@Nullable Object o) { // GuiInventoryCreative
        return o != null && o.getClass().equals(CreativeScreen.class);
    }

    public static boolean isGuiInventory(@Nullable Object o) { // GuiInventory
        return o != null && o.getClass().equals(InventoryScreen.class);
    }

    public static boolean isGuiButton(@Nullable Object o) { // GuiButton
        return o instanceof InvTweaksGuiBaseButton;
    }

    // FontRenderer members

    public static boolean isGuiEditSign(@Nullable Object o) {
        return o != null && o.getClass().equals(EditSignScreen.class);
    }

    public static boolean isItemArmor(@Nullable Object o) { // ItemArmor
        return o != null && o instanceof ArmorItem;
    }

    public static boolean isBasicSlot(@Nullable Object o) { // Slot
        return o != null && o.getClass().equals(Slot.class);
    }

    // Container members

    public static Container getCurrentContainer() {
        Minecraft mc = Minecraft.getInstance();
        Container currentContainer = mc.player.openContainer;
        /*if(InvTweaks.isGuiContainer(mc.currentScreen)) {
            currentContainer = ((ContainerScreen) mc.currentScreen).getContainer();
        }*/

        return currentContainer;
    }

    // Slot members

    public static boolean areSameItemType(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return !itemStack1.isEmpty() && !itemStack2.isEmpty() && (itemStack1.isItemEqual(itemStack2) || (itemStack1.getStack().isDamageable() && itemStack1.getItem() == itemStack2.getItem()));
    }

    public static boolean areItemsStackable(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        // TODO check if canItemStacksStackRelaxed is a better options
        return ItemHandlerHelper.canItemStacksStack(itemStack1, itemStack2);
    }

    public void addChatMessage(@NotNull String message) {
        if(mc.ingameGUI != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(message));
        }
    }

    public PlayerEntity getThePlayer() {
        return mc.player;
    }

    public PlayerController getPlayerController() {
        return mc.playerController;
    }

    @Nullable
    public Screen getCurrentScreen() {
        return mc.currentScreen;
    }

    public FontRenderer getFontRenderer() {
        return mc.fontRenderer;
    }

    public void displayGuiScreen(Screen parentScreen) {
        mc.displayGuiScreen(parentScreen);
    }

    public GameSettings getGameSettings() {
        return mc.gameSettings;
    }

    public int getKeyBindingForwardKeyCode() {
        return getGameSettings().keyBindForward.getKey().getKeyCode();
    }

    // Classes

    public int getKeyBindingBackKeyCode() {
        return getGameSettings().keyBindBack.getKey().getKeyCode();
    }

    public PlayerInventory getInventoryPlayer() { // InventoryPlayer
        return getThePlayer().inventory;
    }

    public NonNullList<ItemStack> getMainInventory() {
        return getInventoryPlayer().mainInventory;
    }

    @NotNull
    public ItemStack getHeldStack() {
        return getInventoryPlayer().getItemStack(); // getItemStack
    }

    @NotNull
    public ItemStack getFocusedStack() {
        return getInventoryPlayer().getCurrentItem(); // getCurrentItem
    }

    public int getFocusedSlot() {
        return getInventoryPlayer().currentItem; // currentItem
    }

    public boolean hasTexture(@NotNull ResourceLocation texture) {
        try {
            mc.getResourceManager().getResource(texture);
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    @NotNull
    public ItemStack getOffhandStack() {
        return getInventoryPlayer().offHandInventory.get(0);
    }
}
