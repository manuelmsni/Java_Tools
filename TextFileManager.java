package form;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/**
 * This class manages the reading / writing of plain text in files.
 * 
 * @author manuelmsni
 * @version 1.0
 */

public class TextFileManager implements Serializable{
    
    private File f;
    
    /**
     * This constructor calls the mathod that instantiates a File with the provided path.
     * @path The path of the file.
     */
    public TextFileManager(String path){
        innitFile(path);
    }
    
    /**
     * This method launch a graphic representation of an alert message in a modal window.
     * @title The title at the top of the window.
     * @message The alert message.
     */
    private void alert(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    // File reader --------------------------------------------------
    
    private FileReader fr;
    private BufferedReader br;
    
    /**
     * This method instantiates the File with the provided path.
     * @path The path of the file.
     */
    private void innitFile(String path){
        if(!(path == null || path.isBlank())) f = new File(path);
        else alert("File error 0", "La ruta no es válida");
    }
    
    /**
     * If the File is instantiated, this method instantiates the FileReader.
     */
    private void innitFileReader(){
        try{
            if(f != null) fr = new FileReader(f);
        } catch (FileNotFoundException fnfe){
            alert("FileReader error", "Imposible iniciar el lector de archivos");
        }
    }
    
    /**
     * If the FileReader is instantiated, this method instantiates the BufferedReader.
     */
    private void innitBufferedReader(){
        if(fr != null) br = new BufferedReader(fr);
    }
    
    /**
     * Returns the next line in the file. Returns null if there is no more lines. If the file has been overwrited (not modified) by this object while reading, returns the first line.
     * If the FileReader or the BufferedReader are null, it calls the corresponding methods to instantiate it.
     */
    public String readLine(){
        String linea;
        if (br == null){
            if(f == null){
                alert("File error 1", "El fichero no está disponible");
                return null;
            }
            if(fr == null) innitFileReader();
            innitBufferedReader();
            if(br == null) return null;
        }
        try{
            return br.readLine();
        }catch (IOException ioe){
            alert("BufferedReader error", "¡Imposible efectuar la lectura!");
            return null;
        }catch (NullPointerException npe){
            alert("Null pointer", "¡El lector se ha perdido!");
            return null;
        }
    }
    
    /**
     * Resets the reader by setting it in null. The next read will start at the begining of the file.
     */
    public void resetTextReader(){
        fr = null;
        br = null;
    }
    
    // File writer --------------------------------------------------
    
    FileWriter fw;
    boolean append;
    /**
     * This method instantiates the FileWritter.
     * @append If true the FileWritter will append the text at the end of the file. | If false the FileWritter will overwrite the file.
     */
    private void innitFileWritter(boolean append){
        this.append = append;
        try{
            fw = new FileWriter(f, append);
        }catch (IOException ioe){
            alert("FileWriter error 0", "No es posible iniciar el escritor");
        }
    }
    /**
     * This method checks if the file is instantiated, if so, it calls the method that instantiates the FileWriter. After this, it checks if it has been instantiated correctly.
     * @append If true the FileWritter will append the text at the end of the file. | If false the FileWritter will overwrite the file.
     */
    private boolean checkWritter(boolean append){
        if(fw == null){
            if(f == null){
                alert("File error 2", "El fichero no está disponible");
                return false;
            }
            if(this.append == append || fw == null){
                innitFileWritter(append);
                if(fw == null){
                    alert("FileWritter error 0", "El escritor no se ha podido iniciar");
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Writes a String in the file trough the FileWritter. Then it closes the FileWriter.
     * @text Text to write.
     */
    private void tryWrite(String text){
        try{
            fw.write(text);
            fw.close();
            fw = null;
            if(!append && fr != null) resetTextReader();
        }catch (IOException ioe){
            alert("FileWriter error 1", "No es posible escribir en el fichero");
        }
    }
    
    /**
     * Calls the method that instantiates the FileWritter in append mode and writes a String at the end of the file.
     * @text Text to write.
     */
    public void append(String text){
        if(checkWritter(true)){
            tryWrite(text);
        } else{
            alert("Writing error 0", "No se ha podido escribir en el fichero");
        }
    }
    
    /**
     * Calls the method that instantiates the FileWritter in overwrite mode and writes a String in the file.
     * @text Text to write.
     */
    public void overwrite(String text){
        if(checkWritter(false)){
            tryWrite(text);
        } else{
            alert("Writing error 1", "No se ha podido escribir en el fichero");
        }
    }
    
    /**
     * Appends text in the next line by calling the method that appends a String at the end of the file.
     */
    public void appendLine(String text){
        append("\r\n" + text);
    }
    
}
