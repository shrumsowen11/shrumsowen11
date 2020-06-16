# shrumsowen11


******************************* Github Repository Operations ****************************************

First create a new repository in your GitHub using the GitHub Web Interface 


Checking which files in the directory are staged/tracked or not

                git status

After staging, we add files which are staged

                git add <fileNames>		->> just for that filename or
                git add .			->> just for all new files

After adding, we commit those added files on the command line

                git commit -m "first commit"

In order to make sure the committed files are there on the command line

                 git log

Upto to here, it has only affected the local machine, in order to get the repo up to date, we do the git push

                git push origin master

This might ask for your password




 
Reinitializing the directory in GitHub/Create a new repository on the command line

                echo "# shrumsowen11" >> README.md
                git init
                git add README.md
                git commit -m "first commit"

                git remote add origin https://github.com/shrumsowen11/shrumsowen11.git
		                            OR in order to set the origin first
                git remote set-url origin https://github.com/your/repository
	                              (ERROR: fatal: remote origin already exists.)


                git push -u origin master


What exactly happens when you are trying to push and this error is there?

! [rejected]        master -> master (fetch first)
error: failed to push some refs to 'https://github.com/shrumsowen11/testing.git'
->> When trying to push and this error show up, this means you or someone has edited the repository in the website after your last edits and you might have forgotten about that. When this happens, do not do the git push –force, this might give troubles in the future. So, do this
                git fetch origin master
                git pull origin master		                  and then 
                git push

What exactly happens when you do this?
                git remote add origin https://github.com/shrumsowen11/shrumsowen11.git
->> Using in the cmd prompt, this will actually create the “origin”[Your GitHub Original Primary URL] as https://github.com/shrumsowen11/shrumsowen11.git link and the “git remote add” means, you are trying to add the committed files from your local repository(.git) in your computer to your remote GitHub repository(whichever is made there), for this case “shrumsowen11”.

If you would like to change/Set the “origin” into a different link(URL)
                git remote set-url origin https://github.com/your/repository


Push an existing repository from the command line
                git remote add origin https://github.com/shrumsowen11/shrumsowen11.git
                git push -u origin master

Create a new file on the command line
                touch newFile.md


***********************************************************************************************

Restoring all the files in the directory which were pulled from an external git repository:

                git restore . 

ERROR:
From https://github.com/JavaHunk2020/cubic-bank
   38e6738..399f3a9  Feature-NK/TK-2929-Change-Pass -> origin/Feature-NK/TK-2929-Change-Pass
error: Your local changes to the following files would be overwritten by merge:
        banking-web/src/main/java/com/rab3tech/customer/ui/controller/CustomerUIController.java
        banking-web/src/main/resources/application.properties
Please commit your changes or stash them before you merge.
Aborting
Updating 3407a9a..399f3a9

***********************************************************************************************
