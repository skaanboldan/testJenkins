package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ExcelController.ExcelGenerator2;
import org.example.MailController.GmailSender;
import org.example.ZabbixController.ReportController;
import org.example.ZabbixController.ZabbixReport;
import org.json.JSONArray;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Hello world!");

        String messageBody="test";

        System.out.println("test başlıyo...");
        logger.info("test başlıyo...");
        try {
            String  address = InetAddress.getByName("www.google.com").getHostAddress();
            InetAddress inet = InetAddress.getByName(address);
            System.out.println("Sending Ping Request to " + address);
            if(inet.isReachable(50000)){
                System.out.println("Host is reachable");
                logger.info("Host is reachable");

            }
            else{
                System.out.println("Host is not reachable");
                logger.info("Host is not reachable");

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Host is not reachable but exception");

        }

        try {


            System.out.println("Consumed message:" +messageBody);
            ReportController reportController=new ReportController();
            ExcelGenerator2 excelGenerator2=new ExcelGenerator2();
            ArrayList<ZabbixReport> zabbixReport= reportController.listProblems();
            JSONArray jsArray = new JSONArray(zabbixReport);


            assert zabbixReport != null;
            //TelegramSender.sendToTelegram(String.valueOf(jsArray),excelGenerator2.createExcel());
            System.out.println("Mail Gönderilecek");
            GmailSender.sendEmail("kaan.boldan@gizdanismanlik.com.tr","Zabbix Report",String.valueOf(jsArray),excelGenerator2.createExcel(zabbixReport),"Zabbix Report.xlsx");
            System.out.println("Mail gönderme aşaması tamamlandı");

            //RabbitMQBody rabbitMQBody= parseRabbitMQBodyJson(messageBody);
            //insertDb(rabbitMQBody);
        }catch (Exception e){
            System.out.println("hata ile karşılaştı.."+ e.getMessage());
            e.printStackTrace();
        }
    }
}