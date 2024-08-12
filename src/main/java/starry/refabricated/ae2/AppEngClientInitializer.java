package starry.refabricated.ae2;

import appeng.core.AppEngClient;
import net.fabricmc.api.ClientModInitializer;

public class AppEngClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new AppEngClient();
    }
}
