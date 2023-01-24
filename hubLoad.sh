#!/bin/sh


git remote remove origin
git init
git add -A
git commit -m "Auto Commit"
git remote add origin git@github.com:FaresGitProjects/"$PWD".git
git branch -M main
git push -u origin main

