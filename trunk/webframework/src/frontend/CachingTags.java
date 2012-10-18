/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class CachingTags {
    private static MiddlewareHandler handler=new MiddlewareHandler();
    public static List<Tag> ltags=new ArrayList<Tag>();
    private FileWriter fileWriter;
    public CachingTags() throws TException{
//        for(int i=0;i<112;i++){
//            Tag temp=new Tag();
//            temp.tagID=String.valueOf(i);
//            temp.tagName="TagName of " + String.valueOf(i);
//            ltags.add(temp);
//        }
        ltags=handler.getAllTag();
    }
    public boolean CachingTagsFile(){
        boolean result=true;
        
        Gson gson=new Gson();
        try {
            String contentFile;
            contentFile = "var listTags='";
            //lisTags=ltags;
            //String strListTags=gson.toJson(ltags);
            //String contentFile="var listTags=[";
//            for(int i=0;i<ltags.size();i++){
//                contentFile+="["+'"'+ltags.get(i).tagID+'"'+","+'"'+ltags.get(i).tagName+'"'+"]"+",";
//            }
//            contentFile+="]"+";" +"//alert(listTags);";
            contentFile+=gson.toJson(ltags)+"';";
           File newTextFile = new File("src/webapp/cachingfolder/listtags.js");
            fileWriter = new FileWriter(newTextFile);
            fileWriter.write(contentFile);
            fileWriter.close();

        } catch (IOException e) {
            result=false;
        }
        return result;
    }
}
