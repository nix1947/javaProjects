## Compile all the .java files

cd server
  javac *.java 

cd client
  javac *.java


## Run java rmi registry 
 
- Run the Rmiregisty in specified port. 
- `rmiregistry 9400 &` // this run the rmiregistry in background 


- Step2: Start the server 
    `java Server` 


- Step 3: Start the client
    `java Client`

