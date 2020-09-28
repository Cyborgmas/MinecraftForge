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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.biomes.ForgeBiomeModifiers;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.base.IBiomeCondition;

import java.util.List;

public class BiomeOrCondition implements IBiomeCondition
{
    public static final MapCodec<BiomeOrCondition> CODEC = IBiomeCondition.INNER_CODEC.listOf().xmap(BiomeOrCondition::new, or -> or.conditions).fieldOf("conditions");

    private final List<IBiomeCondition> conditions;

    public BiomeOrCondition(List<IBiomeCondition> conditions)
    {
        this.conditions = conditions;
    }

    @Override
    public BiomeConditionType<BiomeOrCondition> getType()
    {
        return ForgeBiomeModifiers.OR_COMBINED.get();
    }

    @Override
    public boolean test(Biome biome)
    {
        for(IBiomeCondition cond : conditions)
        {
            if(cond.test(biome))
                return true;
        }
        return false;
    }
}
