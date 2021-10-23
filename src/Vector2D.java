public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(){
       this(0,0);
    }

    public static Vector2D add(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2D sub(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public Vector2D mul(float k){
        return new Vector2D(k * this.x, k * this.y);
    }

    public Vector2D div(float k){
        return new Vector2D( this.x / k,  this.y / k);
    }

    public static float dot(Vector2D v1, Vector2D v2){
        return v1.x * v2.x + v1.y * v2.y;
    }

    public float magnitude(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2D unit(){
        float m = this.magnitude();
        return new Vector2D(this.x / m, this.y / m);
    }





}
