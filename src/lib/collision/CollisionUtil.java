package lib.collision;

/**
 * slimon
 * 04.08.2014
 */
public class CollisionUtil {

    private static boolean checkAABBCollision(float[][] v1, float[][] v2) {
        float maxX1 = maxFloat(v1[0]);
        float maxY1 = maxFloat(v1[1]);
        float maxZ1 = maxFloat(v1[2]);

        float minX1 = minFloat(v1[0]);
        float minY1 = minFloat(v1[1]);
        float minZ1 = minFloat(v1[2]);

        float maxX2 = maxFloat(v2[0]);
        float maxY2 = maxFloat(v2[1]);
        float maxZ2 = maxFloat(v2[2]);

        float minX2 = minFloat(v2[0]);
        float minY2 = minFloat(v2[1]);
        float minZ2 = minFloat(v2[2]);

        return     maxX1 >= minX2 && minX1 <= maxX2
                && maxY1 >= minY2 && minY1 <= maxY2
                && maxZ1 >= minZ2 && minZ1 <= maxZ2;
    }

    public static float maxFloat(float[] array) {
        float max = array[0];
        for(float f : array) {
            if(f > max) {
                max = f;
            }
        }
        return max;
    }

    public static float minFloat(float[] array) {
        float min = array[0];
        for(float f : array) {
            if(f < min) {
                min = f;
            }
        }
        return min;
    }
}
