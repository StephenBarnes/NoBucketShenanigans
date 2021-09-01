package com.stebars.nobucketshenanigans;

import java.util.List;

import com.stebars.nobucketshenanigans.OptionsHolder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@Mod("nobucketshenanigans")
public class NoBucketShenanigans
{	
    public NoBucketShenanigans() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC); 
    }
    
    @SubscribeEvent
    public void stopRightClickBucket(RightClickBlock event) {
    	Item item = event.getItemStack().getItem();
    	List<? extends String> itemsToBlock = OptionsHolder.COMMON.itemsBlockRightClick.get();
    	if (itemsToBlock.contains(item.getRegistryName().getNamespace())
    			|| itemsToBlock.contains(item.getRegistryName().toString())) {
    		PlayerEntity player = event.getPlayer();
    		if (player.isInLava() || player.isInWater() || player.onClimbable())
    			return;
    		if (player.isPassenger() && player.getVehicle().isOnGround())
    			return;
    		if (!player.isOnGround() || player.isFallFlying()
    						|| (player.fallDistance > 0)) {
        		event.setCancellationResult(ActionResultType.FAIL);
        		event.setCanceled(true);
    		}
    	}
    }
}
