package com.stebars.nobucketshenanigans;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class OptionsHolder {
	public static class Common {
		
		public final ConfigValue<List<? extends String>> itemsCancelRightClick;
		public final ConfigValue<Boolean> preventGettingInBoat;
		public final ConfigValue<Boolean> preventGettingInMinecart;
		public final ConfigValue<Boolean> preventGettingOnAnimal;
		
		public Common(ForgeConfigSpec.Builder builder) {			
	        itemsCancelRightClick = builder.comment("List of all the items you want to block right-clicking with while falling. "
	        		+ "To block bucket saving in vanilla, you only need water and lava buckets (not e.g. milk buckets because "
	        		+ "they can't be placed). If you have a mod adding milk, honey, slime, etc. then add their items here. "
	        		+ "This includes e.g. teapots, cups, bowls that can place fluids. You can also put non-fluid-placing items "
	        		+ "in here, e.g. flint and steel, though I only tested this for flint and steel.")
	        		.define("itemsBlockRightClick", Lists.newArrayList(
	        				"minecraft:water_bucket",
	        				"minecraft:lava_bucket",
	        				"minecraft:hay_block",
	        				"minecraft:cobwebs"));
	        preventGettingInBoat = builder.comment("Prevent players from getting in boats while they're falling. NOTE that "
	        		+ "boats will still cancel fall damage, so I recommend installing Random Tweaks "
	        		+ "(https://www.curseforge.com/minecraft/mc-mods/randompatches-forge) to re-enable boat fall damage.")
	        		.define("preventGettingInBoat", true);
	        preventGettingInMinecart = builder.comment("Prevent players from getting in minecarts while falling. "
	        		+ "Minecarts cancel all fall damage if they land on rails, otherwise they pass it to the rider.")
	        		.define("preventGettingInMinecart", true);
	        preventGettingOnAnimal = builder.comment("Prevent players from getting on horses, striders, and pigs while "
	        		+ "falling. These animals don't cancel fall damage.")
	        		.define("preventGettingOnAnimal", true);
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