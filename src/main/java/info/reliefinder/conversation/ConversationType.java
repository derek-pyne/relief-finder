package info.reliefinder.conversation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ConversationType {
    POST_SHIFT("Post Shift"),
    SEE_SHIFTS("See Shifts");

    @Getter
    private final String label;

    public static ConversationType valueOfLabel(String label) {
        for (ConversationType e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
