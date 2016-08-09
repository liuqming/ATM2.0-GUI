package liuming.atm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.swing.JOptionPane;

public class ATM {
	
	private float cash = 5000.0f;    //ATM��Ǯ
	private static final float MAX_CASH = 100000.0f;      //ATM����Ǯ
	private UserInfo theUser;      //��ǰ�û�
	//private HashMap allUsers;   //װ�����û���Ϣ
	Date date = new Date();
	SimpleDateFormat simpleDate = new SimpleDateFormat();
	List lis = new ArrayList();
	
	//�½�һ��Map����
	Map<String,UserInfo> map = new HashMap();   //װ�����û���Ϣ
	//����һ����Դ�������
	Properties pro = new Properties();
	
	//run����������������з���
	public void run(){
		//��ӭ����
		this.welcome();
		
		//��������
		this.loadData();
		
		int count = 0;       //����������½ʧ�ܴ�����
		boolean login = false;
		//��½
		do{
			if(this.login() == true){
				JOptionPane.showMessageDialog(null, "��½�ɹ�");
				break;
			}
			count ++;
		}while(count < 3);
		if(count > 3){
			JOptionPane.showMessageDialog(null, "���ε�½ʧ�ܣ�");
			System.exit(0);
		}
		//ѡ��ҵ��
		this.choice();
		
		
	}

	//��ӭ����
	private void welcome(){
		JOptionPane.showMessageDialog(null, "��ӭ  ICBC ���� ");
	}
	

	//��ʼ������
	private void loadData() {
		try{
			//�����ļ�
			pro.load(new FileReader("user.properties"));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "�ļ�δ�ҵ���");
		}
		
		Set setKey = pro.stringPropertyNames();//ȡ�����м�������Set������
		Collection setValues = pro.values();     //ȡ������ֵ������Collection���϶�����
		Iterator itKey = setKey.iterator();       //����Set����
		Iterator itValues = setValues.iterator();     //����Collection����
		while(itKey.hasNext()){
			while(itValues.hasNext()){
				theUser = new UserInfo();   //�½�һ��UserInfo����theUser
				theUser.setName(itKey.next().toString());   //����theUser������û���
				String[] ary1 = itValues.next().toString().split("\\$");  //��ֵ��$�п����������String�������ary1[0],�û����ֽ����String�������ary1[1]
				String[] ary2 = ary1[1].split("\\|");        //��Ǯ�������� | �ֿ�
				theUser.setPassword(ary1[0]);                        //��������
				theUser.setAccount(Float.parseFloat(ary2[0]));       //�����û��ֽ�
//===========================================================================================================
//���ַ���ת����Date����
//		method1��  
//				String date = "Wed Sep 07 21:54:58 CST 2011";
//				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
//				Date d=sdf.parse(date);
//				sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println(sdf.format(d));
//		method2��
//				String s = "Wed Sep 07 21:54:58 CST 2011";
//				Date date = new Date(s);         
//				Timestamp nousedate = new Timestamp(date.getTime());
//				System.out.println(nousedate);
//===========================================================================================================
				//����ʱ���ʽ
				SimpleDateFormat simpleDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
				try {
					//����ÿ���û�����ʱ��
					theUser.setDate(simpleDate.parse(ary2[1]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//����ʱ���ʽ
				simpleDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				lis.add(simpleDate.format(theUser.getDate()) + "    �û�����" + theUser.getName());//��Ӳ���ʱ����û�����List����
				System.out.println(theUser.getName() + "  " + theUser.getPassword() + "  " + theUser.getAccount() + "  " + theUser.getDate());
				map.put(theUser.getName(), theUser);                //�����úõ�UserInfo���󣬰��ռ���Name��ֵ��UserInfo���󣩷���Map������
			}
		}
	}

	//��½
	private boolean login(){
		String user = JOptionPane.showInputDialog("�������û�����");
		//�ж��û����Ƿ����
		if(map.get(user).getName() != null){
			String pwd = JOptionPane.showInputDialog("���������룺");
			//�ж������Ƿ�ƥ��
			if(pwd.equals(map.get(user).getPassword())){
				theUser = map.get(user);    //���û��������붼��ȷ�󣬽������������ǰ����
				//����"user.properties"�ļ���Ӧ�ļ�ֵ��
				//lis.set(lis.indexOf(theUser.getDate() + "    �û�����" + theUser.getName()),date + "    �û�����" + theUser.getName() );
				pro.setProperty(theUser.getName(), theUser.getPassword()+"$"+theUser.getAccount()+"|"+date.toString());
				try {
					//д��"user.properties"�ļ�
					pro.store(new FileWriter("user.properties"), null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "�ļ�δ�ҵ���");
				}
				return true;                //������true
			}
			else{
				//�����������������������롣
				JOptionPane.showMessageDialog(null, "����������������롣");
			}
		}
		else{
			//����û�������
			JOptionPane.showMessageDialog(null,"�û�������");
			
		}
		return false;     //����false
	}
	//ѡ��ҵ��
	private void choice(){
		do{
			//��ʾ�û�ѡ��ҵ�񣬲������û�������
			int choice = Integer.parseInt(JOptionPane.showInputDialog(
					"1����ѯ\n2��ȡ��\n3�����\n4��������\n5����ѯ��������û��Ĳ���ʱ��\n6���˳�\n��ѡ��"));
			switch(choice){
			case 1:
				this.queryMoney();    //���ò�ѯ����
				break;
			case 2:
				this.getMoney();      //����ȡ���
				break;
			case 3:
				this.storeMoney();    //���ô���
				break;
			case 4:
				this.changePWD();     //���ø����빦��
				break;
			case 5:
				this.FindListUserDate();//���ò�ѯ��������û��Ĳ���ʱ��
				break;
			case 6:
				this.exit();          //�����˳�����
				break;
				default:
					JOptionPane.showMessageDialog(null, "����������������롣");
			}
		}while(true);
	}
	//��ѯ����
	private void queryMoney(){
		System.out.println("��ѯ����ʵ��");
		JOptionPane.showMessageDialog(null,"�û���:" + theUser.getName() + "\n������Ϊ��"+theUser.getAccount());
	}
	//����
	private void storeMoney(){
		System.out.println("����ʵ��");
		String tempMoney = JOptionPane.showInputDialog("������������");
		float inputMoney  =  Float.parseFloat(tempMoney);      //���û�����Ĵ��ת��Ϊfloat��
		//��������ʽƥ���û�������
		if(tempMoney.matches("(1[0-9]{1,}00|[1-9]00|2000)")){
			cash += inputMoney;//ATM��Ǯ+�û�����Ĵ��
			theUser.setAccount(theUser.getAccount()+inputMoney);  //���õ�ǰ�û����ֽ�
			//map.put(theUser.getName(), theUser);             //���޸ĺ�����ݣ�����Map������
			//����"user.properties"�ļ���Ӧ�ļ�ֵ��
			pro.setProperty(theUser.getName(), theUser.getPassword()+"$"+theUser.getAccount()+"|"+date.toString());
			try {
				//д��"user.properties"�ļ�
				pro.store(new FileWriter("user.properties"), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "�ļ�δ�ҵ���");
			}
		}
		else if (tempMoney.matches("-[0-9]{0,}")) {
			System.out.println("���Ǽ����д渺����0��");
			return;
		}
		else if (inputMoney > this.MAX_CASH - this.cash) {
			System.out.println("�Բ��𣬱���װ������ô��Ǯ����̫����......");
			return;
		}
	}
	//ȡ���
	private void getMoney(){
		System.out.println("���ʵ��");
		String tempMoney = JOptionPane.showInputDialog("������ȡ������");
		float outputMoney = Float.parseFloat(tempMoney);       //���û�����Ĵ��ת��Ϊfloat��
		//��������ʽƥ���û�������
		if(tempMoney.matches("(1[0-9]00|[1-9]00|2000)")){
			cash -= outputMoney;  //ATM��Ǯ-�û�����Ĵ��
			theUser.setAccount(theUser.getAccount()-outputMoney);  //���õ�ǰ�û����ֽ�         
			//map.put(theUser.getName(), theUser);             //���޸ĺ�����ݣ�����Map������
			//����"user.properties"�ļ���Ӧ�ļ�ֵ��
			pro.setProperty(theUser.getName(), theUser.getPassword()+"$"+theUser.getAccount()+"|"+date.toString());
			try {
				//д��"user.properties"�ļ�
				pro.store(new FileWriter("user.properties"), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "�ļ�δ�ҵ���");
			}
		}
		else if (outputMoney <= 0) {
			System.out.println("���Ǽ�����ȡ������0��");
			return;
		}
		else if (outputMoney >= this.theUser.getAccount()) {
			System.out.println("�Բ������˻��ϵ����㣡");
			return;
		}
		else if (outputMoney > this.cash) {
			System.out.println("�Բ��𣬱����ϵ��ֽ��㣡");
			return;
		}
	}
	//�޸����빦��
	private void changePWD(){
		System.out.println("�޸����빦��ʵ��");
		String newPWD = JOptionPane.showInputDialog("�����������룺");
		theUser.setPassword(newPWD);              //�޸ĵ�ǰ�û�������
		//����"user.properties"�ļ���Ӧ�ļ�ֵ��
		pro.setProperty(theUser.getName(), theUser.getPassword()+"$"+theUser.getAccount()+"|"+date.toString());
		try {
			//д��"user.properties"�ļ�
			pro.store(new FileWriter("user.properties"), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "�ļ�δ�ҵ���");
		}
	}
	//�˳�
	private void exit(){
		JOptionPane.showMessageDialog(null,"ллʹ�ã�");
		System.exit(0);    //���������
	}
	//��ѯ��������û��Ĳ���ʱ��
	private void FindListUserDate(){
		Collections.sort(lis);
		Collections.reverse(lis);
		String str = "";
		for(int i = 0 ; i < 3 ; i++){
			str += lis.get(i) + "\n";
		}
		JOptionPane.showMessageDialog(null, str);
	}
}
