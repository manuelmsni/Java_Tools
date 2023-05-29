
/**
 * Write a description of class IBarco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IBarco
{
    public boolean navegaA(Puerto p);
    public void reCombustible(double newCombustible);
    public boolean carga(Contenedor cont);
    public boolean desCarga(Contenedor cont);
}
