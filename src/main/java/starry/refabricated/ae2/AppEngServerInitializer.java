package starry.refabricated.ae2;

import appeng.core.AppEngServer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class AppEngServerInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        new AppEngServer();
    }
}
