package com.example.voice.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author jianhui.Yang
 * @version $Id IpTest.java, v 0.1 2018-11-07 15:33 jianhui.Yang Exp $$
 */
public class GetIpTest {

    public static void main(String[] args) {
        GetIpTest getIpTest = new GetIpTest();
        getIpTest.getIP();
        getIpTest.getMemory();
    }

    private void getIP(){
        try {
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            String hostName = address.getHostName();
            System.out.println("本机IP: "+ ip);
            System.out.println("本机名称: "+ hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    private void getMemory(){
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        long totalMemorySize = memoryUsage.getInit(); //初始总内存

        long maxMemorySize = memoryUsage.getMax(); //最大可用内存

        long usedMemorySize = memoryUsage.getUsed(); //已使用内存

        System.out.println("初始总内存 "+totalMemorySize/(1024*1024)+"M");

        System.out.println("剩余 内存 " + (totalMemorySize - usedMemorySize)/ (1024 * 1024) +  "M" );

        System.out.println("最大可用内存 " + maxMemorySize/ (1024 * 1024) +  "M" );

        System.out.println("已使用 " + usedMemorySize/ (1024 * 1024) +  "M" );
    }

}
