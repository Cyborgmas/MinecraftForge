package net.minecraftforge.common.data.worldgen;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.world.biomes.BiomeModifierManager;
import net.minecraftforge.common.world.biomes.modifiers.base.BiomeModifier;
import net.minecraftforge.common.world.biomes.modifiers.base.BiomeModifierType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BiomeModificationProvider extends CodecBackedProvider<BiomeModifier>
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final String modid;
    private Map<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();
    private final DataGenerator generator;
    private boolean replace = false;

    protected BiomeModificationProvider(DataGenerator generator, RegistryOpsHelper regOps, String modid)
    {
        super(BiomeModifier.GENERAL_CODEC, regOps);
        this.modid = modid;
        this.generator = generator;
    }

    protected BiomeModificationProvider withReplace()
    {
        this.replace = true;
        return this;
    }

    protected abstract void start();

    @Override
    public void act(DirectoryCache cache) throws IOException
    {
        start();

        modifiers.forEach(LamdbaExceptionUtils.rethrowBiConsumer((name, inst)->
                this.save(inst, cache , generator.getOutputFolder().resolve("data/" + name.getNamespace() +"/biome_modifiers/"+name.getPath()+".json"))
        ));

        Path modifierListPath = generator.getOutputFolder().resolve("data/forge/biome_modifiers/global_biome_modifiers.json");

        JsonElement element = BiomeModifierManager.BIOME_MODIFIER_LIST
                .encodeStart(JsonOps.INSTANCE, Pair.of(replace, new ArrayList<>(modifiers.keySet())))
                .getOrThrow(false, LOGGER::error);
        IDataProvider.save(this.gson, cache, element, modifierListPath);
    }

    protected void put(ResourceLocation name, BiomeModifier modifier)
    {
        this.modifiers.put(name, modifier);
    }

    @Override
    public String getName()
    {
        return "Biome Modifications: " + modid;
    }
}
