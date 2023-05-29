import java.util.ArrayList;

/**
 * Write a description of class Puerto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Puerto implements IPuerto
{
    private int ID;
    private double X;
    private double Y;
    private static int contPuertos=0;
    ArrayList<Contenedor> listaContenedores;
    ArrayList<Barco> listaVisitaBarcos;
    ArrayList<Barco> listaActualBarcos;
    public Puerto(int ID,double X,double Y){
        listaContenedores = new ArrayList<Contenedor>();
        listaVisitaBarcos = new ArrayList<Barco>();
        listaActualBarcos = new ArrayList<Barco>();
        this.ID=ID;
        this.X=X;
        this.Y=Y;
        contPuertos++;
    }
    public double getDistancia(Puerto other){
        return Math.sqrt(Math.pow((other.getX()-X),2)+(Math.pow((other.getY()-Y),2)));
    }
    public double getX(){
        return X;
    }
    public double getY(){
        return Y;
    }
    public int getID(){
        return ID;
    }
    public static int getPuertosCreados(){
        return contPuertos;
    }
    public boolean addContenedor(Contenedor c){
        if(c==null)return false;
        if(listaContenedores.indexOf(c)!=-1)return false;
        listaContenedores.add(c);
        return true;
    }
    public boolean removeContenedores(Contenedor c){
        if(c==null)return false;
        if(listaContenedores.indexOf(c)==-1)return false;
        listaContenedores.remove(c);
        return true;
    }
    public int posContenedor(Contenedor c){
        return listaContenedores.indexOf(c);
    }
    public boolean equals(Object obj){
        if(obj==null)return false;
        if(!(obj instanceof Puerto))return false;
        Puerto puertecito = (Puerto)obj;
        if(puertecito.getID()==ID)return true;
        return false;
    }
    //solo añade barcos cuando no estan creados
    public void addBarcoCreado(Barco b){
        if(b!=null){
            if(listaActualBarcos.indexOf(b)==-1){
                listaActualBarcos.add(b);
                if(listaVisitaBarcos.indexOf(b)==-1){
                    listaVisitaBarcos.add(b);
                }
            }
        }
    }
    public void entradaBarco(Barco b){
        if(b!=null){
            if(listaActualBarcos.indexOf(b)==-1 && !b.getPuertoActual().equals(this)){
                listaActualBarcos.add(b);
                b.getPuertoActual().salidaBarco(b);
                b.setPuertoActual(this);
                
                if(listaVisitaBarcos.indexOf(b)==-1){
                    listaVisitaBarcos.add(b);
                }
            }
        }
    }
    public void salidaBarco(Barco b){
        if(b!=null){
            if(b.getPuertoActual().equals(this) && listaActualBarcos.indexOf(b)!=-1){
                listaActualBarcos.remove(b);
            }
        }
    }
    public Contenedor getContenedor(int ID){
        for(Contenedor c:listaContenedores){
            if(c.getID()==ID)return c;
        }
        return null;
    }
    public Barco getBarco(int ID){
        for(Barco b:listaActualBarcos){
            if(b.getID()==ID)return b;
        }
        return null;
    }
    public String toString(){
        StringBuilder datos = new StringBuilder();
        datos.append("Puerto "+ ID + ": ("+ X + ", " + Y + ") \n");
        String contenedoresBase = "ContenedoresBase: ";
        String contenedoresPesados = "ContenedoresPesados: ";
        String contenedoresLiquidos = "ContenedoresLiquidos: ";
        String contenedoresClimatizados = "ContenedoresClimatizados: ";
        int numCB = contenedoresBase.length(), numCP = contenedoresPesados.length(), numCL = contenedoresLiquidos.length(), numCC = contenedoresClimatizados.length();
        for(Contenedor c:listaContenedores){
            int id = c.getID();
            if(c instanceof ContenedorBase)contenedoresBase+=(id+" ");
            else if(c instanceof ContenedorLiquido)contenedoresLiquidos+=(id+" ");
            else if(c instanceof ContenedorClimatizado)contenedoresClimatizados+=(id+" ");
            else if(c instanceof ContenedorPesado)contenedoresPesados+=(id+" ");
        }
        if(contenedoresBase.length()!=numCB)datos.append("\t"+contenedoresBase+"\n");
        if(contenedoresPesados.length()!=numCP)datos.append("\t"+contenedoresPesados+"\n");
        if(contenedoresLiquidos.length()!=numCL)datos.append("\t"+contenedoresLiquidos+"\n");
        if(contenedoresClimatizados.length()!=numCC)datos.append("\t"+contenedoresClimatizados+"\n");
        for(Barco b:listaActualBarcos){
            datos.append(b.toString());
            
        }
        return datos.toString();
    }
}
