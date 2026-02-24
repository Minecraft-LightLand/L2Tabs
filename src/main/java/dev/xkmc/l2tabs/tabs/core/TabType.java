package dev.xkmc.l2tabs.tabs.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public enum TabType {
	ABOVE(DefaultTabs.UP),
	BELOW(DefaultTabs.BOTTOM),
	LEFT(DefaultTabs.LEFT),
	RIGHT(DefaultTabs.RIGHT);

	public final int width;
	public final int height;
	public final TabSprites sprite;

	TabType(TabSprites sprite) {
		this.width = sprite.w();
		this.height = sprite.h();
		this.sprite = sprite;
	}

	public void draw(TabSprites tex, GuiGraphics g, int x, int y, boolean selected, int index) {
		g.blitSprite(tex.get(index, selected), x, y, tex.w(), tex.h());
	}

	public void drawIcon(GuiGraphics g, int x, int y, ItemStack stack) {
		int i = x;
		int j = y;
		switch (this) {
			case ABOVE -> {
				i += 6;
				j += 9;
			}
			case BELOW -> {
				i += 6;
				j += 6;
			}
			case LEFT -> {
				i += 10;
				j += 5;
			}
			case RIGHT -> {
				i += 6;
				j += 5;
			}
		}
		g.renderFakeItem(stack, i, j);
		g.renderItemDecorations(Minecraft.getInstance().font, stack, i, j);
	}

	public int getX(int w, int h, int index, int split) {
		int space = split < 0 ? 0 : w % width;
		return switch (this) {
			case ABOVE, BELOW -> width * index + (index >= split ? space : 0);
			case LEFT -> -width + 4;
			case RIGHT -> w - 4;
		};
	}

	public int getY(int w, int h, int index, int split) {
		int space = split < 0 ? 0 : h % height;
		return switch (this) {
			case ABOVE -> -height + 4;
			case BELOW -> h - 4;
			case LEFT, RIGHT -> (height + 1) * index + (index >= split ? space : 0);
		};
	}

	public boolean isMouseOver(int gx, int gy, int w, int h, int ind, int split, double mx, double my) {
		int x = gx + getX(w, h, ind, split);
		int y = gy + getY(w, h, ind, split);
		return mx > x && mx < x + width && my > y && my < y + height;
	}

	public int getTabX(int w, int h, int index, int split) {
		return (this == RIGHT ? w : 0) + getX(w, h, index, split);
	}

	public int getTabY(int w, int h, int index, int split) {
		return (this == BELOW ? h : 0) + getY(w, h, index, split);
	}

	@Deprecated
	public int getX(int index) {
		return getX(0, 0, index, -1);
	}

	@Deprecated
	public int getY(int index) {
		return getY(0, 0, index, -1);
	}

	@Deprecated
	public boolean isMouseOver(int gx, int gy, int ind, double mx, double my) {
		int x = gx + getX(0, 0, ind, -1);
		int y = gy + getY(0, 0, ind, -1);
		return mx > x && mx < x + width && my > y && my < y + height;
	}

	@Deprecated
	public int getTabX(int w, int index) {
		return getTabX(w, 0, index, -1);
	}

	@Deprecated
	public int getTabY(int h, int index) {
		return getTabY(0, h, index, -1);
	}

}
