
/**
 * 
 */
public class ContenedorBase extends Contenedor
{
    public ContenedorBase(int ID,int peso){
        super(ID,peso);
    }
    public double consumo(){
        return 2.5*peso;
    }
    public int getID(){
        return super.ID;
    }
    public String toString(){
        return "ContenedorBase " +  super.ID + " " + super.peso;
    }
}
