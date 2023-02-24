package com.teampotato.lifechoice.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.teampotato.lifechoice.LifeChoice.DISABLE_BONE_MEAL;
import static com.teampotato.lifechoice.util.ToBeOrNotToBe.*;

@Mod.EventBusSubscriber
public class GrowEvent {
    @SubscribeEvent
    public static void onGrowUp(BlockEvent.CropGrowEvent.Pre event) {
        BlockState state = event.getState();
        int age = state.getValue(((CropsBlock)state.getBlock()).getAgeProperty());
        IWorld world = event.getWorld();

        if (!(state.getBlock() instanceof CropsBlock) || (age <= 3 || age == 7) || world.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState deadBush = Blocks.DEAD_BUSH.defaultBlockState();
        int flower = numberChecker(pos, world);

        if (flower > 3) {
            world.setBlock(pos, deadBush, 11);
        } else if (flower < 2) {
            world.setBlock(pos, deadBush, 11);
        }
    }

    @SubscribeEvent
    public static void onDeath(BlockEvent.CropGrowEvent.Pre event) {
        BlockState state = event.getState();
        Block block = state.getBlock();
        int age = state.getValue(((CropsBlock)state.getBlock()).getAgeProperty());
        IWorld world = event.getWorld();
        if (!(block instanceof CropsBlock) || (age <= 3 || age == 7) || world.isClientSide()) return;
        BlockPos death = deathFinder(event.getPos(), world);
        if (death.getY() != 0 && toBeOrNotToBe(death, world) >= 3) world.setBlock(death, block.defaultBlockState(), 11);
    }

    @SubscribeEvent
    public static void onBoneMeal(PlayerInteractEvent.RightClickBlock event) {
        if (!DISABLE_BONE_MEAL.get()) return;
        World world = event.getWorld();
        if (world.isClientSide()) return;
        BlockPos pos = event.getPos();
        if (
                regName(world.getBlockState(pos)).equals("minecraft:bone_meal") &&
                world.getBlockState(pos).getBlock() instanceof CropsBlock
        ) event.setCanceled(true);
    }
}
