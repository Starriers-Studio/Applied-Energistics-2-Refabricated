package starry.refabricated.ae2;

import appeng.core.AppEngServer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class AppEngServerInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        new AppEngServer();
    }
}
