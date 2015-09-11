package javaweb.app.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.FieldNameHelper;

import javaweb.app.beans.FileUploadBean;
import javaweb.utils.FileUploadAppProperties;

@WebServlet("/app/fileuploadservlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String FILE_PATH = "/WEB-INF/files/";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletFileUpload upload = getServletUpload();

		try {

			// Put FileItem those need to be upload into Map
			// Key: the path of file is going to be stored
			// Value: the relative FileItem object
			Map<String, FileItem> uploadFiles = new HashMap<>();

			// Using parse to get FileItem collection
			List<FileItem> items = upload.parseRequest(request);

			// 1. Build FileUploadBean collection and fill uploadFiles
			List<FileUploadBean> beans = buildFileUploadBeans(items, uploadFiles);

			// 2. Check files' post-fix
			vaidateExtName(beans);
			
			
			// 3. check the size of files: It was checked when the file upload

			// 4. Upload files
			upload(uploadFiles);

			// 5. Store uploaded information into database
			saveBeans(beans);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void vaidateExtName(List<FileUploadBean> beans) {
		// TODO Auto-generated method stub
		
	}

	private void saveBeans(List<FileUploadBean> beans) {
		// TODO Auto-generated method stub

	}

	
	/**
	 * The preparation of files upload.
	 * Gain filePath and InputStream
	 * 
	 * @param uploadFiles
	 * @throws IOException
	 */
	
	private void upload(Map<String, FileItem> uploadFiles) throws IOException {

		for(Map.Entry<String, FileItem> uploadFile:uploadFiles.entrySet()){
			
			String filePath = uploadFile.getKey();
			FileItem item = uploadFile.getValue();
			
			upload(filePath,item.getInputStream());
			
		}
		
	}
	
	
	/**
	 * File upload IO method
	 * 
	 * 
	 * @param filePath
	 * @param inputStream
	 * @throws IOException
	 */

	private void upload(String filePath, InputStream inputStream) throws IOException {
		OutputStream out = new FileOutputStream(filePath);
		
		byte[] buffer = new byte[1024];
		int len = 0;
		
		while((len = inputStream.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		
		inputStream.close();
		out.close();
		
		System.out.println(filePath);
	}

	
	/**
	 * Build FileUploadBean collection, meanwhile fill uploadFiles by FileItem
	 * 
	 * FileUploadBean Object includs id, fileName, filePath, fileDesc
	 * uploadFiles: Map<String, FileItem> type, store file field's FileItem 
	 * Key:the new file name, Value: item
	 * 
	 * The process of Construction
	 * 1. Loop the import FileItem collection to gain Map of desc. 
	 * Key: desc's fieldName(desc1, desc2 ...).  Value: desc's text
	 * 
	 * 2. Loop FileItem collection to gain FileItem Objects of fileField, construct key(desc1...)
	 * build FileUploadBean Object, and fill beans and uploadFiles 
	 * 
	 * @param items
	 * @param uploadFiles
	 * @return
	 */
	
	private List<FileUploadBean> buildFileUploadBeans(List<FileItem> items, Map<String, FileItem> uploadFiles) {

		List<FileUploadBean> beans = new ArrayList<>();
		
		
		// 1. Loop FileItem collection, Gain a Map<String, String> of desc
		// Key: fieldName(desc1, desc2...),
		// value field relate with value of string
		Map<String, String> descs = new HashMap<>();

		for (FileItem item : items) {

			if (item.isFormField())
				descs.put(item.getFieldName(), item.getString());
		}

		//
		for (FileItem item : items) {

			if (!item.isFormField()) {
				String fieldName = item.getFieldName();

				String index = fieldName.substring(fieldName.length()-1);

				String fileName = item.getName();
				String desc = descs.get("desc"+index);
				String filePath = getFilePath(fileName);
				
				
				FileUploadBean bean = new FileUploadBean(fileName, filePath,desc);
				beans.add(bean);
		
				uploadFiles.put(bean.getFilePath(), item);
			}

		}

		return beans;
	}

	/**
	 * Build a random string according to file name
	 * 1. The new file name's extension name must be same as the original one
	 * 2. Use ServletContext's getRealPath method to gain absolute path
	 * 3. User Random and current system time to composed the random file name
	 * 
	 * 
	 * @param fileName
	 * @return
	 */
	
	private String getFilePath(String fileName) {
		Random random = new Random();
		int randomNumber = random.nextInt(10000000);
		String extName = fileName.substring(fileName.lastIndexOf("."));
		String filePath = getServletContext().getRealPath(FILE_PATH)+System.currentTimeMillis() +randomNumber+ extName;
		
		
		
		return filePath;
	}

	
	/**
	 * Build ServletFileUpload Object
	 * Gain attributes from properties, users set constrain
	 * This method from document
	 * 
	 * @return
	 */
	
	private ServletFileUpload getServletUpload() {
		String exts = FileUploadAppProperties.getInstance().getProperty("exts");
		String fileMazSize = FileUploadAppProperties.getInstance().getProperty("file.MaxSize");
		String totalFileMazSize = FileUploadAppProperties.getInstance().getProperty("total.file.max.size");

		 System.out.println(exts);
		 System.out.println(fileMazSize);
		 System.out.println(totalFileMazSize);

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024 * 500);
		File file = new File("/home/tony/Documents/JAVA/tempDirectory");
		factory.setRepository(file);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(Integer.parseInt(totalFileMazSize));
		upload.setFileSizeMax(Integer.parseInt(fileMazSize));
		return upload;
	}

}
