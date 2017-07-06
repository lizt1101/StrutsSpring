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
	
	private String upfileFileName;//***FileName��***�����ļ�upfile�ļ���
	
	private String allowedExtensions;//�ļ���׺jpg,png
	
	private long maximumSize;//�ϴ�ͼƬ��С,�ֽ�Ϊ��λ,Ĭ������Ϊ10M

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
		//ȡ���ļ��ϴ�·�������ڴ���ϴ����ļ���  
        File uploadFile = new File(ServletActionContext.getServletContext().getRealPath("upload"));
        //File uploadFile = new File("D://lzt");
        //�ж�����·���Ƿ���ڣ�����������򴴽���·��  
        if (!uploadFile.exists()) { 
            uploadFile.mkdir();  
        }
		if(upfile != null){
			//�õ��ļ��ĺ�׺
			String hz = upfileFileName.substring(upfileFileName.lastIndexOf(".")+1);		
			String[] type = allowedExtensions.split(",");
			List<String> list = new ArrayList<String>();
			for(int i=0;i<type.length;i++){
				list.add(type[i]);
			}
			//�жϸ������Ƿ��ڼ��ϵ���
			if(list.contains(hz)){
				if(upfile.length()>maximumSize){
					request.put("error", "�ļ�����");
					return "error";
				}else{
					//ͼƬ��������
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
						System.out.println("�������!"+(end-start));
					} catch (Exception e) {
						e.printStackTrace();  
		                ServletActionContext.getServletContext().setAttribute("error", upfileFileName + "�ϴ������з���δ֪��������ϵ����Ա���ϴ�ʧ�ܣ�");
					}finally{
						fis.close();
						fos.close();
						upfile.delete(); 
					}
					return "success";
				}
			}else{
				request.put("error", "�ļ���ʽ����");
				return "error";
			}
		}else{
			request.put("error", "��ѡ��ͼƬ");
			return "error";
		}
	}

		

}
