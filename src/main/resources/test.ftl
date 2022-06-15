${functions[0].getClassName()}
    <#list functions as fun>
    ${fun.functionName}
    ${fun.getClassName()}
    ${fun.getFunctionName()}
    ${fun.getAsFunctionCall()}
        <#list fun.arguments as arg>
        ${arg.getArgumentAsStackVariableDeclaration()}
        </#list>
    </#list>
    ${functions?size}
