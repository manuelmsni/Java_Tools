
/**
 * 
 */
public class ContenedorPesado extends Contenedor
{
    public ContenedorPesado(int ID,int peso){
        super(ID,peso);
    }
    public double consumo(){
        return 3*peso;
    }
    public int getID(){
        return super.ID;
    }
    public String toString(){
        return "ContenedorPesado " +  super.ID + " " + super.peso;
    }
}
