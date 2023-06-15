package lib.collision;

/**
 * slimon
 * 12.08.2014
 */
public class AABB {

    private float[] min, max, pos;
    private int dimension;

    /*public AABB(float[][] vertexes) {
        dimension = vertexes.length;
        min = new float[dimension];
        max = new float[dimension];
        for(int i = 0; i < dimension; i++) {
            min[i] = CollisionUtil.minFloat(vertexes[i]);
            max[i] = CollisionUtil.maxFloat(vertexes[i]);
        }
    }*/

    public AABB(float[][] vertexes) {
        dimension = vertexes[0].length;
        min = new float[dimension];
        max = new float[dimension];
        float[] dimArray;
        for(int i = 0; i < dimension; i++) {
            dimArray = new float[vertexes.length];
            for(int k = 0; k < vertexes.length; k++) {
                dimArray[k] = vertexes[k][i];
            }
            min[i] = CollisionUtil.minFloat(dimArray);
            max[i] = CollisionUtil.maxFloat(dimArray);
        }
    }

    public AABB(float[][] vertexes, float[] position) {
        this(vertexes);
        pos = position;
    }

    public AABB(float[][] vertexes, float[] position, float[] rotation) {

    }

    public void setPosition(float[] newPosition) {
        pos = newPosition;
    }

    public int getDimension() {
        return dimension;
    }

    public float[] getMin() {
        return min;
    }

    public float[] getMax() {
        return max;
    }

    public float getMin(int axis) {
        if(axis > dimension) {
            return 0;
        }
        return min[axis];
    }

    public float getMax(int axis) {
        if(axis > dimension) {
            return 0;
        }
        return max[axis];
    }
}
