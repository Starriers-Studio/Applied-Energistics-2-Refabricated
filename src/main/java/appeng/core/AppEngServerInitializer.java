package appeng.core;

import net.fabricmc.api.DedicatedServerModInitializer;

public class AppEngServerInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        new AppEngServer();
    }
}
