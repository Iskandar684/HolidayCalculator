BIRT_JAR="./src/main/resources/standalone/deployments/holiday.ear/lib/org.eclipse.birt.runtime.jar"

deleteRSA(){
 if unzip -l $BIRT_JAR | grep -q "ECLIPSE_.RSA"
  then
    echo "deleting RSA";
    zip -d $BIRT_JAR 'META-INF/*.RSA';
  else
   echo "don't have RSA";
  fi
}

deleteSF(){
 if unzip -l $BIRT_JAR | grep -q "ECLIPSE_.SF"
  then
    echo "deleting SF";
    zip -d $BIRT_JAR 'META-INF/*.SF';
  else
   echo "don't have SF";
  fi
}

deleteRSA
deleteSF
