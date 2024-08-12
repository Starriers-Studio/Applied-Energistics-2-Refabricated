package appeng.parts.reporting;

import starry.refabricated.ae2.render.ModelData;

public class ReportingModelData implements ModelData {
    private final byte spin;

    public ReportingModelData(byte spin) {
        this.spin = spin;
    }

    @Override
    public byte getSpin() {
        return spin;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(spin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportingModelData that = (ReportingModelData) o;
        return spin == that.spin;
    }
}
