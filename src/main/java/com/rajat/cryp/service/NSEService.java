package com.rajat.cryp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.stereotype.Service;

@Service
public class NSEService {
	
	public static void main(String[] args) throws IOException {
		NSEService s=new NSEService();
		s.downloadNseFile();
	}
	
	public String downloadNseFile() throws IOException {
		String s="\\"+"app"+"\\"+"src"+"\\"+"main"+"\\"+"java"+"\\"+"com"+"\\"+"rajat"+"\\"+"cryp"+"\\"+"service"+"\\";
        String script = "script.sh";
        File fl=new File(script);
        String sa=fl.getAbsolutePath().replaceAll("script.sh", "")+s;
        String cUrl="",url="",csvFileName="",zipFileName="",filePath=sa;
		ZonedDateTime now= ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		Calendar cal = Calendar.getInstance();
		
		for(int i=0;i<5;i++) {
		String day=String.valueOf(now.minusDays(i).getDayOfMonth());
		String year=String.valueOf(now.minusDays(i).getYear());
		String month=now.minusDays(i).getMonth().toString().substring(0, 3);
		csvFileName="cm"+day+month+year+"bhav.csv";
		zipFileName="cm"+day+month+year+"bhav.csv.zip";
		url="https://www.nseindia.com/content/historical/EQUITIES/"+year+"/"+month+"/"+zipFileName;
		cUrl="curl "+url+" -H 'Host: www.nseindia.com' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,/;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' > "+filePath+zipFileName;
//		System.out.println(cUrl);
		if(downloadFile(cUrl,filePath,zipFileName,csvFileName))
				break;
		}
		return filePath+csvFileName;
	}
	
	public boolean downloadFile(String cUrl,String filePath, String zipFileName,String csvFileName) throws IOException {
		boolean flag=false;		
        String script = "script.sh";
        File fl=new File(script);
        FileWriter f = new FileWriter(script);
        try {
            f.write(cUrl);
            f.close();           
            Process awk = new ProcessBuilder("/bin/bash", filePath + script).start();
            awk.waitFor();      
            flag= NSEService.unZipFile(filePath, zipFileName, csvFileName);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
//            Debug.appendLogFile("generateFile=" + e.getMessage());
        } finally {
            f.close();
        }
        return flag;
	}
	
	public static boolean unZipFile(String filePath,String zipFileName,String csvFileName) {		
		File f=new File(filePath+zipFileName);		
		if(!f.exists())
			return false;
		FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(filePath+zipFileName);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            File newFile=null;
            while(ze != null){
                String fileName = ze.getName();
                newFile = new File(filePath + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
            if(newFile.exists())
            	return true;            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}
}
