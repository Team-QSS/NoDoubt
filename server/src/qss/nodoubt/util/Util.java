package qss.nodoubt.util;


import javax.swing.JTextArea;

public class Util {
	//모든 서버상의 로그는 이함수들을 이용하여 출력
	public static void printLog(JTextArea mainTextArea,Object content){
		mainTextArea.append(content.toString()+"\n");
		System.out.println(content);
		mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
	}
	
}
