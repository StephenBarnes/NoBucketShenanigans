package com.stebars.nobucketshenanigans;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class OptionsHolder {
	public static class Common {
		
		public final ConfigValue<List<? extends String>> itemsBlockRightClick;

		public Common(ForgeConfigSpec.Builder builder) {			
	        itemsBlockRightClick = builder.comment("List of all the items you want to block right-clicking with while falling. "
	        		+ "To block bucket saving in vanilla, you only need water and lava buckets (not e.g. milk buckets because "
	        		+ "they can't be placed). If you have a mod adding milk, honey, slime, etc. then add their items here. "
	        		+ "This includes e.g. teapots, cups, bowls that can place fluids. You can also put non-fluid-placing items "
	        		+ "in here, e.g. flint and steel, though I only tested this for flint and steel.")
	        		.define("itemsBlockRightClick", Lists.newArrayList(
	        				"minecraft:water_bucket",
	        				"minecraft:lava_bucket",
	        				"minecraft:hay_block"));
		}
	}

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static { //constructor
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}