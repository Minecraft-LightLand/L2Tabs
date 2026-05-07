package dev.xkmc.l2tabs.tabs.core;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public record DelegateTabScreen(AbstractContainerScreen<?> screen) implements ITabScreen {

	@Override
	public int getGuiLeft() {
		return screen.getLeftPos();
	}

	@Override
	public int getGuiTop() {
		return screen.getTopPos();
	}

	@Override
	public int screenWidth() {
		return screen.width;
	}

	@Override
	public int screenHeight() {
		return screen.height;
	}

	@Override
	public int getXSize() {
		return screen.getImageWidth();
	}

	@Override
	public int getYSize() {
		return screen.getImageHeight();
	}

	@Override
	public Screen asScreen() {
		return screen;
	}

}
