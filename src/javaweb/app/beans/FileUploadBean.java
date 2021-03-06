package javaweb.app.beans;

public class FileUploadBean {

	private Integer Id;
	private String fileName;
	private String filePath;
	private String fileDesc;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public FileUploadBean(String fileName, String filePath, String fileDesc) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileDesc = fileDesc;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public FileUploadBean() {
		super();
	}
	
	
}
