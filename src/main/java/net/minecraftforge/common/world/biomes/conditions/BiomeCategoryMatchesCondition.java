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
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.biomes.ForgeBiomeModifiers;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.base.IBiomeCondition;

import java.util.List;

public class BiomeCategoryMatchesCondition implements IBiomeCondition
{
    public static final MapCodec<BiomeCategoryMatchesCondition> CODEC =
            Codec.mapEither(
                    Biome.Category.field_235102_r_.listOf().fieldOf("categories"),
                    Biome.Category.field_235102_r_.fieldOf("category")
            ).xmap(e ->
            {
                if(e.left().isPresent())
                    return new BiomeCategoryMatchesCondition(e.left().get());
                else
                    return new BiomeCategoryMatchesCondition(e.right().get());
            }, cond -> cond.categories.size() == 1 ? Either.right(cond.categories.get(0)) : Either.left(cond.categories));

    private final List<Biome.Category> categories;

    public BiomeCategoryMatchesCondition(Biome.Category category)
    {
        this(ImmutableList.of(category));
    }

    public BiomeCategoryMatchesCondition(List<Biome.Category> categories)
    {
        this.categories = categories;
    }

    @Override
    public BiomeConditionType<BiomeCategoryMatchesCondition> getType()
    {
        return ForgeBiomeModifiers.MATCHES_CATEGORY.get();
    }

    @Override
    public boolean test(Biome biome)
    {
        return categories.contains(biome.getCategory());
    }
}
