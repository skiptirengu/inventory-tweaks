package invtweaks.container;

import invtweaks.InvTweaks;
import invtweaks.api.container.ContainerSection;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Allows to perform various operations on a single section of the inventory and/or containers. Works in both single and
 * multiplayer.
 *
 * @author Jimeo Wan
 */
public class ContainerSectionManager {

    private IContainerManager containerMgr;
    private ContainerSection section;

    public ContainerSectionManager(ContainerSection section_) throws Exception {
        this(InvTweaks.getContainerManager(Minecraft.getInstance().player.openContainer), section_);
    }

    public ContainerSectionManager(IContainerManager manager, ContainerSection section_) throws Exception {
        containerMgr = manager;
        section = section_;
        //InvTweaks.log.info(containerMgr.getClass().toString());
        if(!containerMgr.hasSection(section)) {
            throw new Exception("Section not available");
        }
    }

    public boolean move(int srcIndex, int destIndex) {
        return containerMgr.move(section, srcIndex, section, destIndex);
    }

    public boolean moveSome(int srcIndex, int destIndex, int amount) {
        return containerMgr.moveSome(section, srcIndex, section, destIndex, amount);
    }

    public boolean drop(int srcIndex) {
        return containerMgr.drop(section, srcIndex);
    }

    public void leftClick(int index) {
        containerMgr.leftClick(section, index);
    }

    public void click(int index, boolean rightClick) {
        containerMgr.click(section, index, rightClick);
    }

    public List<Slot> getSlots() {
        return containerMgr.getSlots(section);
    }

    public int getSize() {
        return containerMgr.getSize(section);
    }

    public int getFirstEmptyIndex() {
        return containerMgr.getFirstEmptyIndex(section);
    }

    @Nullable
    public Slot getSlot(int index) {
        return containerMgr.getSlot(section, index);
    }

    @NotNull
    public ItemStack getItemStack(int index) throws NullPointerException, IndexOutOfBoundsException {
        return containerMgr.getItemStack(section, index);
    }

    public Container getContainer() {
        return containerMgr.getContainer();
    }

    public void applyChanges() {
        containerMgr.applyChanges();
    }
}
