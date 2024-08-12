package starry.refabricated.ae2.render;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ModelLoader<T extends UnbakedModel> implements PreparableModelLoadingPlugin<T> {
    private final ResourceLocation identifier;
    private final Supplier<T> factory;

    public ModelLoader(ResourceLocation identifier, Supplier<T> factory) {
        this.factory = factory;
        this.identifier = identifier;
    }

    @Override
    public void onInitializeModelLoader(UnbakedModel unbakedModel, ModelLoadingPlugin.Context pluginContext) {
        unbakedModel.resolveParents(resourceLocation -> {
            if (resourceLocation.equals(identifier)) {
                return factory.get();
            } else {
                return null;
            }
        });
    }
}
