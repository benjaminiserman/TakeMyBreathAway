package dev.biserman.takemybreathaway;

import java.util.List;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS = BUILDER
            .comment("A comma-separated list of dimension IDs to remove air from.")
            .defineListAllowEmpty("dimension", List.of("minecraft:the_nether", "minecraft:the_end"), () -> "", o -> true);

    public static final ModConfigSpec.ConfigValue<Boolean> ENABLED_FOR_MOBS = BUILDER
            .comment("Whether to remove air from mobs too.")
            .define("enabledForMobs", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
