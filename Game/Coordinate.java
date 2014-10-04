package Game;
public class Coordinate{
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(String d){
        if(d.charAt(0)=='C'){
            d = d.replaceAll("C|\\(|\\)","");
            String [] sp = d.split(",");
            this.x = Integer.parseInt(sp[0]);
            this.y = Integer.parseInt(sp[1]);
        }
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public Boolean equals(Coordinate b){
        if (x==b.getX() && y==b.getY()){
            return true;
        }
        return false;
    }

    public String toString(){
        return "C("+x+","+y+")";
    }
}
