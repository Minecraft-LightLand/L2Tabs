//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.xkmc.l2tabs.compat.common;

import dev.xkmc.l2menustacker.init.MouseCache;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.init.data.OpenCurioHandler;
import dev.xkmc.l2tabs.init.data.OpenCuriosPacket;
import dev.xkmc.l2tabs.tabs.core.TabBase;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TabCurios extends TabBase<InvTabData, TabCurios> {

	private static final Identifier ICON = L2Tabs.loc("curios");

	public TabCurios(int index, TabToken<InvTabData, TabCurios> token,
	                 TabManager<InvTabData> manager, Component title) {
		super(index, token, manager, title);
	}

	public void onTabClicked() {
		MouseCache.cacheMousePos();
		L2Tabs.PACKET_HANDLER.toServer(new OpenCuriosPacket(OpenCurioHandler.CURIO_OPEN));
	}

	@Override
	public void renderBackground(GuiGraphicsExtractor g) {
		if (getX() == 0 && getY() == 0) return;
		if (this.visible) {
			token.draw(g, getX(), getY(), manager.selected == token, index);
			g.blitSprite(RenderPipelines.GUI_TEXTURED, ICON, getX() + 5, getY() + 7, 14, 14);
		}
	}

}
