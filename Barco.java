import java.util.ArrayList;
import java.util.Collections;
/**
 * Write a description of class Barco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Barco implements IBarco,Comparable
{
    private int ID;
    private static int contBarcs=0;
    private double combustible;
    Puerto puertoActual;
    double consumoCombustiblePorKM;
    ArrayList<Contenedor> listaContenedores;
    private int totalPesoCapacidad,numMaxContenedoresTotales,numMaxContenedoresPesados,numMaxContenedoresClimatizados,numMaxContenedoresLiquidos;
    private int totalPeso,pesados,climatizados,liquidos;
    public Barco(int ID,Puerto puertoActual,int totalPesoCapacidad,int numMaxContenedoresTotales,
    int numMaxContenedoresPesados, int numMaxContenedoresClimatizados,int numMaxContenedoresLiquidos,
    double consumoCombustiblePorKM){
        listaContenedores = new ArrayList<Contenedor>();
        contBarcs++;
        combustible=0;
        this.totalPesoCapacidad=totalPesoCapacidad;
        this.numMaxContenedoresTotales=numMaxContenedoresTotales;
        this.numMaxContenedoresPesados=numMaxContenedoresPesados;
        this.numMaxContenedoresClimatizados=numMaxContenedoresClimatizados;
        this.numMaxContenedoresLiquidos=numMaxContenedoresLiquidos;
        totalPeso=0;
        numMaxContenedoresTotales=0;
        pesados=0;
        climatizados=0;
        this.consumoCombustiblePorKM=consumoCombustiblePorKM;
        liquidos=0;
        this.ID=ID;
        this.puertoActual=puertoActual;
    }
    public static int getContBarcs(){
        return contBarcs;
    }
    public ArrayList<Contenedor> getContenedores(){
        Collections.sort(listaContenedores);
        return listaContenedores;
    }
    public int compareTo (Object obj){
        return ((Barco)obj).getID()-ID;
    }
    public int getID(){
        return ID;
    }
    public Puerto getPuertoActual(){
        return puertoActual;
    }
    public void setPuertoActual(Puerto p){
        puertoActual=p;
    }
    public boolean navegaA(Puerto p){
        if(puertoActual.equals(p))return false;
        double consumo=p.getDistancia(puertoActual)*consumoTotalKM();
        if(consumo<=combustible){
            combustible-=consumo;
            p.entradaBarco(this);
        }
        return true;
    }
    public double consumoTotalKM(){
        double total=consumoCombustiblePorKM;
        for(Contenedor c:listaContenedores){
            total+=c.consumo();
        }
        return total;
    }
    public void reCombustible(double newCombustible){
        combustible+=newCombustible;
    }
    public boolean carga(Contenedor cont){
        if(cont==null)return false;
        if(listaContenedores.indexOf(cont)!=-1 || puertoActual.posContenedor(cont)==-1)return false;
        if(getPesoTotal()+cont.getPeso()>totalPesoCapacidad)return false;
        if(listaContenedores.size()>=numMaxContenedoresTotales)return false;
        if(cont instanceof ContenedorBase){
            if(puertoActual.removeContenedores(cont)){
                listaContenedores.add(cont);
                return true;
            }        
        }else if(cont instanceof ContenedorPesado &&  pesados<numMaxContenedoresPesados){
            if(puertoActual.removeContenedores(cont)){
                pesados++;
                listaContenedores.add(cont);
                return true;
            }
        }else if(cont instanceof ContenedorLiquido &&  liquidos<numMaxContenedoresLiquidos){
            if(puertoActual.removeContenedores(cont)){
                liquidos++;
                listaContenedores.add(cont);
                return true;
            }
        }else if(cont instanceof ContenedorClimatizado &&  climatizados<numMaxContenedoresClimatizados){
            if(puertoActual.removeContenedores(cont)){
                climatizados++;
                listaContenedores.add(cont);
                return true;
            }
        }
        return false;
    }
    public int getPesoTotal(){
        int pesoTotal=0;
        for(Contenedor c:listaContenedores){
            pesoTotal+=c.getPeso();
        }
        return pesoTotal;
    }
    public boolean desCarga(Contenedor cont){
        if(cont==null)return false;
        if(listaContenedores.indexOf(cont)==-1)return false;
        if(cont instanceof ContenedorPesado)pesados--;
        else if(cont instanceof ContenedorLiquido)liquidos--;
        else if(cont instanceof ContenedorClimatizado)climatizados--;
        listaContenedores.remove(cont);
        puertoActual.addContenedor(cont);
        return true;
    } 
    public String toString(){
        StringBuilder datos = new StringBuilder();
        datos.append("\tBarco "+ ID + "\n");
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
        if(contenedoresBase.length()!=numCB)datos.append("\t\t"+contenedoresBase+"\n");
        if(contenedoresPesados.length()!=numCP)datos.append("\t\t"+contenedoresPesados+"\n");
        if(contenedoresLiquidos.length()!=numCL)datos.append("\t\t"+contenedoresLiquidos+"\n");
        if(contenedoresClimatizados.length()!=numCC)datos.append("\t\t"+contenedoresClimatizados+"\n");
        return datos.toString();
    }
    public Contenedor getContenedor(int ID){
        for(Contenedor c: listaContenedores){
            if(c.getID()==ID)return c;
        }
        return null;
    }
}
