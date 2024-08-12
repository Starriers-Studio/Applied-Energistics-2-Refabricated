package starry.refabricated.ae2.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class MouseEvents {

    public static final Event<WheelScrolled> SCROLLED = EventFactory.createArrayBacked(WheelScrolled.class, (listeners) -> (scrollDelta) -> {
        for (WheelScrolled listener : listeners) {
            if (listener.onWheelScrolled(scrollDelta)) {
                return true;
            }
        }
        return false;
    });

    @FunctionalInterface
    public interface WheelScrolled {
        boolean onWheelScrolled(double scrollDelta);
    }
}
