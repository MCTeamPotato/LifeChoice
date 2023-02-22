package com.teampotato.lifechoice.events;

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
        if (state.getBlock() instanceof CropsBlock){
            int age = state.getValue(((CropsBlock)state.getBlock()).getAgeProperty());
            if (!(age <= 3) && age != 7) {
                IWorld world = event.getWorld();
                BlockPos pos = event.getPos();
                int flower = numberChecker(pos, world);

                BlockState deadBush = Blocks.DEAD_BUSH.defaultBlockState();

                BlockPos death = deathFinder(pos, world);
                if (death.getY() != 0 && toBeOrNotToBe(death, world) >= 3) world.setBlock(death, state.getBlock().defaultBlockState(), 11);

                if (flower > 3) {
                    world.setBlock(pos, deadBush, 11);
                } else if (flower < 2) {
                    world.setBlock(pos, deadBush, 11);
                }

            }
        }
    }

    @SubscribeEvent
    public static void onBoneMeal(PlayerInteractEvent.RightClickBlock event) {
        if (!DISABLE_BONE_MEAL.get()) return;
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (
                regName(world.getBlockState(pos)).equals("minecraft:bone_meal") &&
                world.getBlockState(pos).getBlock() instanceof CropsBlock
        ) {
            event.setCanceled(true);
        }
    }
}
