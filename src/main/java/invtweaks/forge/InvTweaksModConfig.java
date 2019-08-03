package invtweaks.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;
import java.nio.file.Path;

public class InvTweaksModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue SHOW_FOOD_VALUES_IN_TOOLTIP;
    public static boolean SHOW_FOOD_VALUES_IN_TOOLTIP_DEFAULT = true;
    private static final String SHOW_FOOD_VALUES_IN_TOOLTIP_NAME = "showFoodValuesInTooltip";
    private static final String SHOW_FOOD_VALUES_IN_TOOLTIP_COMMENT =
            "If true, shows the hunger and saturation values of food in its tooltip while holding SHIFT";

    // Sorting settings
    public static final String CATEGORY_SORTING = "Sorting";
    public static final String ENABLE_MIDDLE_CLICK = "enableMiddleClick";
    public static final String SHOW_CHEST_BUTTONS = "showChestButtons";
    public static final String ENABLE_SORTING_ON_PICKUP = "enableSortingOnPickup";
    public static final String ENABLE_AUTO_EQUIP_ARMOR = "enableAutoEquipArmor";
    public static final String ENABLE_AUTO_REFILL = "enableAutoRefill";
    public static final String AUTO_REFILL_BEFORE_BREAK = "autoRefillBeforeBreak";
    public static final String AUTO_REFILL_DAMAGE_THRESHHOLD = "autoRefillDamageThreshhold";
    public static final String INVERT_TOOL_DAMAGE = "invertToolDamageSorting";
    // Shortcuts
    public static final String ENABLE_SHORTCUTS = "enableShortcuts";
    public static final String SHORTCUT_PREFIX = "shortcutKey";
    public static final String SHORTCUT_ONE_ITEM = "shortcutKeyOneItem";
    public static final String SHORTCUT_ALL_ITEMS = "shortcutKeyAllItems";
    public static final String SHORTCUT_EVERYTHING = "shortcutKeyEverything";
    public static final String SHORTCUT_DROP = "shortcutKeyDrop";
    public static final String SHORTCUT_UP = "shortcutKeyToUpperSection";
    public static final String SHORTCUT_DOWN = "shortcutKeyToLowerSection";
    public static final String TOOLTIP_PATH = "enableToolTipTreePath";
    // Other
    public static final String ENABLE_SOUNDS = "enableSounds";
    public static final String ENABLE_SERVER_ITEMSWAP = "enableServerItemSwap";
    public static final String ENABLE_CONFIG_LOADED_MESSAGE = "enableConfigLoadedMesssage";
    public static final String ENABLE_CONTAINER_MIRRORING = "enableContainerMirroring";

    public static void init(File file)
    {
        SPEC.setConfig(CommentedFileConfig.builder(file).build());
    }

    static
    {
        BUILDER.push("Test");
        SHOW_FOOD_VALUES_IN_TOOLTIP = BUILDER
                .comment(SHOW_FOOD_VALUES_IN_TOOLTIP_COMMENT)
                .define(SHOW_FOOD_VALUES_IN_TOOLTIP_NAME, SHOW_FOOD_VALUES_IN_TOOLTIP_DEFAULT);
        /*ALWAYS_SHOW_FOOD_VALUES_TOOLTIP = BUILDER
                .comment(ALWAYS_SHOW_FOOD_VALUES_TOOLTIP_COMMENT)
                .define(ALWAYS_SHOW_FOOD_VALUES_TOOLTIP_NAME, ALWAYS_SHOW_FOOD_VALUES_TOOLTIP_DEFAULT);
        SHOW_SATURATION_OVERLAY = BUILDER
                .comment(SHOW_SATURATION_OVERLAY_COMMENT)
                .define(SHOW_SATURATION_OVERLAY_NAME, SHOW_SATURATION_OVERLAY_DEFAULT);
        SHOW_FOOD_VALUES_OVERLAY = BUILDER
                .comment(SHOW_FOOD_VALUES_OVERLAY_COMMENT)
                .define(SHOW_FOOD_VALUES_OVERLAY_NAME, SHOW_FOOD_VALUES_OVERLAY_DEFAULT);
        SHOW_FOOD_EXHAUSTION_UNDERLAY = BUILDER
                .comment(SHOW_FOOD_EXHAUSTION_UNDERLAY_COMMENT)
                .define(SHOW_FOOD_EXHAUSTION_UNDERLAY_NAME, SHOW_FOOD_EXHAUSTION_UNDERLAY_DEFAULT);
        SHOW_FOOD_DEBUG_INFO = BUILDER
                .comment(SHOW_FOOD_DEBUG_INFO_COMMENT)
                .define(SHOW_FOOD_DEBUG_INFO_NAME, SHOW_FOOD_DEBUG_INFO_DEFAULT);*/
        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
