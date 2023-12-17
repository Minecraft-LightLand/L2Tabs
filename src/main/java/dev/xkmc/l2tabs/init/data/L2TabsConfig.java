package dev.xkmc.l2tabs.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class L2TabsConfig {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue showTabs;
		public final ForgeConfigSpec.BooleanValue showTabsOnlyCurio;
		public final ForgeConfigSpec.IntValue attributeLinePerPage;

		Client(ForgeConfigSpec.Builder builder) {
			showTabs = builder.comment("Show inventory tabs")
					.define("showTabs", true);
			showTabsOnlyCurio = builder.comment("Show inventory tabs only in curio page. Only works when showTabs is true and curio is installed.")
					.define("showTabsOnlyCurio", false);
			attributeLinePerPage = builder.comment("Number of attribure lines per page")
					.defineInRange("attributeLinePerPage", 15, 1, 100);
		}

	}

	public static class Common {

		Common(ForgeConfigSpec.Builder builder) {

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
