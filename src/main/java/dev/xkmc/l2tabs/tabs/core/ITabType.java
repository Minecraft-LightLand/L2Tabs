package dev.xkmc.l2tabs.tabs.core;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public interface ITabType<G extends TabGroupData<G>> {

	Component getTitle();

	void reverseMap(TabToken<G, ?> ans);

	@Nullable
	TabToken<G, ?> getMapped();

}
