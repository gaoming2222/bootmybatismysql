package com.springboot.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Service;


@Service
public class SendMailService {
	//下面文件可以放进配置文件
	private String from = "m13212710080@163.com"; // 发件人邮箱地址  
    private String user = "m13212710080@163.com"; // 发件人称号，同邮箱地址  
    private String password = "cjw417"; // 发件人邮箱客户端授权码  
    
    /** 
     * @author codergaoming
     
     * @param to  接收邮件人邮箱
     * @param text  邮件内容
     * @param title 邮件标题
     * @param file 附件
     * @param is  附件（输入流的形式）
     * @throws IOException 
     */  
    /* 发送验证信息的邮件 */
    public boolean sendMail(String to, String text, String title,File file,List<Map<String,Object>> isMapList) throws IOException {  
        Properties props = new Properties();  
        props.setProperty("mail.smtp.host", "smtp.163.com"); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）  
        props.put("mail.smtp.host", "smtp.163.com"); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）  
        props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session  
        Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使  
                                                                // 用（你可以在控制台（console)上看到发送邮件的过程）  
        session.setDebug(true); // 用session为参数定义消息对象  
        MimeMessage message = new MimeMessage(session); // 加载发件人地址  
        try {  
            message.setFrom(new InternetAddress(from));  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 加载收件人地址  
            //message.addRecipients(MimeMessage.RecipientType.TO, arg1); //用于添加多个收件人
            message.setSubject(title); // 加载标题  
            Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件  
            BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容  
            contentPart.setContent(text, "text/html;charset=utf-8");  
            multipart.addBodyPart(contentPart);
            
            
            //通过流的方式添加附件
            if(isMapList != null && isMapList.size() > 0){
            	for(int i = 0;i<isMapList.size();i++){
                	Map<String,Object> isMap = new HashMap<>();
                	isMap = isMapList.get(i);
                	MimeBodyPart fileBody = new MimeBodyPart();  
                    DataSource source = new ByteArrayDataSource((InputStream)isMap.get("FILE"), "application/msexcel"); 
                    fileBody.setDataHandler(new DataHandler(source));  
                    String fileName  = (String)isMap.get("FILE_NAME");
                    // 中文乱码问题
                    fileBody.setFileName(MimeUtility.encodeText(fileName));  
                    multipart.addBodyPart(fileBody);  
                }
            }
            
//            String fname = file.getName();  
//                //创建FileDAtaSource(用于添加附件)  
//            FileDataSource fds = new FileDataSource(file);  
//            BodyPart fileBodyPart = new MimeBodyPart();
//            fileBodyPart.setDataHandler(new DataHandler(fds));  
//                // 设置附件文件名  
//            fileBodyPart.setFileName(fname);  
//            multipart.addBodyPart(fileBodyPart);  
            
            message.setContent(multipart);
            message.saveChanges(); // 保存变化  
            Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱  
            transport.connect("smtp.163.com", user, password); // 把邮件发送出去  
            transport.sendMessage(message, message.getAllRecipients());  
            transport.close();  
        } catch (MessagingException e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }
}
