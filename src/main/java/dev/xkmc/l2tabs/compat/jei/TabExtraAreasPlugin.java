package dev.xkmc.l2tabs.compat.jei;

import dev.xkmc.l2tabs.tabs.core.TabBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Reports the bounding boxes of all visible l2tabs tab widgets so JEI keeps its ingredient list
 * away from the tab strips that stick out of the container. Generic for every tab screen.
 */
@JeiPlugin
public class TabExtraAreasPlugin implements IModPlugin {

	private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("l2tabs", "tab_extra_areas");

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addGlobalGuiHandler(TabExtraAreas.INSTANCE);
	}

	private enum TabExtraAreas implements IGlobalGuiHandler {
		INSTANCE;

		@Override
		public Collection<Rect2i> getGuiExtraAreas() {
			Screen screen = Minecraft.getInstance().screen;
			if (screen == null) return List.of();
			List<Rect2i> ans = new ArrayList<>();
			for (var child : screen.children())
				if (child instanceof TabBase<?, ?> tab && tab.visible)
					ans.add(new Rect2i(tab.getX(), tab.getY(), tab.getWidth(), tab.getHeight()));
			return ans;
		}
	}

}
