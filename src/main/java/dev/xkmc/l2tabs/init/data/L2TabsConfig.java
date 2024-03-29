package dev.xkmc.l2tabs.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class L2TabsConfig {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue showTabs;
		public final ForgeConfigSpec.BooleanValue showTabsOnlyCurio;
		public final ForgeConfigSpec.BooleanValue redirectInventoryTabToCuriosInventory;
		public final ForgeConfigSpec.IntValue attributeLinePerPage;

		public final ForgeConfigSpec.ConfigValue<List<String>> hiddenTabs;

		Client(ForgeConfigSpec.Builder builder) {
			showTabs = builder.comment("Show inventory tabs")
					.define("showTabs", true);
			showTabsOnlyCurio = builder.comment("Show inventory tabs only in curio page. Only works when showTabs is true and curio is installed.")
					.define("showTabsOnlyCurio", false);
			redirectInventoryTabToCuriosInventory = builder.comment("Redirect Inventory Tab to Curios Inventory")
					.define("redirectInventoryTabToCuriosInventory", true);
			attributeLinePerPage = builder.comment("Number of attribure lines per page")
					.defineInRange("attributeLinePerPage", 15, 1, 100);

			hiddenTabs = builder.comment("List of tabs to hide. Use title translation key for tab id.")
					.comment("Example: menu.tabs.attribute for attribute tab")
					.comment("Example: menu.tabs.curios for curios tab")
					.comment("Example: pandora.menu.title for pandora tab")
					.define("hiddentTabs", new ArrayList<>(List.of()));
		}

	}

	public static class Common {

		public final ForgeConfigSpec.BooleanValue syncPlayerAttributeName;
		public final ForgeConfigSpec.BooleanValue syncAllEntityAttributeName;

		Common(ForgeConfigSpec.Builder builder) {
			syncPlayerAttributeName = builder.comment("Sync player attribute names to client")
					.define("syncPlayerAttributeName", true);
			syncAllEntityAttributeName = builder.comment("Sync all entity attribute name to client")
					.define("syncAllEntityAttributeName", false);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}


}
