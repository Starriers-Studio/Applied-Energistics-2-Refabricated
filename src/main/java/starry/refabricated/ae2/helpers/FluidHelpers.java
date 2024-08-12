package starry.refabricated.ae2.helpers;

public class FluidHelpers {

    public enum FluidAction {
        EXECUTE, SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }

        public boolean simulate() {
            return this == SIMULATE;
        }
    }

}
