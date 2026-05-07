package dev.xkmc.l2tabs.compat.common;

import dev.xkmc.l2core.base.menu.base.BaseContainerScreen;
import dev.xkmc.l2core.util.GuiHelper;
import dev.xkmc.l2tabs.compat.api.INamedSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class BaseCuriosListScreen<T extends BaseCuriosListMenu<T>> extends BaseContainerScreen<T> {

	public BaseCuriosListScreen(T cont, Inventory plInv, Component title) {
		super(cont, plInv, title);
	}

	@Override
	protected void init() {
		super.init();
		if (topPos < 28) topPos = 28;
		int w = 10;
		int h = 11;
		int x = getLeftPos() + titleLabelX + font.width(getTitle()) + 14,
				y = getTopPos() + 4;
		if (menu.curios.page > 0) {
			addRenderableWidget(Button.builder(Component.literal("<"), e -> click(1))
					.pos(x - w - 1, y).size(w, h).build());
		}
		if (menu.curios.page < menu.curios.total - 1) {
			addRenderableWidget(Button.builder(Component.literal(">"), e -> click(2))
					.pos(x, y).size(w, h).build());
		}
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor g, int mx, int my, float pTick) {
		super.extractBackground(g, mx, my, pTick);
		var sr = getRenderer();
		sr.start(g);
		for (int i = 0; i < menu.curios.getRows() * 9; i++) {
			if (menu.curios.getSlotAtPosition(i) != null)
				sr.draw(g, "grid", "slot", i % 9 * 18 - 1, i / 9 * 18 - 1);
		}
	}

	@Override
	protected void extractTooltip(GuiGraphicsExtractor g, int mx, int my) {
		LocalPlayer clientPlayer = Minecraft.getInstance().player;
		if (clientPlayer != null && clientPlayer.inventoryMenu
				.getCarried().isEmpty() && this.getHoveredSlot() != null) {
			Slot slot = this.getHoveredSlot();
			if (slot instanceof INamedSlot slotCurio && !slot.hasItem()) {
				GuiHelper.tooltip(g, List.of(slotCurio.getName()), mx, my);
			}
		}
		super.extractTooltip(g, mx, my);
	}

}
