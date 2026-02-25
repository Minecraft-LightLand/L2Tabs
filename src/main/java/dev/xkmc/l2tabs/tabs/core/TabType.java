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
		int dx = x;
		int dy = y;
		switch (this) {
			case ABOVE -> {
				dx += 4;
				dy += 6;
			}
			case BELOW -> {
				dx += 4;
				dy += 4;
			}
			case LEFT -> {
				dx += 8;
				dy += 3;
			}
			case RIGHT -> {
				dx += 6;
				dy += 4;
			}
		}
		g.renderFakeItem(stack, dx, dy);
		g.renderItemDecorations(Minecraft.getInstance().font, stack, dx, dy);
	}

	public int getX(int w, int h, int index, int split) {
		int space = split < 0 ? 0 : (w + 1) % (width + 1);
		return switch (this) {
			case ABOVE, BELOW -> (width + 1) * index + (index >= split ? space : 0);
			case LEFT -> -width + 4;
			case RIGHT -> w - 4;
		};
	}

	public int getY(int w, int h, int index, int split) {
		int space = split < 0 ? 0 : (h + 1) % (height + 1);
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
		return getX(w, 0, index, -1);
	}

	@Deprecated
	public int getTabY(int h, int index) {
		return getY(0, h, index, -1);
	}

}
