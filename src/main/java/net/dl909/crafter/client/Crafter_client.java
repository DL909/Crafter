package net.dl909.crafter.client;

import net.dl909.crafter.Crafter;
import net.fabricmc.api.ClientModInitializer;
import net.dl909.crafter.client.gui.screen.ingame.CrafterScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class Crafter_client implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Crafter.CRAFTER_3X3, CrafterScreen::new);
    }
}
