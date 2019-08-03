/*
package invtweaks.network;

import invtweaks.InvTweaksConst;
import invtweaks.network.packets.ITPacket;
import invtweaks.network.packets.ITPacketClick;
import invtweaks.network.packets.ITPacketLogin;
import invtweaks.network.packets.ITPacketSortComplete;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import org.jetbrains.annotations.NotNull;

public class ITMessageToMessageCodec extends FMLIndexedMessageToMessageCodec<ITPacket> {
    public ITMessageToMessageCodec() {
        addDiscriminator(InvTweaksConst.PACKET_LOGIN, ITPacketLogin.class);
        addDiscriminator(InvTweaksConst.PACKET_CLICK, ITPacketClick.class);
        addDiscriminator(InvTweaksConst.PACKET_SORTCOMPLETE, ITPacketSortComplete.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, @NotNull ITPacket source, ByteBuf target) throws Exception {
        source.writeBytes(target);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, @NotNull ITPacket target) {
        target.readBytes(source);
    }
}
*/
