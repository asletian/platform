package cn.com.leadfar.oa.model;

public class Form {
	private int id;
	
	/**
	 * ������
	 */
	private String name;
	
	/**
	 * ��Ψһ��ʶ
	 */
	private String key;
	
	/**
	 * ������
	 */
	private String content;
	
	/**
	 * ������
	 */
	private String description;
	
	private FormType type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FormType getType() {
		return type;
	}
	public void setType(FormType type) {
		this.type = type;
	}
}
