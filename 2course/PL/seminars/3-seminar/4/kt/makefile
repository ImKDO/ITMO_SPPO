PATH_SERVER = ~/httpd-root/fcgi-bin
PORT = 2222
user = s408891
addr = se.ifmo.ru
FLAGS = -P

all: compile cd_to_file send cd_to_root

compile:
	cd ./build/libs/
cd_to_file:
	cd ./build/libs/

send:
	 		gradle build ; cd ./build/libs/ ; scp $(FLAGS)$(PORT) ./*.jar $(user)@$(addr):$(PATH_SERVER)
cd_to_root:
	cd ../..
