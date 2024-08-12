package appeng.core;

import net.fabricmc.api.ClientModInitializer;

public class AppEngClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new AppEngClient();
    }
}
