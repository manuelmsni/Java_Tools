
/**
 * 
 */
public class ContenedorClimatizado extends ContenedorPesado
{
    public ContenedorClimatizado(int ID,int peso){
        super(ID,peso);
    }
    public double consumo(){
        return 5*peso;
    }
    public int getID(){
        return super.ID;
    }
    public String toString(){
        return "ContenedorClimatizado " +  super.ID + " " + super.peso;
    }
}
