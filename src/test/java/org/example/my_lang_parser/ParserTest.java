package org.example.my_lang_parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ParserTest {

    @Test
    void testSimpleFunction() {

        String content = "Int fooBar(){bla}";
        MyLangListener listener = setupListener(content);

        assertThat(listener.getFunctions().stream().map(func -> func.getFunctionName()).collect(Collectors.toList()), equalTo(Arrays.asList("fooBar")));
    }

    @Test
    public void testComment() {

        String content = "//bla\n" +
            "Int fooBar(){bla}";
        MyLangListener listener = setupListener(content);

        final var expected = Arrays.asList("fooBar");
        assertThat(listener.getFunctions().stream().map(func -> func.getFunctionName()).collect(Collectors.toList()), equalTo(expected));
    }

    @Test
    public void testTwoFunctions() {

        String content = "Int fooBaz(){bli}\n" +
                "Int fooBar(){bla}";
        MyLangListener listener = setupListener(content);

        assertThat(listener.getFunctions().stream().map(func -> func.getFunctionName()).collect(Collectors.toList()), equalTo(Arrays.asList("fooBaz", "fooBar")));
    }

    @Test
    public void testConstArg() {

        String content = "Int fooBaz(const int bla){bli}";
        MyLangListener listener = setupListener(content);
        var fun = listener.getFunctions().get(0);

        assertThat(fun.getFunctionName(), equalTo("fooBaz"));

        assertThat(fun.arguments.get(0).getName(), equalTo("bla"));
        assertThat(fun.arguments.get(0).getType(), equalTo("int"));
        assertThat(fun.arguments.get(0).getStyle(), equalTo(FunctionArgument.ParamStyle.IN));
    }

    private MyLangListener setupListener(String content) {
        CharStream stream = CharStreams.fromString(content);
        MyLanguageLexer lexer = new MyLanguageLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MyLanguageParser parser = new MyLanguageParser(tokenStream);
        var tree = parser.myLanguageFile();

        MyLangListener listener = new MyLangListener("Example");
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        return listener;
    }
}
