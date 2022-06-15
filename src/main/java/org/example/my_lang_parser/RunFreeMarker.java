package org.example.my_lang_parser;

import freemarker.core.UndefinedOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RunFreeMarker {

    private Configuration cfg;
    private Template template;

    public RunFreeMarker() {
        cfg = new Configuration(Configuration.VERSION_2_3_30);

        cfg.setDefaultEncoding("ASCII");
        cfg.setOutputFormat(UndefinedOutputFormat.INSTANCE);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        cfg.setClassForTemplateLoading(this.getClass(), "/");
        try {
            template = cfg.getTemplate("test.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void main(String[] args)
    {
        try {
            new RunFreeMarker().run();
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    void run() throws IOException {

        List<String> inputFiles = getInputFiles();
        for (var name: inputFiles) {
            var sourceCode = parseFileAndGenerateTest(name);
            writeTestFile(name, sourceCode);
        }
    }

    public List<String> getInputFiles() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("input_files");
        String path = url.getPath();

        var pathnames = new File(path).list();
        Arrays.sort(pathnames);
        var allFoundFiles = Arrays.asList(pathnames).stream().map(v -> v.replace(".in", "")).collect(Collectors.toList());
        System.err.println("Number of Files: " + allFoundFiles.size());
        return allFoundFiles;
    }
    String parseFileAndGenerateTest(String name) throws IOException {
        String source = readInputFile(name);

        MyLangListener listener = setupListener(source, name);
        var value = listener.getModel();

        StringWriter out = new StringWriter();
        try {
            template.process(value, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return out.toString();
    }

    void writeTestFile(String name, String outputSource) throws IOException {
        var fileName = String.format("out/%soutput.txt", name);
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(outputSource);
        fileWriter.close();
    }

    private String readInputFile(String name) throws IOException {
        System.err.println("Generate test for: " + name);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        var filePath = String.format("input_files/%s.in", name);
        InputStream inputStream = classloader.getResourceAsStream(filePath);

        String source = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return source;
    }

    private MyLangListener setupListener(String content, String className) {
        CharStream stream = CharStreams.fromString(content);
        MyLanguageLexer lexer = new MyLanguageLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MyLanguageParser parser = new MyLanguageParser(tokenStream);
        var tree = parser.myLanguageFile();

        MyLangListener listener = new MyLangListener(className);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        return listener;
    }
}
