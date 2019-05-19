import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClearFile  
{  
    static String folder = "D:\\Java\\test";
    List<FilePojo> filelist = new ArrayList<FilePojo>();
    List<FilePojo> contentlist = new ArrayList<FilePojo>();

    public static void main(String[] args) throws IOException   
    {      
        ClearFile clear = new ClearFile();
        clear.clear(folder);        
    }
    
    public void clear(String folder) throws IOException{
        getAllFilePaths(new File(folder));
        getContentFiles(new File(folder));        
        
        for(FilePojo file : contentlist){
            search(file.getPath());
        }
        
        System.out.println("******************************************删除了以下文件************************************"); 
       /* for(FilePojo file : filelist){
            if(!file.isFind()) {                
                System.out.println(file.getPath());
                new File(file.getPath()).delete();
            }
        }*/
    } 
    
    private void getAllFilePaths(File filePath){
        File[] files = filePath.listFiles();   
        
        for(File file : files){
            if(file.isDirectory()){
                getAllFilePaths(file);
            }else{
                String filename = file.getName();                
                if(filename.contains(".jpg") 
                        || filename.contains(".png")
                        || filename.contains(".jpeg")
                        || filename.contains(".bmp")
                        || filename.contains(".gif")
                        || filename.contains(".js")
                        || filename.contains(".css")) {
                	System.out.println(file.getName());
                    FilePojo pojo = new FilePojo();
                    pojo.setName(file.getName());
                    pojo.setPath(file.getPath());                
                    filelist.add(pojo);
                }
            }
        }
    }
    
    private void getContentFiles(File filePath){
        File[] files = filePath.listFiles();   
        
        for(File file : files){
            if(file.isDirectory()){
                getContentFiles(file);
            }else{
                String filename = file.getName();                

                if(filename.contains(".html") 
                        || filename.contains(".ftl")
                        || filename.contains(".jsp")
                        || filename.contains(".css")
                        || filename.contains(".js")
                        || filename.contains(".java")) {
                	System.out.println(file.getName());
                    FilePojo pojo = new FilePojo();
                    pojo.setName(file.getName());
                    pojo.setPath(file.getPath());                
                    contentlist.add(pojo);
                }
            }
        }
    }
    
    public void search(String filename) throws IOException   
    {          
        BufferedReader br = new BufferedReader(new FileReader(filename)); 
        for(String line; (line = br.readLine()) != null; ) {                
            for(FilePojo file : filelist){
                if (line.contains(file.getName())){
                    file.setFind(true);
                }
            }
        }
        
        br.close();
    } 
    
    private class FilePojo{
        private String name;
        private String path;
        private boolean find;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public boolean isFind() {
            return find;
        }
        public void setFind(boolean find) {
            this.find = find;
        }
    }
}