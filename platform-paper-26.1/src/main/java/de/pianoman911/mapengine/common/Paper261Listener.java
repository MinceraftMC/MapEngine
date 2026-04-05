package de.pianoman911.mapengine.common;

import de.pianoman911.mapengine.api.util.PassthroughMode;
import de.pianoman911.mapengine.common.platform.IListenerBridge;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public final class Paper261Listener extends MessageToMessageDecoder<Packet<?>> {

    private final Player player;
    private final IListenerBridge bridge;

    public Paper261Listener(Player player, IListenerBridge bridge) {
        this.player = player;
        this.bridge = bridge;
    }

    @Override
    public boolean acceptInboundMessage(Object msg) {
        return msg instanceof ServerboundInteractPacket || msg instanceof ServerboundSwingPacket;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Packet<?> msg, List<Object> out) {
        PassthroughMode passthroughMode = null;
        if (msg instanceof ServerboundInteractPacket interact) {
            int entityId = interact.entityId();
            Vec3 pos = interact.location();

            passthroughMode = this.bridge.handleInteract(this.player, entityId, pos.x, pos.y, pos.z);
        } else if (msg instanceof ServerboundSwingPacket) {
            passthroughMode = this.bridge.handleSwing(this.player);
            if (passthroughMode == PassthroughMode.ONLY_ANIMATION) {
                ClientboundAnimatePacket animatePacket = new ClientboundAnimatePacket(((CraftPlayer) this.player).getHandle(),
                        ClientboundAnimatePacket.SWING_MAIN_HAND);
                this.player.getTrackedBy().forEach(player -> ((CraftPlayer) player).getHandle().connection.send(animatePacket));
            }
        }
        if (passthroughMode != null && passthroughMode != PassthroughMode.ALL) {
            return;
        }

        out.add(msg);
    }
}
