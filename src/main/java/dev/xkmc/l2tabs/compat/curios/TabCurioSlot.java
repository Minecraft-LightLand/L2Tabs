package dev.xkmc.l2tabs.compat.curios;

import dev.xkmc.l2tabs.compat.api.INamedSlot;
import dev.xkmc.l2tabs.compat.track.CurioSlotData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

class TabCurioSlot extends SlotItemHandler implements INamedSlot {

	private final String identifier;
	private final LivingEntity entity;
	private final SlotContext slotContext;

	private final IDynamicStackHandler handler;
	private final int index;

	public TabCurioSlot(LivingEntity entity, IDynamicStackHandler handler, int index, String identifier,
	                    int xPosition, int yPosition, NonNullList<Boolean> renders) {
		super(handler, index, xPosition, yPosition);
		this.identifier = identifier;
		this.entity = entity;
		this.slotContext = new SlotContext(identifier, entity, index, false, renders.get(index));

		ISlotType slotType = ISlotType.get(identifier);
		if (slotType != null) {
			this.setBackground(slotType.getIcon());
		}

		this.handler = handler;
		this.index = index;
	}

	@Override
	public CurioSlotData toSlotData() {
		return new CurioSlotData(identifier, index);
	}

	public boolean isValid() {
		return handler.getSlots() > index;
	}

	@Override
	public void set(ItemStack stack) {
		if (!isValid()) return;
		ItemStack current = this.getItem();
		boolean flag = current.isEmpty() && stack.isEmpty();
		((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
		this.setChanged();
		if (!flag && !ItemStack.matches(current, stack)) {
			CuriosApi.getCurio(stack)
					.ifPresent(curio -> curio.onEquipFromUse(this.slotContext));
		}
	}

	@Override
	public ItemStack getItem() {
		if (!isValid()) return ItemStack.EMPTY;
		return super.getItem();
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean mayPlace(ItemStack stack) {
		if (!isValid()) return false;
		return super.mayPlace(stack);
	}

	@Override
	public boolean mayPickup(Player playerIn) {
		if (!isValid()) return false;
		return super.mayPickup(playerIn);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		((IItemHandlerModifiable) getItemHandler()).setStackInSlot(index, getItem());
	}

	@Override
	public Component getName() {
		return Component.translatable("curios.identifier." + this.identifier);
	}

}
