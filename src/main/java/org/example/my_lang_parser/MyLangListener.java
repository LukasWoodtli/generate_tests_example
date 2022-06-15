package org.example.my_lang_parser;

import org.antlr.v4.runtime.misc.Interval;

import org.antlr.v4.runtime.*;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class MyLangListener extends org.example.my_lang_parser.MyLanguageBaseListener {

    private final String name;
    private String lastReturnType = "";
    private ArrayList<MyLangFunction> functions = new ArrayList<>();

    public MyLangListener(String className) {
        this.name = className;
    }

    @Override
    public void enterReturnType(org.example.my_lang_parser.MyLanguageParser.ReturnTypeContext ctx) {
        var returnType = getSourceText(ctx);
        this.lastReturnType = returnType;
    }

    @Override
    public void enterFunctionName(org.example.my_lang_parser.MyLanguageParser.FunctionNameContext ctx) {
        var functionName = getSourceText(ctx);
        this.functions.add(new MyLangFunction(this.lastReturnType, functionName, name));
    }


    @Override
    public void exitArgAsVal(org.example.my_lang_parser.MyLanguageParser.ArgAsValContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx), FunctionArgument.ParamStyle.IN, FunctionArgument.ReferenceType.VALUE, getSourceText(nameCtx)));
    }

    @Override
    public void exitArgAsConstVal(org.example.my_lang_parser.MyLanguageParser.ArgAsConstValContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx),
                FunctionArgument.ParamStyle.IN,
                FunctionArgument.ReferenceType.VALUE,
                getSourceText(nameCtx)));
    }


    @Override
    public void exitArgAsRef(org.example.my_lang_parser.MyLanguageParser.ArgAsRefContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx),
                FunctionArgument.ParamStyle.OUT,
                FunctionArgument.ReferenceType.REF,
                getSourceText(nameCtx)));
    }

    @Override
    public void exitArgAsConstRef(org.example.my_lang_parser.MyLanguageParser.ArgAsConstRefContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx),
                FunctionArgument.ParamStyle.IN,
                FunctionArgument.ReferenceType.REF,
                getSourceText(nameCtx)));
    }

    @Override
    public void exitArgAsPtr(org.example.my_lang_parser.MyLanguageParser.ArgAsPtrContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx),
                FunctionArgument.ParamStyle.OUT,
                FunctionArgument.ReferenceType.PTR,
                getSourceText(nameCtx)));
    }

    @Override
    public void exitArgAsConstPtr(org.example.my_lang_parser.MyLanguageParser.ArgAsConstPtrContext ctx) {
        var fun = functions.get(functions.size() - 1);
        var typeCtx = ctx.type();
        var nameCtx = ctx.variableName();
        fun.arguments.add(new FunctionArgument(getSourceText(typeCtx),
                FunctionArgument.ParamStyle.IN,
                FunctionArgument.ReferenceType.PTR,
                getSourceText(nameCtx)));
    }

    public List<MyLangFunction> getFunctions() {
        return functions;
    }

    private static String getSourceText(ParserRuleContext ctx) {
            int a = ctx.start.getStartIndex();
            int b = ctx.stop.getStopIndex();
            Interval interval = new Interval(a,b);
            return ctx.start.getInputStream().getText(interval);
    }

    public Map<String, List<MyLangFunction>> getModel() {
        List<String> list = new ArrayList<>();
        var map = new HashMap<String, List<MyLangFunction>>();
        map.put("functions", functions);
        return map;
    }

    public String getName() {
        return name;
    }
}
