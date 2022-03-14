package utils;

import com.badlogic.gdx.math.Vector2;

public class Mat {
    public static float angle(float angle1, float angle2) {
        Vector2 vec1 = new Vector2((float)Math.cos(angle1), (float)Math.sin(angle1));
        Vector2 vec2 = new Vector2((float)Math.cos(angle2), (float)Math.sin(angle2));
        return vec1.angle(vec2);
    }
}
