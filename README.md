
# LoopGuard

LoopGuard is an application designed to ensure your application remains running by automatically restarting it every 600 seconds (10 minutes). This is particularly useful for ensuring high availability and reliability of critical applications.
## Customize

To change a command to run you need to change `COMMAND` variable. To change a time window change `LIFETIME_SEC` variable.


## How to Compile

To compile LoopGuard, you need to have [Kotlin](https://kotlinlang.org/docs/command-line.html) compiler installed. Use the following command to compile the source code into a runnable JAR file:

```bash
kotlinc main.kt -include-runtime -d main.jar
```

## How to Run

After compiling, you can run LoopGuard using the Java Runtime Environment:

```bash
java -jar main.jar
```

## Using PM2 for Additional Reliability

PM2 is a process manager for Node.js applications, but it can also be used to manage any executable. To use PM2 with LoopGuard, ensure PM2 is installed and then start LoopGuard with the following command:

```bash
pm2 start "java -jar main.jar"
```

This will allow PM2 to automatically restart LoopGuard if it ever crashes, providing an extra layer of stability for your applications.

