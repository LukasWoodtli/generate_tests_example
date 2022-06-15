package org.example.my_lang_parser;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MyLangFunction {
    private final String className;
    private final String returnType;
    private String functionName;
    public ArrayList<FunctionArgument> arguments = new ArrayList<FunctionArgument>();

    public MyLangFunction(String returnType, String functionName, String className) {
        this.returnType = returnType;
        this.functionName = functionName;
        this.className = className;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public ArrayList<FunctionArgument> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<FunctionArgument> arguments) {
        this.arguments = arguments;
    }

    public String getAsFunctionCall() {
        String sourceCode =  functionName;
        functionName += "(";
        functionName += arguments.stream().map(arg -> arg.getArgumentAsFunctionParam()).collect(Collectors.joining(", "));
        functionName +=  ");";

        return functionName;
    }

    public String getClassName() {
        return className;
    }

    private boolean isLastArgumentOutParam() {
        if (arguments.size() > 0) {
            var lastArg = arguments.get(arguments.size() - 1);
            return lastArg.getStyle() == FunctionArgument.ParamStyle.OUT;
        }

        return false;
    }

}
