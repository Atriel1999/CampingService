source ./var.sh
if [ -z ${PROJECT_PID} ]; then
        nohup java -jar -Dspring.profiles.active=prod ${JAR_PATH} 1>log.out 2>err.out &
        echo "스프링부트 재실행 완료"
fi
