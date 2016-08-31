package com.inter.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

import com.inter.common.Constants;
import com.inter.service.ServerConnect;
import com.inter.util.DateUtil;

public class UITestMain implements ActionListener{
	
	private JTextField appkeyText;
	private JTextField lastTimeText;
	private JTextField interTypeText;
	private JTextField signText;
	private JTextArea area;
	
	private ServerConnect sconnect = new ServerConnect();
	
	private void createAndShowGUI() {    
        JFrame f = new JFrame("测试数据");
        f.getContentPane().setLayout(new BorderLayout());
        
        JPanel norJP = new JPanel();
        norJP.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        initNorth(norJP);
        
        JPanel centerJP = new JPanel();
        initCenter(centerJP, f);
        
        initProp();
        
        f.add(norJP, BorderLayout.NORTH);
        f.add(centerJP, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.validate();
        f.pack();
        f.setVisible(true);
    }
	
	public void initCenter(JPanel centerJP, JFrame jf) {
		area = new JTextArea(30 ,60);
		area.setEditable(false);
		area.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centerJP.add(jsp);
	}
	
	public void initNorth(JPanel norJP) {
		JPanel appkyeJP = new JPanel();
		JLabel appkeyLable = new JLabel("appKey");
		appkyeJP.add(appkeyLable);
        appkeyText = new JTextField(25);
        appkyeJP.add(appkeyText);
        norJP.add(appkyeJP);
        
        JPanel lastTimeJP = new JPanel();
        JLabel lastTimeLable = new JLabel("上次时间(yyyy-MM-dd HH:mm:ss)");
        lastTimeJP.add(lastTimeLable);
        lastTimeText = new JTextField(15);
        lastTimeJP.add(lastTimeText);
        norJP.add(lastTimeJP);
        
        JPanel signJP = new JPanel();
        JLabel signLable = new JLabel("摘要");
        signJP.add(signLable);
        signText = new JTextField(25);
        signJP.add(signText);
        norJP.add(signJP);
        
        JPanel interTypeJP = new JPanel();
        JLabel interTypeLable = new JLabel("接口类型(1 | 2 | 3)");
        interTypeJP.add(interTypeLable);
        interTypeText = new JTextField(4);
        interTypeJP.add(interTypeText);
        norJP.add(interTypeJP);
        
        JButton connServerJB = new JButton("连接服务器");
        connServerJB.addActionListener(this);
        norJP.add(connServerJB);
	}
	
	public void initProp() {
		Map<String, String> params = sconnect.getRequestParams(Constants.filename, false, Constants.TimeType.TIME.getValue());
		appkeyText.setText(params.get("appKey"));
        signText.setText(params.get("sign"));
        lastTimeText.setText(params.get("lastTimestamp"));
        interTypeText.setText(params.get("interfaceType"));
	}
    
    public static void main(String args[]) {
        Runnable doCreateAndShowGUI = new Runnable() {
            public void run() {
            	new UITestMain().createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		area.setText("");
		String appkey = appkeyText.getText().trim();
		String lastTime = lastTimeText.getText().trim();
		String interType = interTypeText.getText().trim();
		String sign = signText.getText().trim();
		if(StringUtils.isNotBlank(appkey) || StringUtils.isNotBlank(lastTime) 
				|| StringUtils.isNotBlank(interType)
				|| StringUtils.isNotBlank(sign)) {
			Map<String, String> params = new HashMap<String, String>();
			try {
				params.put("appKey", appkey);
				params.put("lastTimestamp", String.valueOf(DateUtil.parse(lastTime).getTime()/1000));
				params.put("interfaceType", interType);
				params.put("sign", sign);
				String result = sconnect.getPostRequestResult(Constants.url, params);
				area.setText(result);
			} catch (Exception e) {
				area.setText(e.getMessage());
			}
		} else {
			area.setText("请输入内容");
		}
	}

}
