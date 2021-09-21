package game.settings;

public class RenderSettings {

    private final Setting<Boolean> shouldRenderGrib;

    public RenderSettings() {
        this.shouldRenderGrib = new Setting<>(false);
    }

    public Setting<Boolean> getShouldRenderGrib() {
        return shouldRenderGrib;
    }
}
