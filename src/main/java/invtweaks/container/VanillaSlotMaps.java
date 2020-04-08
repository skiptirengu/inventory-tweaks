package invtweaks.container;

import invtweaks.InvTweaksConst;
import invtweaks.api.container.ContainerSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class VanillaSlotMaps {

    public static Map<ContainerSection, List<Slot>> getSlotMapFromContainerClass(String classname, Container container) {
    	String contPrefix = "net.minecraft.inventory.container.";
    	if (classname.equalsIgnoreCase(contPrefix+"PlayerContainer")) {
    		return containerPlayerSlots(container);
    	} else if (
    			classname.equalsIgnoreCase(contPrefix+"MerchantContainer") || 
    			classname.equalsIgnoreCase(contPrefix+"HopperContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"BeaconContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"StonecutterContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"CartographyContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"GrindstoneContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"LecternContainer") ||
    			classname.equalsIgnoreCase(contPrefix+"LoomContainer")) 
    	{
    	    return unknownContainerSlots(container);	
    	} else if (classname.equalsIgnoreCase(contPrefix+"RepairContainer")) {
    		return containerRepairSlots(container);
    	} else if (classname.equalsIgnoreCase(contPrefix+"BrewingStandContainer")) {
    		return containerBrewingSlots(container);
    	} else if (classname.equalsIgnoreCase(contPrefix+"WorkbenchContainer")) {
    		return containerWorkbenchSlots(container);
    	} else if (classname.equalsIgnoreCase(contPrefix+"EnchantmentContainer")) {
    		return containerEnchantmentSlots(container);
    	} else if (classname.equalsIgnoreCase(contPrefix+"BlastFurnaceContainer") || classname.equalsIgnoreCase(contPrefix+"FurnaceContainer") || classname.equalsIgnoreCase(contPrefix+"SmokerContainer")) {
    		return containerFurnaceSlots(container);
    	} else if (classname.equalsIgnoreCase(contPrefix+"DispenserContainer") || classname.equalsIgnoreCase(contPrefix+"ChestContainer") || classname.equalsIgnoreCase(contPrefix+"ShulkerBoxContainer")) {
    		return containerChestDispenserSlots(container);
    	} else {
    		return unknownContainerSlots(container);
    	}
	}    
    
    public static boolean getIsChest(String classname, Container container, boolean override) {
    	String contPrefix = "net.minecraft.inventory.container.";
    	if (override) return true;
    	if (classname.equalsIgnoreCase(contPrefix+"DispenserContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"ChestContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"ShulkerBoxContainer")) 
    	{
    	    return true;	
    	} else {
    		return false;
    	}
	}  
    public static short getChestRowSize(String classname, Container container) {
    	String contPrefix = "net.minecraft.inventory.container.";
    	if (getIsChest(classname,container,false)) {
    		if (classname.equalsIgnoreCase(contPrefix+"DispenserContainer")) {
    			return 3;
    		}
    		return 9;
    	}
    	return 1;
    }
    
    public static boolean getShouldShowButtons(String classname, Container container, boolean override) {
    	String contPrefix = "net.minecraft.inventory.container.";
    	if (override) return true;
    	if (classname.equalsIgnoreCase(contPrefix+"PlayerContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"MerchantContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"HopperContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BeaconContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"CartographyContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"GrindstoneContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"LoomContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"RepairContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BrewingStandContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"WorkbenchContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BlastFurnaceContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"FurnaceContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"SmokerContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"DispenserContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"ChestContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"ShulkerBoxContainer")) 
    	{
    	    return true;	
    	} else {
    		return false;
    	}
	}   
    
    public static boolean getValidInventory(String classname, Container container, boolean override) {
    	String contPrefix = "net.minecraft.inventory.container.";
    	if (override) return true;
    	if (classname.equalsIgnoreCase(contPrefix+"PlayerContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"MerchantContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"HopperContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BeaconContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"StonecutterContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"CartographyContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"GrindstoneContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"LoomContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"RepairContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BrewingStandContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"WorkbenchContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"EnchantmentContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"BlastFurnaceContainer") ||
    		classname.equalsIgnoreCase(contPrefix+"FurnaceContainer") || 
    		classname.equalsIgnoreCase(contPrefix+"SmokerContainer"))
    	{
    	    return true;	
    	} else {
    		return false;
    	}
	}   
    
    @NotNull
    public static Map<ContainerSection, List<Slot>> containerPlayerSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.CRAFTING_OUT, container.inventorySlots.subList(0, 1));
        slotRefs.put(ContainerSection.CRAFTING_IN, container.inventorySlots.subList(1, 5));
        slotRefs.put(ContainerSection.ARMOR, container.inventorySlots.subList(5, 9));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(9, 45));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(9, 36));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(36, 45));

        return slotRefs;
    }    
    
    @OnlyIn(Dist.CLIENT)
    public static boolean containerCreativeIsInventory(CreativeScreen.CreativeContainer container) {
        @Nullable Screen currentScreen = Minecraft.getInstance().currentScreen;
        return currentScreen instanceof CreativeScreen && ((CreativeScreen) currentScreen).getSelectedTabIndex() == ItemGroup.INVENTORY.getIndex();
    }

    @NotNull
    @OnlyIn(Dist.CLIENT)
    public static Map<ContainerSection, List<Slot>> containerCreativeSlots(@NotNull CreativeScreen.CreativeContainer container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.ARMOR, container.inventorySlots.subList(5, 9));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(9, 45));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(9, 36));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(36, 45));

        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerChestDispenserSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        int size = container.inventorySlots.size();

        slotRefs.put(ContainerSection.CHEST, container.inventorySlots.subList(0, size - InvTweaksConst.INVENTORY_SIZE));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size - InvTweaksConst.HOTBAR_SIZE));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.HOTBAR_SIZE, size));

        return slotRefs;
    }

    /*@NotNull
    public static Map<ContainerSection, List<Slot>> containerHorseSlots(@NotNull HorseInventoryContainer container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        int size = container.inventorySlots.size();

        if(container.horse instanceof AbstractChestedHorseEntity && ((AbstractChestedHorseEntity) container.horse).hasChest()) { // Chest slots are only added if chest is added. Saddle/armor slots always exist.
            slotRefs.put(ContainerSection.CHEST, container.inventorySlots.subList(2, size - InvTweaksConst.INVENTORY_SIZE));
        }
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size - InvTweaksConst.HOTBAR_SIZE));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.HOTBAR_SIZE, size));

        return slotRefs;
    }*/

    public static boolean containerHorseIsInventory(@NotNull HorseInventoryContainer container) {
        // TODO is this used anywhere?
        // return container.horse instanceof AbstractChestedHorseEntity && ((AbstractChestedHorseEntity) container.horse).hasChest();
        return true;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerFurnaceSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.FURNACE_IN, container.inventorySlots.subList(0, 1));
        slotRefs.put(ContainerSection.FURNACE_FUEL, container.inventorySlots.subList(1, 2));
        slotRefs.put(ContainerSection.FURNACE_OUT, container.inventorySlots.subList(2, 3));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(3, 39));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(3, 30));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(30, 39));
        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerWorkbenchSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.CRAFTING_OUT, container.inventorySlots.subList(0, 1));
        slotRefs.put(ContainerSection.CRAFTING_IN, container.inventorySlots.subList(1, 10));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(10, 46));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(10, 37));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(37, 46));

        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerEnchantmentSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.ENCHANTMENT, container.inventorySlots.subList(0, 1));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(2, 38));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(2, 29));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(29, 38));

        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerBrewingSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.BREWING_BOTTLES, container.inventorySlots.subList(0, 3));
        slotRefs.put(ContainerSection.BREWING_INGREDIENT, container.inventorySlots.subList(3, 4));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(4, 40));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(4, 31));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(31, 40));

        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> containerRepairSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        slotRefs.put(ContainerSection.CRAFTING_IN, container.inventorySlots.subList(0, 2));
        slotRefs.put(ContainerSection.CRAFTING_OUT, container.inventorySlots.subList(2, 3));
        slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(3, 39));
        slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(3, 30));
        slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(30, 39));

        return slotRefs;
    }

    @NotNull
    public static Map<ContainerSection, List<Slot>> unknownContainerSlots(@NotNull Container container) {
        @NotNull Map<ContainerSection, List<Slot>> slotRefs = new HashMap<>();

        int size = container.inventorySlots.size();

        if(size >= InvTweaksConst.INVENTORY_SIZE) {
            // Assuming the container ends with the inventory, just like all vanilla containers.
            slotRefs.put(ContainerSection.CHEST, container.inventorySlots.subList(0, size - InvTweaksConst.INVENTORY_SIZE));
            slotRefs.put(ContainerSection.INVENTORY, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size));
            slotRefs.put(ContainerSection.INVENTORY_NOT_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.INVENTORY_SIZE, size - InvTweaksConst.HOTBAR_SIZE));
            slotRefs.put(ContainerSection.INVENTORY_HOTBAR, container.inventorySlots.subList(size - InvTweaksConst.HOTBAR_SIZE, size));
        } else {
            slotRefs.put(ContainerSection.CHEST, container.inventorySlots.subList(0, size));
        }

        return slotRefs;
    }
}
