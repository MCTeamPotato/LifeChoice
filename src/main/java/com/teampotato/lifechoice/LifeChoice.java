package com.teampotato.lifechoice;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(LifeChoice.ID)
public class LifeChoice {
    public static final String ID = "lifechoice";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue CROPS_X_DETECTION;
    public static ForgeConfigSpec.IntValue CROPS_Y_DETECTION;
    public static ForgeConfigSpec.IntValue CROPS_Z_DETECTION;
    public static ForgeConfigSpec.BooleanValue DISABLE_BONE_MEAL;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.comment("Life Choice").push("general");
        CROPS_Y_DETECTION = CONFIG_BUILDER.comment("The bigger the value, the more crops you can plant in one place, the worse the performance.").defineInRange("Crops detection range Y", 1, 1, 10);
        CROPS_X_DETECTION = CONFIG_BUILDER.defineInRange("Crops detection range X", 2, 1, 10);
        CROPS_Z_DETECTION = CONFIG_BUILDER.defineInRange("Crops detection range Z", 2, 1, 10);
        DISABLE_BONE_MEAL = CONFIG_BUILDER.comment("Disable bone meal interactions with crops").define("Disable", true);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }

    public LifeChoice() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
