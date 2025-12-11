package de.pianoman911.mapengine.common;

import de.pianoman911.mapengine.common.platform.IListenerBridge;
import de.pianoman911.mapengine.common.platform.IPlatform;
import de.pianoman911.mapengine.common.platform.IPlatformProvider;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public final class Paper12111Provider implements IPlatformProvider {

    private static final IntSet SUPPORTED_PROTOCOLS = IntSet.of(
            774 // 1.21.11
    );

    @SuppressWarnings("deprecation") // bukkit unsafe
    @Override
    public Optional<IPlatform<?>> tryProvide(Plugin plugin, IListenerBridge bridge) {
        // check we're actually mojang-mapped
        if (IPlatformProvider.existsClass("org.bukkit.craftbukkit.CraftServer")
                && SUPPORTED_PROTOCOLS.contains(Bukkit.getUnsafe().getProtocolVersion())) {
            return Optional.of(Paper12111StaticProvider.provide(plugin, bridge));
        }
        return Optional.empty();
    }
}

final class Paper12111StaticProvider {

    private Paper12111StaticProvider() {
    }

    static IPlatform<?> provide(Plugin plugin, IListenerBridge bridge) {
        return new Paper12111Platform(plugin, bridge);
    }
}
