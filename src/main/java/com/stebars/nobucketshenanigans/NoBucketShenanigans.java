package com.stebars.nobucketshenanigans;

import java.util.List;

import org.jline.utils.Log;

import com.stebars.nobucketshenanigans.OptionsHolder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
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
    public void onRightClickBlock(RightClickBlock event) {
    	cancelRightClickIfItemAndFalling(event);
    }

    @SubscribeEvent
    public void onRightClickEntitySpecific(EntityInteractSpecific event) {
    	cancelRightClickIfItemAndFalling(event);
    }
    
    @SubscribeEvent
    public void onRightClickEntity(EntityInteract event) {
    	Entity target = event.getTarget();
    	if ((OptionsHolder.COMMON.preventGettingInBoat.get() && (target instanceof BoatEntity))
    			|| (OptionsHolder.COMMON.preventGettingInMinecart.get() && (target instanceof MinecartEntity))
    			|| (OptionsHolder.COMMON.preventGettingOnAnimal.get() && ((target instanceof PigEntity)
    					|| (target instanceof StriderEntity)
    					|| (target instanceof HorseEntity))))
    		cancelRightClickIfFalling(event);
    	else
    		cancelRightClickIfItemAndFalling(event);
    }
    
    @SubscribeEvent
    public void onRightClickItem(RightClickItem event) {
    	cancelRightClickIfItemAndFalling(event);
    }
    
    private void cancelRightClickIfItemAndFalling(PlayerInteractEvent event) { 	
    	Item item = event.getItemStack().getItem();
    	List<? extends String> itemsToBlock = OptionsHolder.COMMON.itemsCancelRightClick.get();
    	if (itemsToBlock.contains(item.getRegistryName().getNamespace())
    			|| itemsToBlock.contains(item.getRegistryName().toString()))
    		cancelRightClickIfFalling(event);
    }
    
    private void cancelRightClickIfFalling(PlayerInteractEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player.isInLava() || player.isInWater() || player.onClimbable())
			return;
		if (player.isPassenger() && player.getVehicle().isOnGround())
			return;
		if (!player.isOnGround() || player.isFallFlying()
						|| (player.fallDistance > 0))
			cancelRightClick(event);
    }
    
    private void cancelRightClick(PlayerInteractEvent event) {
		event.setCancellationResult(ActionResultType.FAIL);
		event.setCanceled(true);
    }
}
