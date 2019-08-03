package invtweaks.forge;

import invtweaks.*;
import invtweaks.api.IItemTreeListener;
import invtweaks.api.SortingMethod;
import invtweaks.api.container.ContainerSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class ClientProxy extends CommonProxy {
    public static final KeyBinding KEYBINDING_SORT = new KeyBinding("invtweaks.key.sort", GLFW.GLFW_KEY_R, "invtweaks.key.category");
    public boolean serverSupportEnabled = false;
    public boolean serverSupportDetected = false;
    private InvTweaks instance;

    @Override
    public void preInit(@NotNull FMLCommonSetupEvent e) {
        super.preInit(e);
        InvTweaks.log = LogManager.getLogger(InvTweaksMod.class);
    }

    @Override
    public void init(FMLClientSetupEvent e) {
        super.init(e);

        // invtweaksChannel.get(Side.CLIENT).pipeline().addAfter("ITMessageToMessageCodec#0", "InvTweaks Handler Client", new ITPacketHandlerClient());

        Minecraft mc = e.getMinecraftSupplier().get();
        // Instantiate mod core
        instance = new InvTweaks(mc);

        ClientRegistry.registerKeyBinding(KEYBINDING_SORT);
    }

    @Override
    public void postInit(InterModProcessEvent event) {
        super.postInit(event);
        MinecraftForge.EVENT_BUS.addListener(this::onTick);
    }

    public void onTick(@NotNull TickEvent.ClientTickEvent tick) {
        if(tick.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            if(mc.world != null && mc.player != null) {
                if(mc.currentScreen != null) {
                    instance.onTickInGUI(mc.currentScreen);
                } else {
                    instance.onTickInGame();
                }
            }
        }
    }

    @SubscribeEvent
    public void notifyPickup(PlayerEvent.ItemPickupEvent e) {
        instance.setItemPickupPending(true);
    }

    @Override
    public void setServerAssistEnabled(boolean enabled) {
        serverSupportEnabled = serverSupportDetected && enabled;
        //InvTweaks.log.info("Server has support: " + serverSupportDetected + " support enabled: " + serverSupportEnabled);
    }

    @Override
    public void setServerHasInvTweaks(boolean hasInvTweaks) {
        serverSupportDetected = hasInvTweaks;
        serverSupportEnabled = hasInvTweaks && !InvTweaks.getConfigManager().getConfig().getProperty(InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP).equals(InvTweaksConfig.VALUE_FALSE);
        //InvTweaks.log.info("Server has support: " + hasInvTweaks + " support enabled: " + serverSupportEnabled);
    }

    @Override
    public void slotClick(@NotNull PlayerController playerController, int windowId, int slot, int data, @NotNull ClickType action, @NotNull PlayerEntity player) {
        //int modiferKeys = (shiftHold) ? 1 : 0 /* XXX Placeholder */;
        if(serverSupportEnabled) {
            player.openContainer.slotClick(slot, data, action, player);

            // invtweaksChannel.get(Dist.CLIENT).writeOutbound(new ITPacketClick(slot, data, action, windowId));
        } else {
            playerController.windowClick(windowId, slot, data, action, player);
        }
    }

    @Override
    public void sortComplete() {
        if(serverSupportEnabled) {
            // invtweaksChannel.get(Side.CLIENT).writeOutbound(new ITPacketSortComplete());
        }
    }

    @Override
    public void addOnLoadListener(IItemTreeListener listener) {
        InvTweaksItemTreeLoader.addOnLoadListener(listener);
    }

    @Override
    public boolean removeOnLoadListener(IItemTreeListener listener) {
        return InvTweaksItemTreeLoader.removeOnLoadListener(listener);
    }

    @Override
    public void setSortKeyEnabled(boolean enabled) {
        instance.setSortKeyEnabled(enabled);
    }

    @Override
    public void setTextboxMode(boolean enabled) {
        instance.setTextboxMode(enabled);
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j) {
        return instance.compareItems(i, j);
    }

    @Override
    public void sort(ContainerSection section, SortingMethod method) {
        // TODO: This seems like something useful enough to be a util method somewhere.
        Minecraft mc = Minecraft.getInstance();

        Container currentContainer = mc.player.container;
        if(InvTweaksObfuscation.isGuiContainer(mc.currentScreen)) {
            currentContainer = ((ContainerScreen) mc.currentScreen).getContainer();
        }

        try {
            new InvTweaksHandlerSorting(mc, InvTweaks.getConfigManager().getConfig(), section, method, InvTweaksObfuscation.getSpecialChestRowSize(currentContainer)).sort();
        } catch(Exception e) {
            InvTweaks.logInGameErrorStatic("invtweaks.sort.chest.error", e);
            e.printStackTrace();
        }
    }

    /*@Override
    public void addClientScheduledTask(@NotNull Runnable task) {
        Minecraft.getInstance().addScheduledTask(task);
    }*/

    /*@SubscribeEvent
    public void onConnectionToServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        setServerHasInvTweaks(false);
    }*/
}
