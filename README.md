[![Build Status](https://travis-ci.org/Arugal/github-explore.svg?branch=master)](https://travis-ci.org/Arugal/github-explore)

## github-explore
   通过爬虫定时爬取 github 的 trending 内容并储存,加上历史排名信息后通过页面重新展示

## 模块说明

   - explore-core
   
        定义相关实体类
   - explore-dao
   
        定义相关 dao 类，实现相关 service 类，对 trending 查询结果，语言、时间常量使用了堆内缓存 (spring mybatis)
   - explore-rest-api
        
        相关 rest 风格接口 (spring boot、spring mvc)
   - explore-web
        
        pc 端 web 界面（vue、element-ui）
   - explore-spider
    
        爬虫实现 (spring boot、gecco)
        
## 快速启动
   
   默认以下载相应代码
   
   - mysql
        
        创建 [mysql](mysql) 实例，执行 sql/db.sql 脚本 （后期索引有待优化
   - explore-spider
        
        修改 explore-spider/src/main/resources/application.yml 文件，根据实际情况修改数据库参数
        
        通过 docker 部署时使用 mvn -DskipTests=true install -Pspider-deploy 命令编译项目
   - explore-rest-api
        
        修改 explore-rest-api/src/main/resources/application.yml 文件，根据实际情况修改 servlet 容器监听端口和数据库参数
        
        通过 docker 部署时使用 mvn -DskipTests=true install -Pspider-deploy 命令编译项目
   - explore-web

        修改 explore-web/src/common/js/axiosi.js 文件，根据实际情况修改 rest-api 地址
    
   （后期提供适用的 docker-compose、Makefile、Shell，目前且有 Dockerfile）
   
## 样例
[~~体验地址~~](https://www.sunnus3.top)目前无法使用 frp 反向代理部署在局域网内的接口
![demo](doc/page.png)
