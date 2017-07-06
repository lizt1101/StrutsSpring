package cn.tedu.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class UploadAction extends BaseAction{
	//test git
	private File upfile;
	
	private String upfileFileName;//***FileName，***代表文件upfile文件名
	
	private String allowedExtensions;//文件后缀jpg,png
	
	private long maximumSize;//上传图片大小,字节为单位,默认设置为10M

	public long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public File getUpfile() {
		return upfile;
	}

	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}

	public String getUpfileFileName() {
		return upfileFileName;
	}

	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}
	
	public String execute() throws IOException{
		//取得文件上传路径（用于存放上传的文件）  
        File uploadFile = new File(ServletActionContext.getServletContext().getRealPath("upload"));
        //File uploadFile = new File("D://lzt");
        //判断上述路径是否存在，如果不存在则创建该路径  
        if (!uploadFile.exists()) { 
            uploadFile.mkdir();  
        }
		if(upfile != null){
			//得到文件的后缀
			String hz = upfileFileName.substring(upfileFileName.lastIndexOf(".")+1);		
			String[] type = allowedExtensions.split(",");
			List<String> list = new ArrayList<String>();
			for(int i=0;i<type.length;i++){
				list.add(type[i]);
			}
			//判断该类型是否在集合当中
			if(list.contains(hz)){
				if(upfile.length()>maximumSize){
					request.put("error", "文件过大");
					return "error";
				}else{
					//图片重新命名
					long sysTime = System.currentTimeMillis();
					String newImgName = String.valueOf(sysTime)+"_"+upfileFileName;
					FileInputStream fis = new FileInputStream(upfile);
					FileOutputStream fos = new FileOutputStream(uploadFile+"\\"+newImgName);
					try {
						byte[] data = new byte[1024*10];
						int len = -1;
						long start = System.currentTimeMillis();
						while((len=fis.read(data))!=-1){
							fos.write(data,0,len);
						}
						long end = System.currentTimeMillis();
						System.out.println("复制完毕!"+(end-start));
					} catch (Exception e) {
						e.printStackTrace();  
		                ServletActionContext.getServletContext().setAttribute("error", upfileFileName + "上传过程中发生未知错误，请联系管理员。上传失败！");
					}finally{
						fis.close();
						fos.close();
						upfile.delete(); 
					}
					return "success";
				}
			}else{
				request.put("error", "文件格式不对");
				return "error";
			}
		}else{
			request.put("error", "请选择图片");
			return "error";
		}
	}

		

}
