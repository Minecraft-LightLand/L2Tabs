package dev.xkmc.l2tabs.compat.track;

import dev.xkmc.l2menustacker.screen.base.LayerPopType;
import dev.xkmc.l2menustacker.screen.track.NoData;
import dev.xkmc.l2menustacker.screen.track.TrackedEntryType;
import dev.xkmc.l2tabs.compat.api.AccessoriesMultiplex;
import dev.xkmc.l2tabs.compat.api.TabCuriosCompat;
import io.wispforest.accessories.Accessories;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class CurioTabTrace extends TrackedEntryType<CurioTraceData> {

	@Override
	public LayerPopType restoreMenuNotifyClient(ServerPlayer player, CurioTraceData data, @Nullable Component comp) {
		AccessoriesMultiplex.openScreen(player, data);
		return LayerPopType.CLEAR;
	}

}
