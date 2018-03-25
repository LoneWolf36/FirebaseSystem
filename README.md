# Water Release Information System


## Rules to be followed :
1. **Only** one person is allowed at a time to edit a file.
2. Before starting working, **communicate** changes going to be made, **only** that person works on that file then.
3. Right before starting run `git pull` ( If you didn't have any changes to be saved and you just want the latest project )
4. If you had made changes **but** want the latest version of the development, use `git commit -m "Your_message"` and then run `git pull`
5. Once **you're** ( **Only** one person allowed at a time on the same file ), run `git add file_name` ( no matter how many files ), add them one by one and only then run `git push`.
6. If you've made changes to multiple files ( without editing the same file another person was editing ) you're allowed to do `git add .`, then `git commit -m "My changes"`, and then `git push`

## ALWAYS MAKE SURE YOU'RE ON THE LATEST BUILD BEFORE STARTING.
## GitHub commands:
### To clone repository
	eg. `git clone https://github.com/LoneWolf36/WRIS-User.git`

### To add your changes to your push
	eg. `git add file_name` or `git add .`

### To commit your changes
	eg. `git commit -m "Your message"`

### To push your changes (i.e. apply your changes to the repository /  make permanent changes)
	eg. `git push`

### To pull new changes (i.e. get latest update from the repository)
	eg. `git pull` (If you had made changes **but** want the latest version of the development, use `git commit -m "Your_message"` and then run `git pull`)
