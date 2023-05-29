import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main{
    private static Selector s = new Selector("txt");
    private static ArrayList<Puerto> puertos;
    public static void main(String[] args){
        ArrayList<String> datitos = getDatos();
        if(datitos!=null){
            puertos = new ArrayList<Puerto>();
            for(String d:datitos){
                clasificar(d);
                
            }
            System.out.println(datos());
        }else{
            System.out.println("No se han encontrado datos");
        }
    }
    public static Puerto getPuerto(int ID){
        for(Puerto p:puertos){
            if(p.getID()==ID){
                return p; 
            }
        }
        return null;
    }
    public static Barco getBarco(int ID){
        Barco b = null;
        for(Puerto p:puertos){
            b= p.getBarco(ID);
            if(b!=null){
                return b;
            }
            
        }
        return null;
    }
    
    public static boolean clasificar(String linea){
        linea=linea.replaceAll(",", ".");
        String[] dats = linea.split(" ");
        try{
            double x,y;
            int id;
            Puerto p;
            Contenedor c;
            int pes;
            Barco b;
            int operacion = Integer.valueOf(dats[0]);
            switch(operacion){
                case 1: //Crear Contenedor
                    if(dats.length>=3){
                        p = getPuerto(Integer.valueOf(dats[1]));
                        if(p!=null){
                            c=null;
                            pes = Integer.valueOf(dats[2]);
                            if(dats.length>=4){
                                switch(dats[3]){
                                    case "L":
                                        c=new ContenedorLiquido(Contenedor.getContIds(),pes);
                                        break;
                                    case "R":
                                        c=new ContenedorClimatizado(Contenedor.getContIds(),pes);
                                        break;
                                    default: System.out.println("Ese tipo de contenedor no existe: case1");
                                        break;
                                }
                            }else if(pes>3000){
                                c = new ContenedorPesado(Contenedor.getContIds(),pes);
                            }else if(pes>0){
                                c = new ContenedorBase(Contenedor.getContIds(),pes);
                            }
                            if(c!=null){
                                p.addContenedor(c);
                            }else{
                                System.out.println("No se ha podido crear el contenedor: case1");
                            }
                        }else{
                            System.out.println("Puerto no encontrado: case1");
                        }
                    }else{
                        System.out.println("Cantidad de params erronea: case1");
                    }
                    break;
                case 2://Crear Barco
                    if(dats.length>=8){
                        p = getPuerto(Integer.valueOf(dats[1]));
                        if(p!=null){
                            b = new Barco(Barco.getContBarcs(),p,Integer.valueOf(dats[2]),Integer.valueOf(dats[3]),Integer.valueOf(dats[4]),Integer.valueOf(dats[5]),Integer.valueOf(dats[6]),Double.valueOf(dats[7]));
                            if(b!=null){
                                p.addBarcoCreado(b); 
                            }
                        }else{
                            System.out.println("no existe el puerto con ese ID: case2");
                        }
                    }else{
                        System.out.println("Cantidad de params erronea: case2");
                    }
                    break;
                    
                case 3://Crear Puerto
                    if(dats.length>=3){
                        x=Double.valueOf(dats[1]);
                        y=Double.valueOf(dats[2]);
                        id=Puerto.getPuertosCreados();
                         p = new Puerto(id,x,y);
                        puertos.add(p);
                    }else{
                        System.out.println("Cantidad de params erronea: case3");
                    }
                    break;
                    
                case 4://Cargar contenedor a Barco
                    if(dats.length>=3){
                        b=getBarco(Integer.valueOf(dats[1]));
                        if(b!=null){
                            c = b.getPuertoActual().getContenedor(Integer.valueOf(dats[2]));
                            if(c!=null){
                                if(!b.carga(c)){
                                    System.out.println("No se ha podido cargar el contenedor: case 4");
                                }
                            }else{
                                System.out.println("No esta el contenedor en el puerto: case 4");
                            }
                        }else{
                            System.out.println("El barco no se ha podido encontrar: case 4");
                        }
                        
                    }else{
                        System.out.println("Cantidad de params erronea: case4");
                    }
                    break;

                case 5://Descargar Contenedor de Barco
                    if(dats.length>=3){
                        b=getBarco(Integer.valueOf(dats[1]));
                        if(b!=null){
                            c = b.getContenedor(Integer.valueOf(dats[2]));
                            if(c!=null){
                                if(b.getPuertoActual().addContenedor(c)){
                                    b.desCarga(c);
                                } else{
                                    System.out.println("No se ha podido descargar el contenedor al puerto: case 5");
                                }
                            }else{
                                System.out.println("No está el contenedor en el barco: case 5");
                            }
                        }else{
                            System.out.println("El barco no se ha podido encontrar: case 5");
                        }
                        
                    }else{
                        System.out.println("Cantidad de params erronea: case5");
                    }
                    break;
                
                case 6://Navegar a otro Puerto
                    if(dats.length>=3){
                        b=getBarco(Integer.valueOf(dats[1]));
                        if(b!=null){
                            p=getPuerto(Integer.valueOf(dats[2]));
                            if(p!=null){
                                b.navegaA(p);
                            }else{
                                System.out.println("El puerto de destino no se ha podido encontrar: case 6");
                            }
                        }else{
                            System.out.println("El barco no se ha podido encontrar: case 6");
                        }
                    }else{
                        System.out.println("Cantidad de params erronea: case6");
                    }
                    break;
                    
                case 7://Cargar combustible en Barco
                    if(dats.length>=3){
                        b=getBarco(Integer.valueOf(dats[1]));
                        if(b!=null){
                            b.reCombustible(Double.valueOf(dats[2]));
                        }
                    }else{
                        System.out.println("Cantidad de params erronea: case7");
                    }
                    break;
                
                default: return false;
            }
        }catch(NumberFormatException nfe){
            System.out.println("no se puede castear");
            return false;
        }catch(NullPointerException npe){
            System.out.println("No hay suficientes parametros");
            return false;
        }
        return true;
    }
    public static ArrayList<String> getDatos(){
        File f = s.getFile();
        if(f==null)return null;
        FileReader fr = null;
        try{
           fr= new FileReader(f);
        }catch(FileNotFoundException fnt){
            System.out.println("error1");
        }
        if(fr==null)return null;
        BufferedReader bf =null;
        bf =  new BufferedReader(fr);
        if(bf==null)return null;
        ArrayList<String> datos = new ArrayList<String>();
        String linea;
        try{
            int cont=Integer.valueOf(bf.readLine());
            do{
                linea = bf.readLine();
                if(linea!=null) 
                    if(!linea.isBlank()) datos.add(linea)
                ;
                cont--;
            }while(!(linea==null || cont<=0));
            
        }catch(IOException eaf){
            System.out.println("error2");
        }
        if(datos.size()<=0)return null;
        return datos;
    }
    public static String datos(){
        StringBuilder datos = new StringBuilder();
        for(Puerto p:puertos){
            datos.append(p.toString());
        }
        return datos.toString();
    }
}
