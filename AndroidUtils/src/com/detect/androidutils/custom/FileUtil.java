package com.detect.androidutils.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.content.Context;

public class FileUtil {
	
	private static final String TAG = "FileUtil";

	/**
     * 创建文件
     * @param file  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createFile(File file,String filecontent){
    	if(file == null || MyFunc.isNullOrEmpty(filecontent)) return false;
        Boolean bool = false; 
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                //创建文件成功后，写入内容到文件里
                writeFileContent(file.getAbsolutePath(), filecontent, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    public static boolean createFile(File file){
		if (file == null)
			return false;
		if (file.exists()) {
			LogUtil.i(TAG, "文件已存在： " + file.getAbsolutePath());
			return true;
		}
		try {
			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
    
    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param content  写入的内容\
     * @param reWrite 是否重写
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String content, boolean reWrite) throws IOException{
        Boolean bool = false;
        String filein = content+"\r\n";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            if(!reWrite){
	            //文件原有内容
	            while((temp =br.readLine())!=null){
	                buffer.append(temp);
	                // 行与行之间的分隔符 相当于“\n”
	                buffer = buffer.append(System.getProperty("line.separator"));
	            }
	            buffer.append(filein);
            }else{
            	buffer.append(content);
            }
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    
    public static String readTxt(File file){
    	if(file == null) return null;
    	if(!file.exists()){
    		LogUtil.e(TAG, "文件不存在： " + file.getAbsolutePath());
    		return null;
    	}
    	StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append('\n'+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    /**
     * 读取raw文件， 返回路径
     * @param context
     * @param name
     * @param id
     * @return
     */
	public static String saveRaw(Context context, String name, int id) {
		File target = new File(context.getFilesDir(), name);
		if (target.exists())
			return target.getAbsolutePath();
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getResources().openRawResource(id);
			byte[] buf = new byte[2048];
			int len = 0;

			long sum = 0;
			File file = new File(target.getParent());
			if (!file.exists())
				file.mkdirs();

			fos = new FileOutputStream(target);
			while ((len = is.read(buf)) != -1) {
				sum += len;
				fos.write(buf, 0, len);

			}
			fos.flush();
			return target.getAbsolutePath();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
