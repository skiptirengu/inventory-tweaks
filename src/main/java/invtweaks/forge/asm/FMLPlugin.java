/*
package invtweaks.forge.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"invtweaks.forge.asm", "invtweaks.forge.asm.compatibility"})
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.Name("Inventory Tweaks Coremod")
@IFMLLoadingPlugin.MCVersion("") // We're using runtime debof integration, so no point in being specific about version
public class FMLPlugin implements IFMLLoadingPlugin {
    public static boolean runtimeDeobfEnabled = false;

    @NotNull
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"invtweaks.forge.asm.ContainerTransformer"};
    }

    @NotNull
    @Override
    public String getAccessTransformerClass() {
        return "invtweaks.forge.asm.ITAccessTransformer";
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(@NotNull Map<String, Object> data) {
        runtimeDeobfEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }
}
*/
