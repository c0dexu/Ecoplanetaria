import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Organism {
    public static ArrayList<Organism> organisms = new ArrayList<>(); // container ce retine toate organismele din ecosistem
    public static final float PI = 3.141592653589793238462643383279502884197169f;
    public static final float maxMass = 50;
    public static float maxVelocity = 0.0f;
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D force; // forta ce actioneaza asupra organismului(sursa de locomotie)
    public Color color; // culoarea organismului
    public ArrayList<Point> points; // punctele ce formeaza organismul(un poligon)
    public int numberOfPoints; // determina numarul de puncte ale poligonului

    // starile organismului(se misca, doarme, mananca)

    enum OrgStates{
        stateIdle,
        stateMove,
        stateEat,
        stateBuild,
        stateSeekHome
    }

    // scalari ce determina masa, orientarea si sanatatea organismului
    float mass;
    float angle;
    float life = 1;

    public Organism(float mass, Vector2D position, Vector2D velocity){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.initPolygon(maxMass);
        organisms.add(this);
    }

    public Organism(float mass, Vector2D position){
        this.position = position;
        this.velocity = new Vector2D();
        this.mass = mass;
        this.initPolygon(maxMass);
        organisms.add(this);
    }

    // metoda ce actualizeaza viteza si pozitia organismului
    public void update(float dt){
        velocity = Vector2D.add(velocity, (force.mul(1/mass)).mul(dt));
        position = Vector2D.add(position, velocity.mul(dt));
    }

    public void initPolygon(float maxMass){

        this.points = new ArrayList<>();
        numberOfPoints = ThreadLocalRandom.current().nextInt(3, 7); // nr de puncte va fi intre 3 si 6
        int i;
        float theta = 0.0f; // fiecare punct este rotit cu acest unghi
        float dtheta = (2 * PI) / numberOfPoints;
        for(i = 0; i < numberOfPoints; i++){
            int distanceFromOrigin = ThreadLocalRandom.current().nextInt(8, 16); // distanta de la centrul poligonului
                                                                                                 // sa nu fie mai mare de 15 pixeli
            // stocam individual coordonatele centrului de la care calculam locatia organismului
            float xcenter = this.position.x;
            float ycenter = this.position.y;

            // calculam pozitia punctului, pornind de la coordonatele centrului (xcenter, ycenter),
            // plus pozitia relativa la centru
            float xpoint = (float) (xcenter + distanceFromOrigin * Math.cos((double) theta));
            float ypoint = (float) (ycenter + distanceFromOrigin * Math.sin((double) theta));

            this.points.add(new Point((int)xpoint, (int)ypoint));

            theta += dtheta; // actualizam unghiul de rotire al urmatorului punct

        }

        // dupa ce s-au generat punctele, stabilim culoarea poligonului in functie de masa organismului

        float r, g ,b;
        float rRange, gRange, bRange;

        rRange = ThreadLocalRandom.current().nextInt(0, 256);
        gRange = ThreadLocalRandom.current().nextInt(0, 256);
        bRange = ThreadLocalRandom.current().nextInt(0, 256);

        r = (rRange * mass) / (maxMass);
        g = (gRange * mass) / (maxMass);
        b = (bRange * mass) / (maxMass);

        this.color = new Color(r, g, b);


    }

    public void rotate(){
        // aici se actualizeaza toate punctele poligonului

    }

    public void setColor(int r, int g, int b){
        color = new Color(r, g, b);
    }

    // functie ce creeaza un nou organism, mostenind proprietatile organismului vechi.
    // simuleaza reproducerea asexuala
    public void inherit() throws CloneNotSupportedException {
        Organism inherited_organism = (Organism) this.clone(); // noul organism mosteneste proprietatile org. vechi
        organisms.add(inherited_organism);
        organisms.remove(this);

    }

    // printam proprietatile organismului
    public void print(){
        System.out.println("Mass: " + this.mass);
        System.out.println("Position: (" + this.position.x + ", " + this.position.y + ")");
        System.out.println("Velocity: (" + this.velocity.x + ", " + this.velocity.y + ")");
        System.out.println("Force: (" + this.force.x + ", " + this.force.y + ") N");
    }


    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        // g2d.drawPolygon(points);
    }

    // comportamentul organismului

    // metoda in care organismul se indreapta spre / evita un punct(target)
    public void steering_seek(Vector2D target, boolean canAvoid){

        Vector2D vector_distance;
        // acest lucru il face pe organism sa evite/mearga spre target
        if (!canAvoid){
            vector_distance = Vector2D.sub(target, this.position);
        }
        else{
            vector_distance = Vector2D.sub(this.position, target);
        }


        Vector2D desired_impulse = vector_distance.unit().mul(maxVelocity); // calculam impulsul destinatie

        Vector2D steer = Vector2D.sub(desired_impulse, this.velocity);

        // calculam noua forta
        force = Vector2D.add(force, steer);

        // cu cat organismul e aproape de target, cu atat incetineste
        force = force.mul(vector_distance.magnitude() * .01f);

    }

    // noi comportamente

    // metoda in care organismul se indreapta spre altul
    // el va ajunge in "fata" altui organism
    public void steering_face_organism(Organism other){

    }


}
