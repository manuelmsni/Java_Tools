
/**
 * 
 */
public class ContenedorLiquido extends ContenedorPesado
{
    public ContenedorLiquido(int ID,int peso){
        super(ID,peso);
    }
    public double consumo(){
        return 4*peso;
    }
    public int getID(){
        return super.ID;
    }
    public String toString(){
        return "ContenedorLiquido " +  super.ID + " " + super.peso;
    }
}
