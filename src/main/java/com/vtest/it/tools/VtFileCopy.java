package com.vtest.it.tools;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class VtFileCopy {
    public void copy(File srcFile,String descDirectory,String srcPath) throws IOException
    {
        if (srcFile.isFile())
        {
            File descFile=new File(descDirectory+"/"+srcFile.getName());
            if (descFile.exists())
            {
                descFile.delete();
            }
            FileUtils.copyFile(srcFile,descFile);
        }else
        {
           File targetDirectory=new File(descDirectory+"/"+srcFile.getPath().replace(srcPath,""));
           if (!targetDirectory.exists())
             {
               targetDirectory.mkdirs();
             }
            File[] files=srcFile.listFiles();
            if (files.length>0)
            {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile())
                    {
                        File descFile=new File(targetDirectory.getPath()+"/"+files[i].getName());
                        if (descFile.exists())
                        {
                            descFile.delete();
                        }
                        FileUtils.copyFile(files[i],descFile);
                    }else
                    {
                        copy(files[i],descDirectory,srcPath);
                    }
                }
            }else
            {
                (new File(descDirectory+"/"+(srcFile.getPath().replace(srcPath,"")))).mkdirs();
            }
        }
    }
}
