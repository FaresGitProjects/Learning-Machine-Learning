#!/bin/sh


git remote remove origin
git init
git add -A
git commit -m "Auto Commit"
git remote add origin $1
git branch -M main
git push -u origin main

