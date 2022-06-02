#!/bin/bash
while read line
do
    isFlaky=$(echo $line | cut -d',' -f3)
    if [[ $isFlaky == 1 ]];then
        projName=$(echo $line | cut -d',' -f1)
        
        while read projLine
        do
            projName1=$(echo $projLine | cut -d',' -f1)
            projName2=$(sed 's/https:\/\/github.com\// /g' <<< "$projName1") # Replace github.com and hash
            projName3=$(sed 's/\//-/g' <<< "$projName2") # Replace github.com and hash

            #echo "$projName3 = $projName"
            echo "$projName" | xargs
            projName=$(echo "$projName" | xargs)
            projName3=$(echo "$projName3" | xargs)
#echo "04/Jun/2014:15:54:26" | sed 's/\//-/g'
            if [[ "$projName" == "$projName3"  ]]; then

               echo " proj3= $projName3"
               echo -n "$projLine," >> "flaky-test-by-FlakeFlagger.csv"
               echo $line | cut -d',' -f1,2  >> "flaky-test-by-FlakeFlagger.csv"
               break
            fi
        done < $2

#        echo $line | cut -d',' -f1,2  >> "flaky-test-by-FlakeFlagger.csv"
    fi


done < $1

