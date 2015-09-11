package javaweb.tony;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//1. Gain the collection of FileItem
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024 * 500);
		File file = new File("/home/tony/Documents/JAVA/tempDirectory");
		factory.setRepository(file);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(1024 * 1024 * 5);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			//2. loop items: print information if it is just a normal fileFiled 
			for(FileItem item:items){
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					
					System.out.println("Name: " + name +" value: "+value);
				} else {
					//if it is a fileFiled, store the files into d:\\files
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					long sizeInBytes = item.getSize();
					
					System.out.println(fieldName);
					System.out.println(fileName);
					System.out.println(contentType);
					System.out.println(sizeInBytes);
					
					InputStream in = item.getInputStream();
					
					byte[] buffer = new byte[1024];
					int len = 0;
					
					fileName = "/home/tony/Documents/JAVA/"+ fileName;
					System.out.println(fileName);
					OutputStream out = new FileOutputStream(fileName);
					
					while((len = in.read(buffer)) != -1){
						out.write(buffer, 0, len);
					}

					out.close();
					in.close();
				}
				
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		//1. Gain request information:
//		String file = request.getParameter("")
//		InputStream in = request.getInputStream();
//	
//		Reader reader = new InputStreamReader(in);
//		BufferedReader bufferedRead = new BufferedReader(reader);
//		
//		String str = null;
//		
//		while((str = bufferedRead.readLine()) != null){
//			System.out.println(str);
//		}
		
		
		
		
	}

}
