package net.pixfumy.tourneymod116;

import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RNGStreamGenerator {
    private HashMap<String, Long> rngSeeds;

    public RNGStreamGenerator(long worldSeed) {
        /*  abstracts some of the standardization into this HashMap. To add a new standardized source of RNG, put your entries
            into this map, use your world's RNGStreamGenerator in a mixin, and then update tellPlayerCurrentRates if you want to.
        */
        rngSeeds = new HashMap<>() {
            {
                put("blazeSeed", worldSeed ^ 64711520272L);
                put("enderEyeSeed", worldSeed ^ 0x99A2B75BBL);
                put("flintSeed", worldSeed ^ 0xF110301001B2L);
                put("piglinSeed", worldSeed ^ 47195191252716912L);
            }
        };
    }

    public long getSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            return rngSeeds.get(id);
        }
        return -1;
    }

    public long getAndUpdateSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            long oldSeed = rngSeeds.get(id);
            Random random = new Random(oldSeed);
            long ret = Math.abs((int) oldSeed) % (int) Math.pow(16.0D, 4.0D);
            rngSeeds.put(id, random.nextLong());
            return ret;
        }
        return -1;
    }

    public void setSeed(String id, long seed) {
        rngSeeds.put(id, seed);
    }

    public Set<Map.Entry<String, Long>> entrySet() {
        return rngSeeds.entrySet();
    }
}
