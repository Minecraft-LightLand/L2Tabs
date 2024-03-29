package dev.xkmc.l2tabs.compat;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.mixin.core.AccessorEntity;

import javax.annotation.Nonnull;

public class TabCurioSlot extends CurioSlot {

	private final String identifier;
	private final LivingEntity player;
	private final SlotContext slotContext;

	private final NonNullList<Boolean> renderStatuses;
	private final boolean canToggleRender;

	private final IDynamicStackHandler handler;
	private final int index;

	public TabCurioSlot(LivingEntity player, IDynamicStackHandler handler, int index, String identifier,
						int xPosition, int yPosition, NonNullList<Boolean> renders,
						boolean canToggleRender) {
		super(null, handler, index, identifier, xPosition, yPosition, renders, canToggleRender);
		this.identifier = identifier;
		this.renderStatuses = renders;
		this.player = player;
		this.canToggleRender = canToggleRender;
		this.slotContext = new SlotContext(identifier, player, index, false, renders.get(index));
		this.setBackground(InventoryMenu.BLOCK_ATLAS,
				player.getCommandSenderWorld().isClientSide() ?
						CuriosApi.getSlotIcon(identifier)
						: new ResourceLocation(Curios.MODID, "slot/empty_curio_slot"));
		this.handler = handler;
		this.index = index;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean canToggleRender() {
		return this.canToggleRender;
	}

	public boolean getRenderStatus() {

		if (!this.canToggleRender) {
			return true;
		}
		return this.renderStatuses.size() > this.getSlotIndex() &&
				this.renderStatuses.get(this.getSlotIndex());
	}

	@OnlyIn(Dist.CLIENT)
	public String getSlotName() {
		return I18n.get("curios.identifier." + this.identifier);
	}

	public boolean isValid() {
		return handler.getSlots() > index;
	}

	@Override
	public void set(@Nonnull ItemStack stack) {
		if (!isValid()) return;
		ItemStack current = this.getItem();
		boolean flag = current.isEmpty() && stack.isEmpty();
		((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
		this.setChanged();

		if (!flag && !ItemStack.matches(current, stack) &&
				!((AccessorEntity) this.player).getFirstTick()) {
			CuriosApi.getCurio(stack)
					.ifPresent(curio -> curio.onEquipFromUse(this.slotContext));
		}
	}

	@Override
	public @NotNull ItemStack getItem() {
		if (!isValid()) return ItemStack.EMPTY;
		return super.getItem();
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean mayPlace(@Nonnull ItemStack stack) {
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
		((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, getItem());
	}

}
