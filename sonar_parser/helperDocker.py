import os
import subprocess
import time
import sys


def run_command(cmd):
    ret = subprocess.run(cmd, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, shell=True)


if len(sys.argv) == 3 and (sys.argv[2] == 'D' or sys.argv[2] == 'L'):

    command = 'mkdir /home/rhea/sonarqube && mkdir /home/rhea/sonarqube/output && mkdir resources'
    run_command(command)
    print("Directory created.\n")
    command = 'echo kali | sudo -S apt update;echo kali | sudo -S apt install -y docker.io;echo kali | sudo -S systemctl enable docker --now;echo kali | sudo -S docker load -i sonarParser.tar; echo kali | sudo -S docker stop postgres;echo kali | sudo -S docker stop sonarqube;echo kali | sudo -S docker rm postgres;echo kali | sudo -S docker rm sonarqube;echo kali | sudo -S docker stop sonarparser;echo kali | sudo -S docker rm sonarparser;echo kali | sudo -S docker network rm sonarqube_network;echo kali | sudo -S docker network create sonarqube_network;echo kali | sudo -S sysctl -w vm.max_map_count=524288;echo kali | sudo -S sysctl -w fs.file-max=131072;echo kali | sudo -S docker stop sonarparser; echo kali | sudo -S docker rm sonarparser;echo kali | sudo -S docker run -d --privileged -v /var/run/docker.sock:/var/run/docker.sock -v /home/rhea/sonarqube/output:/home/rhea/sonarqube/output --name sonarparser --network sonarqube_network sonarparser ' + \
              sys.argv[1] + ' ' + sys.argv[2] + ';'
    run_command(command)
    print("Updated system, loaded the sonarparser image and running sonarparser image\n")

    file_found = 0

    while True:
        time.sleep(60)
        dircontent = os.listdir("/home/rhea/sonarqube/output")
        print("looking for xml file in output directory...\n");
        for x in dircontent:
            if (x[-4:] == ".xml"):
                time.sleep(20)
                command = 'echo kali | sudo -S mv /home/rhea/sonarqube/output/' + x + ' /home/rhea/Desktop/Final\ Version/resources/XMLData.xml'
                run_command(command)
                print("Moving xml data to resources directory..\n")
                file_found = 1
                break
        if (file_found == 1):
            break

    time.sleep(15)
    command = 'echo kali | sudo -S docker load -i backend.tar; echo kali | sudo -S docker load -i frontend.tar; echo kali | sudo docker run --name backend -v /home/rhea/Desktop/Final\ Version/resources/:/opt/resources -d backend;'
    run_command(command)
    print("Loaded the backend and frontend images and running backend image \n")

    file_found = 0

    while True:
        time.sleep(30)
        dircontent = os.listdir("/home/rhea/Desktop/Final Version/resources")
        print("looking for txt file in resources directory...\n");
        for x in dircontent:
            if (x[-4:] == ".txt"):
                time.sleep(20)
                print("Txt file found\n")
                file_found = 1
                break
        if (file_found == 1):
            break

    command = 'echo kali | sudo docker run --name frontend -v /home/rhea/Desktop/Final\ Version/resources/:/opt/resources -p 8080:8080 -d frontend;'
    run_command(command)
    time.sleep(20)
    print("Running the frontend image \n")

    command = 'firefox http://localhost:8080;'
    run_command(command)
    print("Launching Firefox at localhoot:8080\n")

    command = 'echo kali | sudo docker stop sonarparser; echo kali | sudo docker rm sonarparser; echo kali | sudo docker stop backend; echo kali | sudo docker rm backend; echo kali | sudo docker stop frontend; echo kali | sudo docker rm frontend;'
    run_command(command)
    print("Cleaning up images \n")

    command = 'echo kali | sudo rm -r resources && echo kali | sudo rm -r /home/rhea/sonarqube'
    run_command(command)
    print("Removing directories \n")


else:
    print(
        "usage python3 dockerRunner.py <link-to-git-repo.git> \n --> The Save Mode option specifies where the project files are deleted after execution or if they are stored. \n --> Use save mode L if you are running JAR locally.\n --> Use save mode D if running in a dockerized instance to save space.")