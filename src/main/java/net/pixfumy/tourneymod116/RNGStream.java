package net.pixfumy.tourneymod116;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;

import java.util.Random;

public class RNGStream extends PersistentState {
    private long seed;
    public RNGStream(String key, long seed) {
        super(key);
        this.seed = seed;
    }

    public long getAndUpdateSeed() {
        Random random = new Random(this.seed);
        this.seed = random.nextLong();
        return this.seed;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        if (tag.contains(this.getId())) {
            this.seed = tag.getLong(this.getId());
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong(this.getId(), this.seed);
        return tag;
    }
}
