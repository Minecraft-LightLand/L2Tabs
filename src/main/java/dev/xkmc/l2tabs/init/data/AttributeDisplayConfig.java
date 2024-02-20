package dev.xkmc.l2tabs.init.data;

import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2library.serial.config.ConfigLoadOnStart;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.tabs.contents.AttributeEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SerialClass
@ConfigLoadOnStart
public class AttributeDisplayConfig extends BaseConfig {

	public static List<AttributeEntry> get() {
		return L2Tabs.ATTRIBUTE_ENTRY.getMerged().cache;
	}

	public static List<AttributeEntry> get(LivingEntity le) {
		var ans = get().stream().filter(e -> le.getAttribute(e.attr()) != null).toList();
		if (L2TabsConfig.COMMON.generateAllAttributes.get()) {
			ans = new ArrayList<>(ans);
			ans.sort(Comparator.comparingInt(AttributeEntry::order));
			int latest = ans.isEmpty() ? 0 : ans.get(ans.size() - 1).order();
			var set = ans.stream().map(AttributeEntry::attr).collect(Collectors.toSet());
			var all = new ArrayList<>(ForgeRegistries.ATTRIBUTES.getEntries());
			all.sort(Comparator.<Map.Entry<ResourceKey<Attribute>, Attribute>, String>
							comparing(e -> e.getKey().location().getNamespace())
					.thenComparing(e -> e.getKey().location().getNamespace()));
			for (var e : all) {
				if (set.contains(e.getValue())) continue;
				var ins = le.getAttribute(e.getValue());
				if (ins == null) continue;
				if (ins.getModifiers().isEmpty()) {
					if (L2TabsConfig.COMMON.generateAllAttributesHideUnchanged.get()) {
						continue;
					}
				}
				latest++;
				ans.add(new AttributeEntry(e.getValue(), false, latest, 0));
			}
		}
		return ans;
	}

	@ConfigCollect(CollectType.COLLECT)
	@SerialClass.SerialField
	protected final ArrayList<AttributeDataEntry> list = new ArrayList<>();

	private final ArrayList<AttributeEntry> cache = new ArrayList<>();

	@Override
	protected void postMerge() {
		for (var e : list) {
			if (!ForgeRegistries.ATTRIBUTES.containsKey(e.id()))
				continue;
			Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(e.id());
			if (attr == null)
				continue;
			cache.add(new AttributeEntry(attr.setSyncable(true), e.usePercent(), e.order(), e.intrinsic()));
		}
		cache.sort(Comparator.comparingInt(AttributeEntry::order));
	}

	public AttributeDisplayConfig add(Attribute attr, boolean usePercent, int order, double intrinsic) {
		list.add(new AttributeDataEntry(ForgeRegistries.ATTRIBUTES.getKey(attr), usePercent, order, intrinsic));
		return this;
	}

	public AttributeDisplayConfig add(Attribute attr, int order) {
		return add(attr, false, order, 0);
	}

	public record AttributeDataEntry(ResourceLocation id, boolean usePercent, int order, double intrinsic) {

	}

}
