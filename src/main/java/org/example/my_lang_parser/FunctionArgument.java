package org.example.my_lang_parser;

public class FunctionArgument {
    enum ParamStyle {IN, OUT};

    enum ReferenceType {PTR, REF, VALUE};

    private String type;
    private ParamStyle style;
    private String name;
    private ReferenceType referenceType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParamStyle getStyle() {
        return style;
    }

    public void setStyle(ParamStyle style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FunctionArgument(String type, ParamStyle style, ReferenceType referenceType, String name) {
        this.type = type;
        this.style = style;
        this.referenceType = referenceType;
        this.name = name;
    }

    public String getArgumentAsStackVariableDeclaration() {
        String out = "";
        if (style == ParamStyle.IN) {
            out += "const ";
        }
        out += "auto " + name + " = " + type + "();";

        return out;
    }

    public String getArgumentAsFunctionParam() {
        String out = "";
        if (referenceType == ReferenceType.PTR) {
            out += '&';
        }
        out += name;

        return out;
    }
}
