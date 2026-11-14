package com.spruceautofarm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderType;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

/**
 * Starter client mod: keybinds + HUD state only.
 * Actual world interaction / order workflow is intentionally not enabled yet.
 */
public final class SpruceAutoFarmClient implements ClientModInitializer {
    private static boolean farmEnabled = false;
    private static boolean orderEnabled = false;

    private static KeyMapping toggleFarmKey;
    private static KeyMapping toggleOrderKey;

    @Override
    public void onInitializeClient() {
        toggleFarmKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.spruceautofarm.toggle_farm",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KeyMapping.Category.MISC
        ));

        toggleOrderKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.spruceautofarm.toggle_order",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleFarmKey.consumeClick()) {
                farmEnabled = !farmEnabled;
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("Spruce Auto Farm: " + (farmEnabled ? "ON" : "OFF")),
                            true
                    );
                }
            }

            while (toggleOrderKey.consumeClick()) {
                orderEnabled = !orderEnabled;
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("Auto Order: " + (orderEnabled ? "ON" : "OFF")),
                            true
                    );
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null || client.options.hideGui) return;

            int x = 8;
            int y = 8;
            drawContext.drawString(client.font, "SPRUCE AUTO FARM", x, y, 0xFFFFFF, true);
            drawContext.drawString(client.font, "Farm: " + state(farmEnabled) + " [K]", x, y + 12, 0xFFFFFF, true);
            drawContext.drawString(client.font, "Order: " + state(orderEnabled) + " [J]", x, y + 24, 0xFFFFFF, true);
            drawContext.drawString(client.font, "Prototype - no automatic actions yet", x, y + 36, 0xAAAAAA, true);
        });
    }

    private static String state(boolean enabled) {
        return enabled ? "ON" : "OFF";
    }
}
