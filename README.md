# ser516-project2-groupC

Google Drive Link: https://drive.google.com/drive/folders/0ACQby-ZThfNmUk9PVA

Steps on How to Run the Project locally

FrontEnd

Install Node in your PC (recommended version 8)

To run the application (open terminal):

1. Go Inside FE dir

2. Inside FE dir you can see, you can see a "team-c" dir. Go inside this dir.

3. You should be able to see package.json

4. Type in your terminal : npm i (This will install all the node modules required for the project)

5. Then type npm start to start the FE server

Backend

To run the application (open terminal):

1. Go inside the BE dir

2. Go inside micro-service dir (like taiga or BDConsistency).

3. Make sute to have maven & java (openjdk 17.0.2) install in your machine. To install latest maven in mac use command : "brew install maven".

4. You should see pom.xml file. Type "mvn clean install" to compile the project

5. To start backend server to you type "mvn sprirng-boot:run"

To start the Docker Files
Goto dir DEVOPS/automation and run the script: python3 deployment_automation.py
