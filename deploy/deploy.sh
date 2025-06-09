#!/bin/bash
source ./var.sh
echo start
touch crontab_delete
crontab crontab_delete
rm crontab_delete
echo "cron delete"
if [ -n "${PROJECT_PID}" ];
        then
                echo kill -9 ${PROJECT_PID}
                kill -9 ${PROEJCT_PID}
                sleep 5
        fi
echo git pulling
cd /home/${PROJECT_NAME}/CampingService
git pull origin
sleep 3s
sudo chmod u+x /home/${PROJECT_NAME}/CampingService/gradlew
cd /home/${PROJECT_NAME}/CampingService
./gradlew build
nohup java -jar -Dspring.profiles.active=prod ${JAR_PATH} 1>/home/CampingService/CampingService/log.out 2>/home/CampingService/CampingService/err.out &
touch crontab_new
echo "* *  */home/${PROJECT_NAME}/CampingService/check-and-restart.sh" 1>>crontab_new
crontab crontab_new
rm crontab_new
echo "cron registration complete"
