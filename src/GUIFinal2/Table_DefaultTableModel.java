package GUIFinal2;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Vector;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 
/**
 * DefaultTableModel创建表格模型，并能够实现增加和删除行
 * 
 * @author Administrator
 * 
 */
public class Table_DefaultTableModel extends JFrame implements ActionListener {
	Object[][] data = { { "王鹏", "001", "69", "87" },
			{ "李斯", "002", "69", "87" }, { "全脂", "003", "69", "87" },
			{ "陆远", "004", "69", "87" } };
	Object[] head = { "姓名", "学号", "科目1", "科目2" };
	// 创建表模型对象
	DefaultTableModel dtm = new DefaultTableModel(data, head);
	// 创建表格对象
	JTable jt = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jt);
	JPanel jp = new JPanel();
	JButton jbAdd = new JButton("增加行");
	JButton jbDelete = new JButton("删除行");
 
	public Table_DefaultTableModel() {
		// 设置列表头不可被用户重新拖动排列
		jt.getTableHeader().setReorderingAllowed(false);
		// 添加监听器
		jbAdd.addActionListener(this);
		jbDelete.addActionListener(this);
		jp.add(jbAdd);
		jp.add(jbDelete);
		this.add(jsp, BorderLayout.CENTER);
		this.add(jp, BorderLayout.SOUTH);
		// 设置窗体属性
		this.pack();
		this.setBounds(10, 100, 400, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
 
 
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbAdd) {
			dtm.addRow(new Vector());
		} else if (e.getSource() == jbDelete) {
			int row = dtm.getRowCount()-1;
			if(row>=0) {
				dtm.removeRow(row);
				dtm.setRowCount(row);
			}
		}
//		表格更新
		jt.revalidate();
	}
 
	public static void main(String[] args) {
		new Table_DefaultTableModel();
	}
 
 
}

