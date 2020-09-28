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

import com.mojang.serialization.MapCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.world.biomes.ForgeBiomeModifiers;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.base.IBiomeCondition;

public class NonVoidBiomeCondition implements IBiomeCondition
{
    public static final NonVoidBiomeCondition INSTANCE = new NonVoidBiomeCondition();

    public static final MapCodec<NonVoidBiomeCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public BiomeConditionType<?> getType()
    {
        return ForgeBiomeModifiers.NON_VOID.get();
    }

    @Override
    public boolean test(Biome biome)
    {
        return !biome.getRegistryName().equals(Biomes.THE_VOID.func_240901_a_());
    }
}
