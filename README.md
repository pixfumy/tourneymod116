# MSM3Mod
The official tournament mod for Minecraft Speedrun Mayhem 3. This mod builds on previous tournament mods (Pre1.9 Racemod, SeedCycle mod, MSL mod) and accomplishes the functionality of standardizing RNG between players in a more extensible way with less boilerplate. I'm hoping that in making this public, the tournament mod problem is solved for good in MCSR and that anyone who knows a bit of fabric can make their own mod based on this.

## Features

- Seed-standardized piglin trades, blaze rod drops, zero cycles (angle, 1/8, target node height), flint rates, ender eye breaks
- Golems always drop 4 iron
- Dragon always perches after 2.5 minutes after entering end, resets on relog.
- f3+c command causes only minimal coordinate readout, not detailed coords (ex : would read 45, 32, -172 instead of all the extra decimals and stuff, remove this if you're not a purist)
- Removes mob spawns in temples during the daytime 

## Implementation

Unlike most other standardized RNG mods which rely on mixins to LevelProperties to store RNG seeds, this mod uses minecraft's own built-in class for saving misc data to the world, PersistentState. If you want to standardize a source of RNG all you need to do is initialize a single instance of RNGStream (which extends PersistentState). Each RNGStream stores a seed and has a way of getting the current seed and updating it, which can be used to generate a consistent sequence of "random" events which depends only on the world seed and is not affected by relogs. See BlazeDropsMixin as an example.

The seeds are stored in the world file, in /data/[state name].dat. This can be modified using any NBT editor (I use intellij with the Minecraft Developer Plugin to edit these).
