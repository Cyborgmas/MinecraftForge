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

package net.minecraftforge.common.world.biomes;

import net.minecraftforge.common.world.biomes.conditions.*;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.NonVoidBiomeCondition;
import net.minecraftforge.common.world.biomes.modifiers.SimpleFeaturesAdditions;
import net.minecraftforge.common.world.biomes.modifiers.SimpleSpawnsAdditions;
import net.minecraftforge.common.world.biomes.modifiers.base.BiomeModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeBiomeModifiers
{
    public static final DeferredRegister<BiomeConditionType<?>> BIOME_CONDITIONS = DeferredRegister.create(ForgeRegistries.BIOME_CONDITION_TYPES, "forge");
    public static final DeferredRegister<BiomeModifierType<?>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.BIOME_MODIFIER_TYPES, "forge");

    public static final RegistryObject<BiomeConditionType<BiomeCategoryMatchesCondition>> MATCHES_CATEGORY = BIOME_CONDITIONS.register("matches_category", () -> new BiomeConditionType<>(BiomeCategoryMatchesCondition.CODEC));
    public static final RegistryObject<BiomeConditionType<BiomeMatchesCondition>> MATCHES_BIOME = BIOME_CONDITIONS.register("matches_biome", () -> new BiomeConditionType<>(BiomeMatchesCondition.CODEC));
    public static final RegistryObject<BiomeConditionType<BiomeInvertedCondition>> INVERTED = BIOME_CONDITIONS.register("inverted", () -> new BiomeConditionType<>(BiomeInvertedCondition.CODEC));
    public static final RegistryObject<BiomeConditionType<BiomeAndCondition>> AND_COMBINED = BIOME_CONDITIONS.register("and_combined", () -> new BiomeConditionType<>(BiomeAndCondition.CODEC));
    public static final RegistryObject<BiomeConditionType<BiomeOrCondition>> OR_COMBINED = BIOME_CONDITIONS.register("or_combined", () -> new BiomeConditionType<>(BiomeOrCondition.CODEC));
    public static final RegistryObject<BiomeConditionType<NonVoidBiomeCondition>> NON_VOID = BIOME_CONDITIONS.register("non_void", () -> new BiomeConditionType<>(NonVoidBiomeCondition.CODEC));

    public static final RegistryObject<BiomeModifierType<SimpleFeaturesAdditions>> SIMPLE_FEATURES = BIOME_MODIFIERS.register("features_additions", () -> new BiomeModifierType<>(SimpleFeaturesAdditions.CODEC));
    public static final RegistryObject<BiomeModifierType<SimpleSpawnsAdditions>> SIMPLE_SPAWNS = BIOME_MODIFIERS.register("spawns_additions", () -> new BiomeModifierType<>(SimpleSpawnsAdditions.CODEC));

    public static void registerAll(IEventBus modBus)
    {
        BIOME_CONDITIONS.register(modBus);
        BIOME_MODIFIERS.register(modBus);
    }
}