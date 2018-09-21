package com.vtest.it.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class Uncompress {
	private RemoveDoubleSpace removeDoubleSpace;

	@Autowired
	public void setRemoveDoubleSpace(RemoveDoubleSpace removeDoubleSpace) {
		this.removeDoubleSpace = removeDoubleSpace;
	}
	public  void Ucompress(File zipfile,String directory,String waferIdOld,String waferIdNew) throws IOException
	{
		FileInputStream fileInputStream=new FileInputStream(zipfile);
		ZipInputStream zipInputStream=new ZipInputStream(fileInputStream);
		BufferedInputStream bufferedInputStream=new BufferedInputStream(zipInputStream);
		ZipEntry zipEntry=null;
		while((zipEntry=zipInputStream.getNextEntry())!=null)
		{
			String fileName=removeDoubleSpace.remove(RemoveBracket.remove(zipEntry.getName()).replace(waferIdOld,waferIdNew));
			File unzipfile=new File(directory+"/"+fileName);
			FileOutputStream fileOutputStream=new FileOutputStream(unzipfile);
			BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			int b;
			while((b=bufferedInputStream.read())!=-1)
			{
				bufferedOutputStream.write(b);
			}
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		}
		bufferedInputStream.close();
	}
	public  void Ucompress(File zipfile,String directory) throws IOException
	{
		FileInputStream fileInputStream=new FileInputStream(zipfile);
		ZipInputStream zipInputStream=new ZipInputStream(fileInputStream);
		BufferedInputStream bufferedInputStream=new BufferedInputStream(zipInputStream);
		ZipEntry zipEntry=null;
		while((zipEntry=zipInputStream.getNextEntry())!=null)
		{
			String fileName=RemoveBracket.remove(zipEntry.getName());
			File unzipfile=new File(directory+"/"+fileName);
			FileOutputStream fileOutputStream=new FileOutputStream(unzipfile);
			BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			int b;
			while((b=bufferedInputStream.read())!=-1)
			{
				bufferedOutputStream.write(b);
			}
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		}
		bufferedInputStream.close();
	}
	public  void Ucompress(File zipfile) throws IOException
	{
		FileInputStream fileInputStream=new FileInputStream(zipfile);
		ZipInputStream zipInputStream=new ZipInputStream(fileInputStream);
		BufferedInputStream bufferedInputStream=new BufferedInputStream(zipInputStream);
		ZipEntry zipEntry=null;
		while((zipEntry=zipInputStream.getNextEntry())!=null)
		{
			File unzipfile=new File(zipfile.getParent()+"/"+RemoveBracket.remove(zipEntry.getName()));
			FileOutputStream fileOutputStream=new FileOutputStream(unzipfile);
			BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			int b;
			while((b=bufferedInputStream.read())!=-1)
			{
				bufferedOutputStream.write(b);
			}
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		}
		bufferedInputStream.close();
	}
	public  ArrayList<File> UncompressRawdataModify(File zipfile) throws IOException
	{
		ArrayList<File> arrayList=new ArrayList<>();
		FileInputStream fileInputStream=new FileInputStream(zipfile);
		ZipInputStream zipInputStream=new ZipInputStream(fileInputStream);
		BufferedInputStream bufferedInputStream=new BufferedInputStream(zipInputStream);
		ZipEntry zipEntry=null;
		while((zipEntry=zipInputStream.getNextEntry())!=null)
		{
			File unzipfile=new File(zipfile.getParent()+"/"+(zipEntry.getName()));
			FileOutputStream fileOutputStream=new FileOutputStream(unzipfile);
			BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			int b;
			while((b=bufferedInputStream.read())!=-1)
			{
				bufferedOutputStream.write(b);
			}
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			arrayList.add(unzipfile);
		}
		bufferedInputStream.close();
		return arrayList;
	}
}
