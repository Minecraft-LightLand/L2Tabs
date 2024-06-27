package dev.xkmc.l2tabs.compat;

import javax.annotation.Nonnull;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.SlotItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.mixin.core.AccessorEntity;

public class CurioSlot extends SlotItemHandler {
	private final String identifier;
	private final Player player;
	private final SlotContext slotContext;
	private NonNullList<Boolean> renderStatuses;
	private boolean canToggleRender;
	private boolean showCosmeticToggle;
	private boolean isCosmetic;

	public CurioSlot(Player player, IDynamicStackHandler handler, int index, String identifier, int xPosition, int yPosition, NonNullList<Boolean> renders, boolean canToggleRender, boolean showCosmeticToggle, boolean isCosmetic) {
		this(player, handler, index, identifier, xPosition, yPosition, renders, canToggleRender);
		this.showCosmeticToggle = showCosmeticToggle;
		this.isCosmetic = isCosmetic;
	}

	public CurioSlot(Player player, IDynamicStackHandler handler, int index, String identifier, int xPosition, int yPosition, NonNullList<Boolean> renders, boolean canToggleRender) {
		super(handler, index, xPosition, yPosition);
		this.identifier = identifier;
		this.renderStatuses = renders;
		this.player = player;
		this.canToggleRender = canToggleRender;
		this.slotContext = new SlotContext(identifier, player, index, false, renders.get(index));
		CuriosApi.getSlot(identifier, player.level()).ifPresent((slotType) -> {
			this.setBackground(InventoryMenu.BLOCK_ATLAS, slotType.getIcon());
		});
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean canToggleRender() {
		return this.canToggleRender;
	}

	public boolean isCosmetic() {
		return this.isCosmetic;
	}

	public boolean showCosmeticToggle() {
		return this.showCosmeticToggle;
	}

	public boolean getRenderStatus() {
		if (!this.canToggleRender) {
			return true;
		} else {
			return this.renderStatuses.size() > this.getSlotIndex() && (Boolean)this.renderStatuses.get(this.getSlotIndex());
		}
	}

	@OnlyIn(Dist.CLIENT)
	public String getSlotName() {
		if (this.isCosmetic) {
			String var10000 = I18n.get("curios.cosmetic", new Object[0]);
			return var10000 + " " + I18n.get("curios.identifier." + this.identifier, new Object[0]);
		} else {
			return I18n.get("curios.identifier." + this.identifier, new Object[0]);
		}
	}

	public void set(@Nonnull ItemStack stack) {
		ItemStack current = this.getItem();
		boolean flag = current.isEmpty() && stack.isEmpty();
		super.set(stack);
		if (!flag && !ItemStack.matches(current, stack) && !((AccessorEntity)this.player).getFirstTick()) {
			CuriosApi.getCurio(stack).ifPresent((curio) -> {
				curio.onEquipFromUse(this.slotContext);
			});
		}

	}

	public boolean allowModification(@Nonnull Player pPlayer) {
		return true;
	}
}
