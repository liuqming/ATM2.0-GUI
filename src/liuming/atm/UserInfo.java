package liuming.atm;

import java.util.Date;

public class UserInfo {
	
	private String name;//�û���
	
	private String password;//����
	
	private float account;//�ֽ�
	
	private Date date;//��¼����ʱ��
	//��ò���ʱ��
	public Date getDate() {
		return date;
	}
	//���ò���ʱ��
	public void setDate(Date date) {
		this.date = date;
	}
	//����û���
	public String getName() {
		return name;
	}
	//�����û���
	public void setName(String name) {
		this.name = name;
	}
	//�������
	public String getPassword() {
		return password;
	}
	//��������
	public void setPassword(String password) {
		this.password = password;
	}
	//����û�������ֽ�
	public float getAccount() {
		return account;
	}
	//�����û�������ֽ�
	public void setAccount(float account) {
		this.account = account;
	}
}