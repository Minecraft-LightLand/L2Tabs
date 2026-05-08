package dev.xkmc.l2tabs.tabs.contents;

import com.mojang.blaze3d.platform.InputConstants;
import dev.xkmc.l2tabs.tabs.core.ITabScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public abstract class BaseTextScreen extends Screen implements ITabScreen {

	private final Identifier texture;

	public int imageWidth, imageHeight, leftPos, topPos;

	protected BaseTextScreen(Component title, Identifier texture) {
		super(title);
		this.texture = texture;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	public void init() {
		this.leftPos = (this.width - this.imageWidth) / 2;
		this.topPos = (this.height - this.imageHeight) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor g, int mouseX, int mouseY, float a) {
		super.extractBackground(g, mouseX, mouseY, a);
		int i = this.leftPos;
		int j = this.topPos;
		g.blit(RenderPipelines.GUI_TEXTURED, texture, i, j, 0, 0, imageWidth, imageHeight, 256, 256);
		//GuiHelper.blit(g, texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		InputConstants.Key mouseKey = InputConstants.getKey(event);
		if (Minecraft.getInstance().options.keyInventory.isActiveAndMatches(mouseKey)) {
			this.onClose();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public int getGuiLeft() {
		return leftPos;
	}

	@Override
	public int getGuiTop() {
		return topPos;
	}

	@Override
	public int getXSize() {
		return imageWidth;
	}

	@Override
	public int getYSize() {
		return imageHeight;
	}
}
