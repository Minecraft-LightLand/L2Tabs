package dev.xkmc.l2tabs.tabs.core;

import net.minecraft.resources.Identifier;

public record TabSprites(
		int w, int h,
		Identifier first,
		Identifier mid,
		Identifier last,
		Identifier firstSel,
		Identifier midSel,
		Identifier lastSel
) {

	Identifier get(int index, boolean sel) {
		return switch (index) {
			case 0 -> sel ? firstSel : first;
			case 2 -> sel ? lastSel : last;
			default -> sel ? midSel : mid;
		};
	}

}
