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

package net.minecraftforge.common.world.biomes.conditions.base;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.biomes.conditions.BiomeAndCondition;
import net.minecraftforge.common.world.biomes.conditions.BiomeInvertedCondition;
import net.minecraftforge.common.world.biomes.conditions.BiomeOrCondition;
import net.minecraftforge.common.world.biomes.modifiers.SimpleFeaturesAdditions;
import net.minecraftforge.common.world.biomes.modifiers.base.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public interface IBiomeCondition extends Predicate<Biome>
{
    Codec<BiomeConditionType<?>> FROM_REGISTRY = ResourceLocation.field_240908_a_.xmap(ForgeRegistries.BIOME_CONDITION_TYPES::getValue, ForgeRegistries.BIOME_CONDITION_TYPES::getKey);

    /**
     * Use this to create a {@link BiomeModifier}'s Codec. This is to be able to parse it alone, so it must be present
     * in the parent JsonObject.
     *
     * See {@link SimpleFeaturesAdditions#CODEC}
     */
    MapCodec<IBiomeCondition> FIELD_CODEC = FROM_REGISTRY.<IBiomeCondition>dispatch(IBiomeCondition::getType, BiomeConditionType::getCodec).fieldOf("condition");

    /**
     * Use this when encoding in any other context than a BiomeModifier.
     *
     * See {@link BiomeOrCondition#CODEC}
     */
    Codec<IBiomeCondition> INNER_CODEC = FROM_REGISTRY.dispatch(IBiomeCondition::getType, BiomeConditionType::getCodec);

    BiomeConditionType<?> getType();

    default BiomeInvertedCondition inverse()
    {
        return new BiomeInvertedCondition(this);
    }

    default BiomeAndCondition and(IBiomeCondition... conditions)
    {
        return new BiomeAndCondition(Lists.asList(this, conditions));
    }

    default BiomeOrCondition or(IBiomeCondition... conditions)
    {
        return new BiomeOrCondition(Lists.asList(this, conditions));
    }
}
