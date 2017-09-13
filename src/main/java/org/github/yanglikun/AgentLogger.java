package org.github.yanglikun;

import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AgentLogger {
    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(
                (loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
                    try {
                        Path path = Paths.get("classes-agent/" + className + ".class");
                        Path parentDir = path.getParent();
                        if (!Files.exists(parentDir))
                            Files.createDirectories(parentDir);
                        Files.write(path, classfileBuffer);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    } finally {
                        return classfileBuffer;
                    }
                }
        );
    }
}