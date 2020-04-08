package invtweaks.forge;

import invtweaks.api.IItemTreeListener;
import invtweaks.api.InvTweaksAPI;
import invtweaks.api.SortingMethod;
import invtweaks.api.container.ContainerSection;
import invtweaks.integration.ItemListSorter;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommonProxy implements InvTweaksAPI {
    // protected static EnumMap<Side, FMLEmbeddedChannel> invtweaksChannel;
    @Nullable
    private static MinecraftServer server;

    public void preInit(final FMLCommonSetupEvent e) {
    }

    public void init(final FMLClientSetupEvent event) {
        // invtweaksChannel = NetworkRegistry.INSTANCE.newChannel(InvTweaksConst.INVTWEAKS_CHANNEL, new ITMessageToMessageCodec());
        // invtweaksChannel.get(Side.SERVER).pipeline().addAfter("ITMessageToMessageCodec#0", "InvTweaks Handler Server", new ITPacketHandlerServer());
    }

    public void postInit(final InterModProcessEvent event) {
        ItemListSorter.LinkJEITComparator();
    }

    /*public void serverAboutToStart(@NotNull FMLServerAboutToStartEvent e) {
        server = e.getServer();
    }*/

    /*public void serverStopped(FMLServerStoppedEvent e) {
        server = null;
    }*/

    public void setServerAssistEnabled(boolean enabled) {
    }

    public void setServerHasInvTweaks(boolean hasInvTweaks) {
    }

    /* Action values:
     * 0: Standard Click
     * 1: Shift-Click
     * 2: Move item to/from hotbar slot (Depends on current slot and hotbar slot being full or empty)
     * 3: Duplicate item (only while in creative)
     * 4: Drop item
     * 5: Spread items (Drag behavior)
     * 6: Merge all valid items with held item
     */
    @OnlyIn(Dist.CLIENT)
    public void slotClick(PlayerController playerController, int windowId, int slot, int data, ClickType action, PlayerEntity player) {
    }

    public void sortComplete() {

    }

    @Override
    public void addOnLoadListener(IItemTreeListener listener) {

    }

    @Override
    public boolean removeOnLoadListener(IItemTreeListener listener) {
        return false;
    }

    @Override
    public void setSortKeyEnabled(boolean enabled) {
    }

    @Override
    public void setTextboxMode(boolean enabled) {
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j) {
        return 0;
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j, boolean onlyTreeSort) {
        return 0;
    }

    @Override
    public void sort(ContainerSection section, SortingMethod method) {
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(@NotNull PlayerEvent.PlayerLoggedInEvent e) {
        /*FMLEmbeddedChannel channel = invtweaksChannel.get(Side.SERVER);

        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(e.player);

        channel.writeOutbound(new ITPacketLogin());*/
    }
    public void addServerScheduledTask(@NotNull Runnable task) {
        server.enqueue((TickDelayedTask) task);
    }

    public void addClientScheduledTask(Runnable task) {
    }
}
