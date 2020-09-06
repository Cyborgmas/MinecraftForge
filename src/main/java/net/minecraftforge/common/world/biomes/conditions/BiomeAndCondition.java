package net.minecraftforge.common.world.biomes.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.biomes.ForgeBiomeModifiers;
import net.minecraftforge.common.world.biomes.conditions.base.BiomeConditionType;
import net.minecraftforge.common.world.biomes.conditions.base.IBiomeCondition;

public class BiomeAndCondition implements IBiomeCondition
{
    public static final MapCodec<BiomeAndCondition> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    IBiomeCondition.INNER_CODEC.fieldOf("first").forGetter(or -> or.first),
                    IBiomeCondition.INNER_CODEC.fieldOf("second").forGetter(or -> or.second)
            ).apply(inst, BiomeAndCondition::new)
    );

    private final IBiomeCondition first;
    private final IBiomeCondition second;

    public BiomeAndCondition(IBiomeCondition first, IBiomeCondition second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public BiomeConditionType<BiomeAndCondition> getType()
    {
        return ForgeBiomeModifiers.AND_COMBINED.get();
    }

    @Override
    public boolean test(Biome biome)
    {
        return first.test(biome) && second.test(biome);
    }
}
