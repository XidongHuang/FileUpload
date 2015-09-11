package javaweb.app.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javaweb.utils.FileUploadAppProperties;

@WebServlet("/app/fileuploadservlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletFileUpload upload = getServletUpload();

		
		try {
			//Using parse to get FileItem collection
			List<FileItem> items = upload.parseRequest(request);
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	private ServletFileUpload getServletUpload() {
		String exts = FileUploadAppProperties.getInstance().getProperty("exts");
		String fileMazSize = FileUploadAppProperties.getInstance().getProperty("file.MaxSize");
		String totalFileMazSize = FileUploadAppProperties.getInstance().getProperty("total.file.max.size");

		// System.out.println(exts);
		// System.out.println(fileMazSize);
		// System.out.println(totalFileMazSize);

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024 * 500);
		File file = new File("/home/tony/Documents/JAVA/tempDirectory");
		factory.setRepository(file);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(Integer.parseInt(fileMazSize));
		upload.setFileSizeMax(Integer.parseInt(fileMazSize));
		return upload;
	}

}
