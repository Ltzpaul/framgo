# Spruce Auto Farm — starter project

- Minecraft Java: 1.21.11
- Loader: Fabric
- Java: 21
- Mod ID: `spruceautofarm`
- Output name target: `SpruceAutoFarm.jar`

## Current state

This is a **starter prototype**, not the finished farming bot:
- Registers `K` to toggle the farm state.
- Registers `J` to toggle the order state.
- Displays a small HUD status panel.
- Does not yet plant, use Bone Meal, break blocks, move items, or interact with order menus.

## Build

Install JDK 21 and use Gradle 9.x. From this folder:

```bash
gradle build
```

The remapped mod JAR should appear in `build/libs/`.

## Important

Before implementing automated world interaction or automated order submission, check Hugo SMP's rules and obtain permission if macros/bots are restricted. Do not add anti-cheat bypasses, packet spoofing, or protection evasion.
