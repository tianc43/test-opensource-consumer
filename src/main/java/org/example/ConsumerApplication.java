package org.example;


import cn.edu.zju.common.Response;
import cn.edu.zju.service.GithubService;
import cn.edu.zju.service.ProjectService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@SpringBootApplication
@Service
@EnableDubbo
public class ConsumerApplication {

    @DubboReference
    private GithubService demoService;
    @DubboReference
    private ProjectService projectService;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        ConsumerApplication application = context.getBean(ConsumerApplication.class);
        String result = application.doSayHello("world");
        System.out.println("result: " + result);
        System.out.println("==========");
        String url = "http://github.com/microsoft/vscode";
        Map<String,Object> projectData = (Map<String, Object>) application.doFetchProjectData(url).getData();
        System.out.println(projectData.toString());
    }
    public Response doFetchProjectData(String url){
        return projectService.fetchProjectData(url);
    }
    public String doSayHello(String name) {
        return demoService.fetchRepo(name);
    }
}
