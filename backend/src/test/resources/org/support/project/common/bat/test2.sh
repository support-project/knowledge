#!/bin/sh
echo "start test.sh"

a=0
while [ $a -ne 1000 ]
do
#	echo "--> commad"
	printenv > out.txt
#	echo "--> commad"
	ifconfig > out.txt
	a=`expr $a + 1`
#	echo "COUNT: ${a} "
done

rm out.txt

echo "finish test.sh"








