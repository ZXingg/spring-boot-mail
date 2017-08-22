package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import javax.security.auth.login.Configuration;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDemoSendMailApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String Sender;


	//简单邮件
	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Sender);
		message.setTo(Sender);
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}


	//HTML邮件
	@Test
	public void sendHtmlMail() {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			helper.setTo(Sender);
			helper.setSubject("主题：HTML邮件");

			StringBuilder sb = new StringBuilder();
			sb.append("<h1>大标题-h1</h1>")
					.append("<p style='color:#F00'>红色字</p>")
					.append("<p style='text-align:right'>右对齐</p>");
			helper.setText(sb.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailSender.send(message);
	}

    //带附件的邮件
	@Test
	public void sendAttachmentsMail() {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			helper.setTo(Sender);
			helper.setSubject("主题：带附件的邮件");
			helper.setText("带附件的邮件内容");
			//注意项目路径问题，自动补用项目路径
			FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
			//加入邮件
			helper.addAttachment("图片.jpg", file);
		} catch (Exception e){
			e.printStackTrace();
		}
		mailSender.send(message);
	}


	//带静态资源的邮件
	@Test
	public void sendInlineMail() {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			helper.setTo(Sender);
			helper.setSubject("主题：带静态资源的邮件");
			//第二个参数指定发送的是HTML格式,同时cid:是固定的写法
			helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);

			FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
			helper.addInline("picture",file);
		} catch (Exception e){
			e.printStackTrace();
		}
		mailSender.send(message);
	}

//    //模板邮件
//	@Autowired
//	private FreeMarkerConfigurer freeMarkerConfigurer;  //自动注入
//
//	@Test
//	public void sendTemplateMail(){
//		MimeMessage message = null;
//		try {
//			message = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			helper.setFrom(Sender);
//			helper.setTo(Sender);
//			helper.setSubject("主题：模板邮件");
//
//			Map<String, Object> model = new HashMap();
//			model.put("username", "zggdczfr");
//
//			//修改 application.properties 文件中的读取路径
////            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
////            configurer.setTemplateLoaderPath("classpath:templates");
//			//读取 html 模板
//			RabbitProperties.Template template = freeMarkerConfigurer.getConfiguration().getTemplet("mail.html");
//			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//			helper.setText(html, true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mailSender.send(message);
//	}


//    @Autowired
//    private FreeMarkerConfig freeMarkerConfig;
//
//    @Test
//    public void sendTemplateMail(){
//        MimeMessage message = null;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(Sender);
//            helper.setTo(Sender);
//            helper.setSubject("主题：模板邮件");
//
//            Map<String, Object> model = new HashMap();
//            model.put("username", "zggdczfr");
//
//            Configuration cfg= new Configuration(Configuration.VERSION_2_3_23);
//
//            // 设定去哪里读取相应的ftl模板
//
//            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
//
//            // 在模板文件目录中寻找名称为name的模板文件
//
//            RabbitProperties.Template template   = cfg.getTemplate("email.ftl");
//
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
