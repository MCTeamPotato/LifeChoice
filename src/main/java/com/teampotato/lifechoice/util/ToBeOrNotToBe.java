package com.teampotato.lifechoice.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Objects;

import static com.teampotato.lifechoice.LifeChoice.*;

public class ToBeOrNotToBe {

    public static String regName(BlockState state) {
        return Objects.requireNonNull(state.getBlock().getRegistryName()).toString();
    }

    public static Integer numberChecker(BlockPos init, IWorld world) {
        int number = 0;
        for (int i = init.getY() + CROPS_Y_DETECTION.get(); i >= init.getY() - CROPS_Y_DETECTION.get(); i --) {
            for (int j = init.getX() + CROPS_X_DETECTION.get(); j >= init.getX() - CROPS_X_DETECTION.get(); j --) {
                for (int k = init.getZ() + CROPS_Z_DETECTION.get(); k >= init.getZ() - CROPS_Z_DETECTION.get(); k --) {
                    if (regName(world.getBlockState(new BlockPos(j, i, k))).equals(regName(world.getBlockState(init)))) number = number + 1;
                }
            }
        }
        return (number - 1);
    }

    public static BlockPos deathFinder(BlockPos init, IWorld world) {
        for (int i = init.getY() + CROPS_Y_DETECTION.get(); i >= init.getY() - CROPS_Y_DETECTION.get(); i --) {
            for (int j = init.getX() + CROPS_X_DETECTION.get(); j >= init.getX() - CROPS_X_DETECTION.get(); j --) {
                for (int k = init.getZ() + CROPS_Z_DETECTION.get(); k >= init.getZ() - CROPS_Z_DETECTION.get(); k --) {
                    if (regName(world.getBlockState(new BlockPos(j, i, k))).equals("minecraft:dead_bush")) return new BlockPos(j, i, k);
                }
            }
        }
        return new BlockPos(0, 0, 0);
    }

    public static int toBeOrNotToBe(BlockPos init, IWorld world) {
        int number = 0;
        for (int i = init.getY() + CROPS_Y_DETECTION.get(); i >= init.getY() - CROPS_Y_DETECTION.get(); i --) {
            for (int j = init.getX() + CROPS_X_DETECTION.get(); j >= init.getX() - CROPS_X_DETECTION.get(); j --) {
                for (int k = init.getZ() + CROPS_Z_DETECTION.get(); k >= init.getZ() - CROPS_Z_DETECTION.get(); k --) {
                    if (world.getBlockState(new BlockPos(j, i, k)).getBlock() instanceof CropsBlock) number = number + 1;
                }
            }
        }
        return number;
    }
}
