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

package net.minecraftforge.common.world.biomes.conditions;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.biomes.ForgeBiomeModifiers;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.base.IBiomeCondition;

import java.util.List;

public class BiomeMatchesCondition implements IBiomeCondition
{
    public static final MapCodec<BiomeMatchesCondition> CODEC =
            Codec.mapEither(
                    ResourceLocation.field_240908_a_.listOf().fieldOf("biomes"),
                    ResourceLocation.field_240908_a_.fieldOf("biome")
            ).xmap(e ->
            {
                if(e.left().isPresent())
                    return new BiomeMatchesCondition(e.left().get());
                else
                    return new BiomeMatchesCondition(e.right().get());
            }, mb -> mb.locations.size() == 1 ? Either.right(mb.locations.get(0)) : Either.left(mb.locations));

    private final List<ResourceLocation> locations;

    public BiomeMatchesCondition(ResourceLocation location)
    {
        this(ImmutableList.of(location));
    }

    public BiomeMatchesCondition(List<ResourceLocation> locations)
    {
        this.locations = locations;
    }

    @Override
    public BiomeConditionType<BiomeMatchesCondition> getType()
    {
        return ForgeBiomeModifiers.MATCHES_BIOME.get();
    }

    @Override
    public boolean test(Biome biome)
    {
        //TODO test, this might not actually work... -> test ForgeRegistries.BIOMES.getKey(biome)
        return locations.contains(biome.getRegistryName());
    }
}
