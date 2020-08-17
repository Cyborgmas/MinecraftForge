/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common.data.worldgen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.common.data.CodecBackedProvider;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Build class is for the sole possible vanilla implementation {@link ISurfaceBuilderConfig}
 */
public abstract class ConfiguredSurfaceBuildersProvider extends CodecBackedProvider<ConfiguredSurfaceBuilder<?>> {
    private final DataGenerator generator;
    private final String modid;
    protected final Map<ResourceLocation, ConfiguredSurfaceBuilder<?>> map = new HashMap<>();
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ConfiguredSurfaceBuildersProvider(DataGenerator generator, ExistingFileHelper fileHelper, String modid) {
        super(ConfiguredSurfaceBuilder.field_237168_a_, fileHelper); //TODO This codec is dispatched for the vanilla SURFACE_BUILDER registry, and won't affect any mod added SURFACE_BUILDERs.
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void start();

    @Override
    public void act(DirectoryCache cache) {
        start();

        Path path = generator.getOutputFolder();

        map.forEach(LamdbaExceptionUtils.rethrowBiConsumer((name, inst) ->
                this.save(inst, cache, path.resolve("data/" + name.getNamespace() + "/worldgen/configured_surface_builder/" + name.getPath() + ".json"))
        ));

        this.fileHelper.reloadResources();
    }

    public void put(ResourceLocation location, ConfiguredSurfaceBuilder<?> inst) {
        map.put(location, inst);
    }

    @Override
    public String getName() {
        return "Configured Surface Builders: " + modid;
    }

    /**
     * Only one implementation of {@link ISurfaceBuilderConfig} exists, this builder is designed for that.
     */
    public static class Builder {
        private BlockState topMaterial = Blocks.GRASS_BLOCK.getDefaultState();
        private BlockState underMaterial = Blocks.DIRT.getDefaultState();
        private BlockState underWaterMaterial = Blocks.GRAVEL.getDefaultState();
        private SurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder = SurfaceBuilder.DEFAULT;

        protected ConfiguredSurfaceBuilder<SurfaceBuilderConfig> build() {
            return surfaceBuilder.func_242929_a(new SurfaceBuilderConfig(topMaterial, underMaterial, underWaterMaterial));
        }

        public Builder setSurfaceBuilder(SurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder) {
            this.surfaceBuilder = surfaceBuilder;
            return this;
        }

        public Builder setTopMaterial(BlockState state) {
            this.topMaterial = state;
            return this;
        }

        public Builder setUnderMaterial(BlockState state) {
            this.underMaterial = state;
            return this;
        }

        public Builder setUnderWaterMaterial(BlockState state) {
            this.underWaterMaterial = state;
            return this;
        }
    }
}
