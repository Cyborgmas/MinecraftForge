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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapCodec.MapCodecCodec;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.mojang.serialization.codecs.PairCodec;
import com.mojang.serialization.codecs.PairMapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * IBiomeCondition Codec holder.
 */
public class BiomeConditionType<A extends IBiomeCondition> extends ForgeRegistryEntry<BiomeConditionType<?>>
{
    private final Codec<A> codec;

    /**
     * Requires a {@link MapCodec}, so that the {@link Codec#dispatch} can function properly, as the {@link KeyDispatchCodec} does
     * an instance check on {@link MapCodecCodec}.
     *
     * There is always a way to create a MapCodec implementation that is equivalent to the Codec implementation:
     *
     * {@link RecordCodecBuilder#mapCodec} vs {@link RecordCodecBuilder#create}
     * or {@link PairMapCodec} vs {@link PairCodec}
     *
     * The dispatch is done in {@link IBiomeCondition}
     */
    public BiomeConditionType(MapCodec<A> codec)
    {
        this.codec = codec.codec();
    }

    public Codec<A> getCodec()
    {
        return codec;
    }
}
