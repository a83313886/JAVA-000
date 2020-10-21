package lesson1;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassloader extends ClassLoader {

    private byte[] loadClassContent() throws Exception {
        return Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("lesson1/Hello.xlass").toURI()));
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("lesson1/Hello.xlass");
        byte[] originalClassBytes;
        byte[] resultClassBytes;
        try {
            originalClassBytes = this.loadClassContent();
            if (originalClassBytes != null && originalClassBytes.length > 0) {
                resultClassBytes = new byte[originalClassBytes.length];
                for (int i = 0; i < originalClassBytes.length; i++) {
                    resultClassBytes[i] = (byte) (255 - originalClassBytes[i]);
                }
                return super.defineClass(name, resultClassBytes, 0, resultClassBytes.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static void main(String[] args) {
        try {
            Class<?> helloClass = new HelloClassloader().loadClass("Hello");
            Object helloObj = helloClass.newInstance();
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(helloObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
