//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.xkmc.l2tabs.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.l2tabs.init.OpenCurioHandler;
import dev.xkmc.l2tabs.init.OpenCuriosPacket;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.tabs.core.BaseTab;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.core.TabToken;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.Curios;

public class TabCurios extends BaseTab<TabCurios> {

	public static TabToken<TabCurios> tab;

	public TabCurios(TabToken<TabCurios> token, TabManager manager, ItemStack stack, Component title) {
		super(token, manager, stack, title);
	}

	public void onTabClicked() {
		L2Tabs.PACKET_HANDLER.toServer(new OpenCuriosPacket(OpenCurioHandler.CURIO_OPEN));
	}

	@Override
	public void renderBackground(PoseStack stack) {
		if (this.visible) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TEXTURE);
			token.type.draw(stack, manager.getScreen(), getX(), getY(), manager.selected == token, token.getIndex());
			RenderSystem.setShaderTexture(0, new ResourceLocation(Curios.MODID, "textures/gui/inventory.png"));
			blit(stack, getX() + 6, getY() + 10, 50, 14, 14, 14);
		}
	}

}
