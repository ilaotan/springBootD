#!/bin/sh

##########################
# 拷贝本脚本到服务器 执行下面命令
#
# chmod +x springboot.sh  && cp springboot.sh /usr/sbin/  && ln -s /usr/sbin/springboot.sh /usr/sbin/springboot
#
# 然后就可以 springboot +jar名字 启动程序了
#
# witparking.technology@2016
##########################

#显示所有行
#set -x

#打印异常
set -e

jarName=$1

nohup java -jar $jarName  > /dev/null 2>&1 &

currPs=`ps -ef|grep ${jarName}|grep 'java -jar'`
echo $currPs
echo "-----------------------华丽的分隔符-----------------------"
echo "执行成功 请查看日志输出并ps一下进程,以确保正常启动     命令:        ps -ef|grep $jarName|grep java "
