package snoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Snoop {	
	/*****创建panel2~panel5******/
	static Mypanel panel2 =new Mypanel();
	static Mypanel panel3 =new Mypanel();
	static Mypanel panel4 =new Mypanel();
	static Mypanel panel5 =new Mypanel();
	
	
	
	/*********memory的标题*********/
	static String[] Mem_ca={
			"Memory","状态","","Memory","状态","","Memory","状态",""
	};
	
	/*********memory中的内容*********/
	static String[][] Mem_Content ={
			{"0","","","10","","","20","",""},{"1","","","11","","","21","",""},{"2","","","12","","","22","",""},
			{"3","","","13","","","23","",""},{"4","","","14","","","24","",""},{"5","","","15","","","25","",""},
			{"6","","","16","","","26","",""},{"7","","","17","","","27","",""},{"8","","","18","","","28","",""},
			{"9","","","19","","","29","",""}
	};
	//相联方式
	static JComboBox<String> Mylistmodel1_1 = new JComboBox<>(new Mylistmodel());
	static class Mylistmodel extends AbstractListModel<String> implements ComboBoxModel<String>{		
		private static final long serialVersionUID = 1L;
		String selecteditem=null;
		private String[] test={"直接映射","两路组相联","四路组相联"};
		public String getElementAt(int index){
			return test[index];
		}
		public int getSize(){
			return test.length;
		}
		public void setSelectedItem(Object item){
			selecteditem=(String)item;
		}
		public Object getSelectedItem( ){
			return selecteditem;
		}
		public int getIndex() {
			for (int i = 0; i < test.length; i++) {
				if (test[i].equals(getSelectedItem()))
					return i;
			}
			return 0;
		}
		
	}
	//读写方式
	static class Mylistmodel2 extends AbstractListModel<String> implements ComboBoxModel<String>{		
		private static final long serialVersionUID = 1L;
		String selecteditem=null;
		private String[] test={"读","写"};
		public String getElementAt(int index){
			return test[index];
		}
		public int getSize(){
			return test.length;
		}
		public void setSelectedItem(Object item){
			selecteditem=(String)item;
		}
		public Object getSelectedItem( ){
			return selecteditem;
		}
		public int getIndex() {
			for (int i = 0; i < test.length; i++) {
				if (test[i].equals(getSelectedItem()))
					return i;
			}
			return 0;
		}
		
	}
	//处理器的Cache面板
	static class Mypanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		JLabel label=new JLabel("访问地址");
		JLabel label_2=new JLabel("Process1");
		
		JTextField jtext=new JTextField("");
		JButton button=new JButton("执行");
		JComboBox<String> Mylistmodel = new JComboBox<>(new Mylistmodel2());
		
		
		/*********cache中的标题*********/
		String[] Cache_ca={"Cache","状态","目标地址"};
		/*********cache中的内容*********/
		String[][] Cache_Content = {
				{"0","Invalid"," "},{"1","Invalid"," "},{"2","Invalid"," "},{"3","Invalid"," "}
		};
		/************cache的滚动模版***********/
		JTable table_1 = new JTable(Cache_Content,Cache_ca); 
		JScrollPane scrollPane = new JScrollPane(table_1);
		/*
		/************memory的滚动模版**********
		JTable table_2 = new JTable(Mem_Content,Mem_ca); 
		JScrollPane scrollPane2 = new JScrollPane(table_2);
		*/
		public Mypanel(){
			super();
			setSize(350, 250);
			setLayout(null);
			
			/*****添加原件********/
			add(jtext);
			add(label);
			add(label_2);
			add(button);
			add(Mylistmodel);
			add(scrollPane);
			//add(scrollPane2);
			
			/****设置原件大小与字体********/
			label_2.setFont(new Font("",1,16));
			label_2.setBounds(10, 10, 100, 30);
			
			label.setFont(new Font("",1,16));
			label.setBounds(10, 50, 100, 30);
			
			jtext.setFont(new Font("",1,15));
			jtext.setBounds(100, 50, 50, 30);
			
			Mylistmodel.setFont(new Font("",1,15));
			Mylistmodel.setBounds(160, 50, 50, 30);
			
			scrollPane.setFont(new Font("",1,15));
			scrollPane.setBounds(10, 90, 310, 90);
			
			//scrollPane2.setFont(new Font("",1,15));
			//scrollPane2.setBounds(10, 190, 310, 180);
			
			button.setFont(new Font("",1,15));
			button.setBounds(220,50, 100, 35);
			
			/******添加按钮事件********/
			button.addActionListener(this);
		}
		
		public void init(){
			/******Mypanel的初始化******/
			jtext.setText(" ");
			Mylistmodel.setSelectedItem(null);
			for(int i=0;i<=3;i++)
				Cache_Content[i][1]="Invalid";
			for(int i=0;i<=3;i++)
				Cache_Content[i][2] = " ";
			for(int i=0;i<=9;i++)
				for(int j=1;j<=2;j++)
					Mem_Content[i][j]=" ";
			setVisible(false);
			setVisible(true);
			
		}
		
		public void actionPerformed(ActionEvent e){
			/******编写自己的处理函数*******/
			int target, i, j, list_model;
			Mypanel panel_exec = null;
			if(e.getSource() == panel2.button)				//确定当前所执行的Cache面板
				panel_exec = panel2;
			else if(e.getSource() == panel3.button)
				panel_exec = panel3;
			else if(e.getSource() == panel4.button)
				panel_exec = panel4;
			else if(e.getSource() == panel5.button)
				panel_exec = panel5;
			list_model = (int)Math.pow(2, Mylistmodel1_1.getSelectedIndex());	//获取相联度
			target = Integer.parseInt(panel_exec.jtext.getText()); 				//获取访问地址
			if(panel_exec.Mylistmodel.getSelectedIndex()==0){					//读
				for (i=target%(4/list_model)*list_model; i< (target%(4/list_model)+1)*list_model; i++){
					if(panel_exec.Cache_Content[i][2].equals(String.valueOf(target))){		//所读地址在Cache中
						if(panel_exec.Cache_Content[i][1].equals("Invalid"))
							break;
						else
							return;
					}
				}
				for (i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
					if (panel_exec.Cache_Content[i][1].equals("Invalid"))
						break;
				}
				panel_exec.Cache_Content[i][2] = String.valueOf(target);		//目标地址写入
				panel_exec.Cache_Content[i][1] = "Shared";						//设置状态为共享
				if(panel_exec != panel2){							//将其他处理器的修改状态改为共享
					for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
						if(panel2.Cache_Content[i][1].equals("Modified") && 
								panel2.Cache_Content[i][2].equals(String.valueOf(target)))
							panel2.Cache_Content[i][1] = "Shared";
					}
				}
				if(panel_exec != panel3){
					for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
						if(panel3.Cache_Content[i][1].equals("Modified") && 
								panel3.Cache_Content[i][2].equals(String.valueOf(target)))
							panel3.Cache_Content[i][1] = "Shared";
					}
				}
				if(panel_exec != panel4){
					for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
						if(panel4.Cache_Content[i][1].equals("Modified") && 
								panel4.Cache_Content[i][2].equals(String.valueOf(target)))
							panel4.Cache_Content[i][1] = "Shared";
					}
				}
				if(panel_exec != panel5){
					for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
						if(panel5.Cache_Content[i][1].equals("Modified") && 
								panel5.Cache_Content[i][2].equals(String.valueOf(target)))
							panel5.Cache_Content[i][1] = "Shared";
					}
				}
			}
			else{											//写
				int Nomodified = 1;
				for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
					if(panel_exec.Cache_Content[i][2].equals(String.valueOf(target))){
						panel_exec.Cache_Content[i][1] = "Modified";		//将目标地址状态改为修改
						Nomodified = 0;
						break;
					}
					if(Nomodified==1){						//如果目标地址之前未读过或写过
						for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
							if(panel_exec.Cache_Content[i][1].equals("Invalid"))	//遇到第一个无效Cache块退出
								break;
						}
						panel_exec.Cache_Content[i][2] = String.valueOf(target);	//将该Cache块的目标地址写入
						panel_exec.Cache_Content[i][1] = "Modified";				//将该Cache块的状态改为修改
					}
					if(panel_exec != panel2){										//将其他处理器的Cache块中的目标地址内容设置为无效
						for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
							if(panel2.Cache_Content[i][2].equals(String.valueOf(target)))
								panel2.Cache_Content[i][1] = "Invalid";
						}
					}
					if(panel_exec != panel3){
						for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
							if(panel3.Cache_Content[i][2].equals(String.valueOf(target)))
								panel3.Cache_Content[i][1] = "Invalid";
						}
					}
					if(panel_exec != panel4){
						for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
							if(panel4.Cache_Content[i][2].equals(String.valueOf(target)))
								panel4.Cache_Content[i][1] = "Invalid";
						}
					}
					if(panel_exec != panel5){
						for(i=target%(4/list_model)*list_model; i<(target%(4/list_model)+1)*list_model; i++){
							if(panel5.Cache_Content[i][2].equals(String.valueOf(target)))
								panel5.Cache_Content[i][1] = "Invalid";
						}
					}
				}
			}
			
			/**********显示刷新后的数据********/
			panel2.setVisible(false);
			panel2.setVisible(true);
			panel3.setVisible(false);
			panel3.setVisible(true);					
			panel4.setVisible(false);
			panel4.setVisible(true);
			panel5.setVisible(false);
			panel5.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myjf = new JFrame("多cache一致性模拟之监听法");
		myjf.setSize(1500, 600);
		myjf.setLayout(null);
		myjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container C1 = myjf.getContentPane();
		
		JTable table_2 = new JTable(Mem_Content,Mem_ca); 
		JScrollPane scrollPane2 = new JScrollPane(table_2);
		
		/*****新建panel1*****/
		JPanel panel1 = new JPanel();

		C1.add(panel2);
		C1.add(panel3);
		C1.add(panel4);
		C1.add(panel5);
		C1.add(scrollPane2);
		panel2.setBounds(10, 100, 350, 200);
		panel3.setBounds(360, 100, 350, 200);
		panel4.setBounds(720, 100, 350, 200);
		panel5.setBounds(1080, 100, 350, 200);
		scrollPane2.setBounds(200,350,1000,180);
		scrollPane2.setFont(new Font("",1,15));
		//scrollPane2.setBounds(100, 250, 310, 180);
		
		/********设置每个Mypanel的不同的参数************/
		panel2.label_2.setText("Process1");
		panel3.label_2.setText("Process2");
		panel4.label_2.setText("Process3");
		panel5.label_2.setText("Process4");
		panel2.table_1.getColumnModel().getColumn(0).setHeaderValue("cache1");
		panel2.Cache_ca[0]="Cache1";
		panel3.table_1.getColumnModel().getColumn(0).setHeaderValue("cache2");
		panel3.Cache_ca[0]="Cache2";
		panel4.table_1.getColumnModel().getColumn(0).setHeaderValue("cache3");
		panel4.Cache_ca[0]="Cache3";
		panel5.table_1.getColumnModel().getColumn(0).setHeaderValue("cache4");
		panel5.Cache_ca[0]="Cache4";
		
		
		//panel2.table_2.getColumnModel().getColumn(0).setHeaderValue("Memory1");
		//panel3.table_2.getColumnModel().getColumn(0).setHeaderValue("Memory2");
		//panel4.table_2.getColumnModel().getColumn(0).setHeaderValue("Memory3");
		//panel5.table_2.getColumnModel().getColumn(0).setHeaderValue("Memory4");
		
		for(int i=0;i<10;i++){
			//panel3.Mem_Content[i][0]=String.valueOf((Integer.parseInt(panel3.Mem_Content[i][0])+10));
			//panel4.Mem_Content[i][0]=String.valueOf((Integer.parseInt(panel3.Mem_Content[i][0])+20));
			//panel5.Mem_Content[i][0]=String.valueOf((Integer.parseInt(panel3.Mem_Content[i][0])+30));
		}
		/********设置头部panel*****/
		panel1.setBounds(10, 10, 1500, 100);
		panel1.setLayout(null);
		
		JLabel label1_1=new JLabel("执行方式:单步执行");
		label1_1.setFont(new Font("",1,20));
		label1_1.setBounds(15, 15, 200, 40);
		panel1.add(label1_1);
		

		
		//JComboBox<String> Mylistmodel1_1 = new JComboBox<>(new Mylistmodel());
		Mylistmodel1_1.setBounds(220, 15, 150, 40);
		Mylistmodel1_1.setFont(new Font("",1,20));
		panel1.add(Mylistmodel1_1);
		
		JButton button1_1=new JButton("复位");
		button1_1.setBounds(400, 15, 70, 40);
		
		/**********复位按钮事件（初始化）***********/
		button1_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				panel2.init();
				panel3.init();
				panel4.init();
				panel5.init();
				Mylistmodel1_1.setSelectedItem(null);
				
			}
		});
		
		/*panel2.Mem_Content[1][1]="11";*/
		panel1.add(button1_1);
		C1.add(panel1);
		myjf.setVisible(true);
		

		
	}

	
}

