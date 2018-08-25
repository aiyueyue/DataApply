package com.ahkeli.map;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileOperate {
    public FileOperate() {
   
    }
    /**
	 * ��תΪ�ֽ�����
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] InputStreamToByte(InputStream is) throws IOException {    
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();    
		int ch;    
		while ((ch = is.read()) != -1) {    
			bytestream.write(ch);    
	    }    
		byte imgdata[] = bytestream.toByteArray();    
		bytestream.close();    
		return imgdata;    
	} 
    /**
     * �½�Ŀ¼
     * @param folderPath String �� c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
    	String[] folderPathSz=folderPath.split("/");
    	for(int i=1;i<folderPathSz.length-1;i++){
    		try {
    			String str="/"+folderPathSz[i]+"/";
    			int beginIndex=0;
    			int endIndex=folderPath.indexOf(str)+str.length();
    			String folderPathSub=folderPath.substring(beginIndex, endIndex);
    			//System.out.println(folderPathSub);
    			File myFilePath = new File(folderPathSub);
    			if(!myFilePath.exists())
    				myFilePath.mkdir();
    		}
    		catch (Exception e) {
    			System.out.println("�½�Ŀ¼��������");
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * �½��ļ�
     * @param filePathAndName String �ļ�·�������� ��c:/fqf.txt
     * @param fileContent String �ļ�����
     * @return boolean
     */
    public static void newFile(String filePathAndName, String fileContent) {

      try {
        String filePath = filePathAndName;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        if (!myFilePath.exists()) {
          myFilePath.createNewFile();
        }
        FileWriter resultFile = new FileWriter(myFilePath);
        PrintWriter myFile = new PrintWriter(resultFile);
        String strContent = fileContent;
        myFile.println(strContent);
        resultFile.close();

      }
      catch (Exception e) {
        System.out.println("�½�Ŀ¼��������");
        e.printStackTrace();

      }

    }

    /**
     * ɾ���ļ�
     * @param filePathAndName String �ļ�·�������� ��c:/fqf.txt
     * @param fileContent String
     * @return boolean
     */
    public void delFile(String filePathAndName) {
      try {
        String filePath = filePathAndName;
        filePath = filePath.toString();
        File myDelFile = new File(filePath);
        myDelFile.delete();

      }
      catch (Exception e) {
        System.out.println("ɾ���ļ���������");
        e.printStackTrace();

      }

    }

    /**
     * ɾ���ļ���
     * @param filePathAndName String �ļ���·�������� ��c:/fqf
     * @param fileContent String
     * @return boolean
     */
    public void delFolder(String folderPath) {
      try {
        delAllFile(folderPath); //ɾ����������������
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete(); //ɾ�����ļ���

      }
      catch (Exception e) {
        System.out.println("ɾ���ļ��в�������");
        e.printStackTrace();

      }

    }

    /**
     * ɾ���ļ�������������ļ�
     * @param path String �ļ���·�� �� c:/fqf
     */
    public void delAllFile(String path) {
      File file = new File(path);
      if (!file.exists()) {
        return;
      }
      if (!file.isDirectory()) {
        return;
      }
      String[] tempList = file.list();
      File temp = null;
      for (int i = 0; i < tempList.length; i++) {
        if (path.endsWith(File.separator)) {
          temp = new File(path + tempList[i]);
        }
        else {
          temp = new File(path + File.separator + tempList[i]);
        }
        if (temp.isFile()) {
          temp.delete();
        }
        if (temp.isDirectory()) {
          delAllFile(path+"/"+ tempList[i]);//��ɾ���ļ���������ļ�
          delFolder(path+"/"+ tempList[i]);//��ɾ�����ļ���
        }
      }
    }

    /**
     * ���Ƶ����ļ�
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt
     * @param newPath String ���ƺ�·�� �磺f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
      try {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) { //�ļ�����ʱ
          InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�
          FileOutputStream fs = new FileOutputStream(newPath);
          byte[] buffer = new byte[1444];
          int length;
          while ( (byteread = inStream.read(buffer)) != -1) {
            bytesum += byteread; //�ֽ��� �ļ���С
            //System.out.println(bytesum);
            fs.write(buffer, 0, byteread);
          }
          inStream.close();
        }
      }
      catch (Exception e) {
        System.out.println("���Ƶ����ļ���������");
        e.printStackTrace();

      }

    }
    /**
     * �ϴ��ļ�
     * @param inStream
     * @param newPath
     */
    public static void upFile(InputStream inStream, String newPath) {
        try {
          int bytesum = 0;
          int byteread = 0;
         // File oldfile = new File(oldPath);
          if (inStream!=null) { //�ļ�����ʱ
           // InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�
            FileOutputStream outStream = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            int length;
            while ( (byteread = inStream.read(buffer)) != -1) {
              bytesum += byteread; //�ֽ��� �ļ���С
             // System.out.println(bytesum);
              outStream.write(buffer, 0, byteread);
            }
            inStream.close();
            outStream.close();            
          }
        }
        catch (Exception e) {
          System.out.println("�ϴ������ļ���������");
          e.printStackTrace();

        }

      }
    /**
     * �����ļ�
     * @param outStream
     * @param fileName
     */
    public static void downFile(OutputStream outStream,String fileName){
        try {
            File file=new File(fileName);
            InputStream inPut = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = inPut.read(buf)) >0)
            outStream.write(buf,0,len);
            inPut.close();
            outStream.close();
        } catch (IOException e) {
            System.out.println("���ص����ļ���������");
            e.printStackTrace();
        }
         
       
    }
    /**
     * ���������ļ�������
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf
     * @param newPath String ���ƺ�·�� �磺f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

      try {
        (new File(newPath)).mkdirs(); //����ļ��в����� �������ļ���
        File a=new File(oldPath);
        String[] file=a.list();
        File temp=null;
        for (int i = 0; i < file.length; i++) {
          if(oldPath.endsWith(File.separator)){
            temp=new File(oldPath+file[i]);
          }
          else{
            temp=new File(oldPath+File.separator+file[i]);
          }

          if(temp.isFile()){
            FileInputStream input = new FileInputStream(temp);
            FileOutputStream output = new FileOutputStream(newPath + "/" +
                (temp.getName()).toString());
            byte[] b = new byte[1024 * 5];
            int len;
            while ( (len = input.read(b)) != -1) {
              output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
          }
          if(temp.isDirectory()){//��������ļ���
            copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
          }
        }
      }
      catch (Exception e) {
        System.out.println("���������ļ������ݲ�������");
        e.printStackTrace();

      }

    }

    /**
     * �ƶ��ļ���ָ��Ŀ¼
     * @param oldPath String �磺c:/fqf.txt
     * @param newPath String �磺d:/fqf.txt
     */
    public void moveFile(String oldPath, String newPath) {
      copyFile(oldPath, newPath);
      delFile(oldPath);

    }

    /**
     * �ƶ��ļ���ָ��Ŀ¼
     * @param oldPath String �磺c:/fqf.txt
     * @param newPath String �磺d:/fqf.txt
     */
    public void moveFolder(String oldPath, String newPath) {
      copyFolder(oldPath, newPath); 
      delFolder(oldPath);

    }
    
//  �õ�����ļ���
    public String generateFileName(String fileName) {    
        DateFormat format = new SimpleDateFormat("yyMMddHHmmss");    
        String formatDate = format.format(new Date());     
        int random = new Random().nextInt(10000);       
        int position = fileName.lastIndexOf(".");    
        String extension = fileName.substring(position);    
            
        return formatDate + random + extension;    
    }  
    public  static void copy(File src, File dst) {      
           InputStream in = null;      
           OutputStream out = null;   
           int BUFFER_SIZE = 16 * 1024; 
            try {      
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);      
    	            out = new BufferedOutputStream(new FileOutputStream(dst),      
    	                   BUFFER_SIZE);      
               byte[] buffer = new byte[BUFFER_SIZE];      
                int len = 0;      
                while ((len = in.read(buffer)) > 0) {      
    	                out.write(buffer, 0, len);      
    	            }      
            } catch (Exception e) {      
              e.printStackTrace();      
           } finally {      
               if (null != in) {      
                    try {      
                        in.close();      
                    } catch (IOException e) {      
                      e.printStackTrace();      
                    }      
                }      
    	            if (null != out) {      
    	               try {      
                        out.close();      
                   } catch (IOException e) {      
    	                   e.printStackTrace();      
                   }      
               }      
           }      
    	   }
    public static boolean isNonEmpty(Object[] objArray) {   
    	       boolean result = false;   
    	        for (int index = 0; index < objArray.length && !result; index++) {   
    	           if (objArray[index] != null) {   
    	                result = true;   
                }   
          }   
            return result;   
       } 




}
