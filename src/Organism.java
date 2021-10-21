public class Organism {
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D force;

    float mass;
    float angle;

    public Organism(float mass, Vector2D position, Vector2D velocity){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public Organism(float mass, Vector2D position){
        this.position = position;
        this.velocity = new Vector2D();
        this.mass = mass;
    }

    public void update(float dt){
        velocity = Vector2D.add(velocity, (force.mul(1/mass)).mul(dt));
        position = Vector2D.add(position, velocity.mul(dt));
    }

    public void print(){
        System.out.println("Mass: " + this.mass);
        System.out.println("Position: (" + this.position.x + ", " + this.position.y + ")");
        System.out.println("Velocity: (" + this.velocity.x + ", " + this.velocity.y + ")");
        System.out.println("Force: (" + this.force.x + ", " + this.force.y + ") N");
    }
}
