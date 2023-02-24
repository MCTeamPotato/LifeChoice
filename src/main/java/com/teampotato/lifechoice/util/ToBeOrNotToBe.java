package com.teampotato.lifechoice.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.teampotato.lifechoice.LifeChoice.*;

public class ToBeOrNotToBe {

    public static String regName(BlockState state) {
        return Objects.requireNonNull(state.getBlock().getRegistryName()).toString();
    }

    public static List<BlockPos> getDetectionPositions(BlockPos init) {
        List<BlockPos> positions = new ArrayList<>();
        //Is there a way to optimize? help wanted qwq
        for (int i = init.getY() + CROPS_Y_DETECTION.get(); i >= init.getY() - CROPS_Y_DETECTION.get(); i--) {
            for (int j = init.getX() + CROPS_X_DETECTION.get(); j >= init.getX() - CROPS_X_DETECTION.get(); j--) {
                for (int k = init.getZ() + CROPS_Z_DETECTION.get(); k >= init.getZ() - CROPS_Z_DETECTION.get(); k--) {
                    positions.add(new BlockPos(j, i, k));
                }
            }
        }
        return positions;
    }

    public static Integer numberChecker(BlockPos init, IWorld world) {
        String initRegName = regName(world.getBlockState(init));
        int number = 0;
        for (BlockPos pos : getDetectionPositions(init)) {
            if (regName(world.getBlockState(pos)).equals(initRegName)) number += 1;
        }
        return (number - 1);
    }

    public static BlockPos deathFinder(BlockPos init, IWorld world) {
        for (BlockPos pos : getDetectionPositions(init)) {
            if (regName(world.getBlockState(pos)).equals("minecraft:dead_bush")) return pos;
        }
        return new BlockPos(0, 0, 0);
    }

    public static int toBeOrNotToBe(BlockPos init, IWorld world) {
        int number = 0;
        for (BlockPos pos : getDetectionPositions(init)) {
            if (world.getBlockState(pos).getBlock() instanceof CropsBlock) number += 1;
        }
        return number;
    }
}