package invtweaks;

import com.google.common.collect.Lists;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class InvTweaksToolType {

    public static final ToolType HOE = ToolType.get("hoe");
    public static final ToolType FISHING_ROD = ToolType.get("fishing_rod");
    public static final ToolType SHEARS = ToolType.get("shears");

    private static final HashMap<ToolType, Integer> PRIORITY_MAP = new HashMap<>();
    static {
        PRIORITY_MAP.put(ToolType.PICKAXE, 1);
        PRIORITY_MAP.put(ToolType.AXE, 2);
        PRIORITY_MAP.put(ToolType.SHOVEL, 3);
        PRIORITY_MAP.put(HOE, 4);
        PRIORITY_MAP.put(SHEARS, 5);
        PRIORITY_MAP.put(FISHING_ROD, 6);
    }

    public static List<ToolType> getToolTypes(ItemStack itemStack) {
        LinkedList<ToolType> toolTypes = Lists.newLinkedList(itemStack.getToolTypes());
        Item item = itemStack.getItem();
        if(item instanceof HoeItem) {
            toolTypes.add(HOE);
        } else if(item instanceof FishingRodItem) {
            toolTypes.add(FISHING_ROD);
        } else if(item instanceof ShearsItem) {
            toolTypes.add(SHEARS);
        }
        toolTypes.sort(Comparator.comparing(PRIORITY_MAP::get));
        return toolTypes;
    }

    public static int compare(@NotNull ToolType toolType1, @NotNull ToolType toolType2) {
        return toolType1.getName().compareTo(toolType2.getName());
    }
}
