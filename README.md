# Tourney Mod 1.16
(Formerly MSM3 Mod) This is a general tournament mod for 1.16. Implementation is based on Seedcycle, MSL and Pre1.9 Racemod.

## Features

- Seed-standardized piglin trades, blaze rod drops, blaze spawn positions and cooldown between cycles, dragon fights (zero, node choices, perch), flint rates, ender eye breaks
- Zero cycles are also possible on more seeds. Node height ranges from 0-5 blocks above the minimum height, instead of the vanilla range of 0-20. the 1/8 chance of the dragon picking a horizontal node is now 1/16.
- Golems always drop 4 iron
- Removes mining fatigue effect from elder guardians
- Dragon always perches after 3 minutes after entering end, resets on relog.
- Removes mob spawns in temples


## Commands for seedfinders

If you have cheats enabled, there are two commands you can use to modify RNG. Make sure you disable cheats before giving a world save to runners.

`/rates` will print the RNG information of a world, in the following format:

![image](https://github.com/pixfumy/tourneymod116/assets/95588510/b8087c69-608e-409b-935a-bcb1c99fa1b5)

`/resetRNGSeed [blazeRodSeed|blazeSpawnSeed|enderEyeSeed|flintSeed|piglinSeed]` will reroll a specific source of RNG specified in the argument. For example, if you notice that `/rates` is giving you bad blaze rates, you can run `/resetRNGSeed blazeRodSeed` and run `/rates` again until you're happy with them. Note that the seeds auto-complete:

![image](https://github.com/pixfumy/tourneymod116/assets/95588510/7560965b-4f08-49f5-bbb7-bd265abe84de)
