import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 *
 */
public class Selector extends JFileChooser{
    
    public Selector(String extension){
        super("./");
        setFileFilter(new Filtro(extension));
    }
    
    
    public File getFile(){
        int returnVal=showOpenDialog(null);    
        return super.getSelectedFile();
    }
    
    
    class Filtro extends FileFilter{
        String extension;
        public Filtro(String extension){
            this.extension=extension;
            
        }
        public boolean accept(File f){
            String nombre = f.getName();
            if(f.isDirectory())return false;
            if(extension == null) return true;
            if(!nombre.contains("."))return false;
            
            String ending = nombre.substring(nombre.lastIndexOf(".")+1);
            
            boolean passExtension = false;
            if(extension.contains(",")){
                String[] buffer = extension.split(",");
                for(String ext: buffer){
                    if(ending.equals(ext.trim())) passExtension = true;
                }
            } else{
                if(ending.equals(extension.trim())) passExtension = true;
            }
            if(passExtension){
                return true;
            }
            return false;
        }
        
        public String getDescription(){
            return "";
        }
    }
    
}
