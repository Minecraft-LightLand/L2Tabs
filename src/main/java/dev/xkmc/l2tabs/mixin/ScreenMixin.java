package dev.xkmc.l2tabs.mixin;

import dev.xkmc.l2tabs.tabs.contents.TabInventory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

	@Inject(method = "extractBackground", at = @At(value = "TAIL"))
	public void l2tabs$render(GuiGraphicsExtractor g, int mouseX, int mouseY, float a, CallbackInfo ci) {
		TabInventory.renderTabs(g, (Screen) (Object) this);
	}

}
