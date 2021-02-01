# shrumsowen11
************************* CLICK ON RAW OR BLAME IN THE TAB IN THE RIGHT TOP OF THIS FORM ******************************

**************************************** Github Repository Operations **************************************

First create a new repository in your GitHub using the GitHub Web Interface 


Checking which files in the directory are staged/tracked or not

                git status

After staging, we add files which are staged

                git add <fileNames>		->> just for that filename or
                git add .			->> just for all new files

After adding, we commit those added files on the command line

                git commit -m "first commit"
After commiting, if you want to unstage or uncommit:

		git reset
In order to make sure the committed files are there on the command line

                 git log

Upto to here, it has only affected the local machine, in order to get the repo up to date, we do the git push

                git push origin master

This might ask for your password



***********************************************************************************************************************
 
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


***********************************************************************************************************************

What exactly happens when you are trying to push and this error is there?

! [rejected]        master -> master (fetch first)
error: failed to push some refs to 'https://github.com/shrumsowen11/testing.git'
->> When trying to push and this error show up, this means you or someone has edited the repository in the website after your last edits and you might have forgotten about that. When this happens, do not do the git push –force, this might give troubles in the future. So, do this

                git fetch origin master
                git pull origin master		                  and then 
                git push

***********************************************************************************************************************

What exactly happens when you do this?

                git remote add origin https://github.com/shrumsowen11/shrumsowen11.git
		
->> Using in the cmd prompt, this will actually create the “origin”[Your GitHub Original Primary URL] as https://github.com/shrumsowen11/shrumsowen11.git link and the 

		git remote add
		
means, you are trying to add the committed files from your local repository(.git) in your computer to your remote GitHub repository(whichever is made there), for this case “shrumsowen11”.

***********************************************************************************************************************

If you would like to change/Set the “origin” into a different link(URL)

                git remote set-url origin https://github.com/your/repository

Push an existing repository from the command line

                git remote add origin https://github.com/shrumsowen11/shrumsowen11.git
                git push -u origin master
***********************************************************************************************************************
Create a new file on the command line

                touch newFile.md
***********************************************************************************************************************
Create a new file TO EDIT on the command line
		
		vim FILENAME
For saving and closing this:
		
		!qw  or wq!  --> this is write quit !-> force
***********************************************************************************************************************
In order to merge the edits from your local(from your branch) to the master(remote):

		git branch   --> shows all the branch in the github
		git pull     --> shows the details, also the hidden branches which are just made
->> Most of the time, you will want to merge a branch with the current active branch and you can do so by passing in the branch name.
->> Goto master branch
		
		git checkout master
->> now, either
	
		git merge master <branchName>       or        git merge master
***********************************************************************************************************************
Deleting a branch via command line:
		
		git branch -d <branchName>
		
***********************************************************************************************************************
Creating a new branch via command line:

		git checkout -b <branch>
--> Simply, we can goto our github account and start writing the branch name in the project repo where we want a new branch

********************************************************************************************************************************************************************************************************************************************************************************
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

********************************************************************************************************************************************************************************************************************************************************************************

Renaming the current branch:

	git branch -M <new_name>


how do I clone from the a specific branch in github;
Cloning a specific branch in github

	git clone --branch <branchname> --single-branch <remote-repo-url>
or

	git clone -b <branchname> --single-branch <remote-repo-url>
For example: if   
branchname → passwordless-auth; 
remote-repo-url → https://github.com/shrumsowen11/shrumsowen11.git
	
	git clone -b passwordless-auth --single-branch https://github.com/shrumsowen11/shrumsowen11.git
	
