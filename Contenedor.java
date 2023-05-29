
/**
 * Write a description of class Contenedor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Contenedor implements Comparable
{
    protected final int ID;
    protected int peso;
    private static int contIds=0;
    public Contenedor(int ID,int peso){
        this.ID=ID;
        this.peso=peso;
        contIds++;
    }
    public abstract double consumo();
    public boolean equals(Object obj){
        if(obj==null)return false;
        if(!(obj instanceof Contenedor))return false;
        Contenedor contenedorcito = (Contenedor)obj;
        if(contenedorcito.getID()==ID)return true;
        return false;
    }
    public int compareTo (Object obj){
        return ((Contenedor)obj).getID()-ID;
    }
    public abstract int getID();
    public static int getContIds(){
        return contIds;
    }
    public int getPeso(){
        return peso;
    }
}
