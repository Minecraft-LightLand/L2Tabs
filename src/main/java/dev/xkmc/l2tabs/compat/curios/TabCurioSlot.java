package dev.xkmc.l2tabs.compat.curios;

import dev.xkmc.l2tabs.compat.api.INamedSlot;
import dev.xkmc.l2tabs.compat.track.CurioSlotData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.ClientTooltipFlag;
import net.neoforged.neoforge.items.SlotItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.extensions.ICurioSlotExtension;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.mixin.core.AccessorEntity;

import java.util.ArrayList;
import java.util.List;

class TabCurioSlot extends SlotItemHandler implements INamedSlot {

	private final String identifier;
	private final LivingEntity player;
	private final ICurioSlotExtension extension;
	private final int index;

	public TabCurioSlot(
			LivingEntity player,
			IDynamicStackHandler handler,
			int index,
			String identifier,
			int xPosition,
			int yPosition) {
		super(handler, index, xPosition, yPosition);
		this.identifier = identifier;
		this.player = player;
		ISlotType slotType = ISlotType.get(identifier);

		if (slotType != null) {
			this.setBackground(slotType.getIcon());
		}
		this.extension = ICurioSlotExtension.from(identifier);
		this.index = index;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public ICurioSlotExtension getSlotExtension() {
		return this.extension;
	}

	public SlotContext getSlotContext() {
		return new SlotContext(identifier, player, index, false,
				true);
	}

	public List<Component> getSlotTooltip() {
		List<Component> tooltip = new ArrayList<>();
		List<Component> oldTooltipCall = this.extension.getSlotTooltip(
				this.getSlotContext(), new ArrayList<>(),
				ClientTooltipFlag.of(Minecraft.getInstance().options.advancedItemTooltips
						? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL));

		if (!oldTooltipCall.isEmpty()) {
			return oldTooltipCall;
		}
		tooltip.add(
				Component.translatableWithFallback("curios.identifier." + this.identifier,
						this.identifier.substring(0, 1).toUpperCase()
								+ this.identifier.substring(1).toLowerCase()));
		tooltip = this.extension.getSlotTooltip(
				this.getSlotContext(),
				tooltip,
				ClientTooltipFlag.of(Minecraft.getInstance().options.advancedItemTooltips
						? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL));
		return tooltip;
	}

	@Override
	public void set(ItemStack stack) {
		ItemStack current = this.getItem();
		boolean flag = current.isEmpty() && stack.isEmpty();
		super.set(stack);

		if (!flag
				&& !ItemStack.matches(current, stack)
				&& !((AccessorEntity) this.player).getFirstTick()) {
			CuriosApi.getCurio(stack).ifPresent(curio -> curio.onEquipFromUse(this.getSlotContext()));

		}
		while (getItemHandler().getSlots() != getItemHandler().getSlots()) ;
	}

	@Override
	public Component getName() {
		return Component.translatable("curios.identifier." + this.identifier);
	}

	@Override
	public CurioSlotData toSlotData() {
		return new CurioSlotData(identifier, index);
	}

	@Override
	public boolean allowModification(Player pPlayer) {
		return true;
	}
}
