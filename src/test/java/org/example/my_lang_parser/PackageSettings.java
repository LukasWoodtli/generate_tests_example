package org.example.my_lang_parser;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.intellij.IntelliJUltimateReporter;

public class PackageSettings
{
    public ApprovalFailureReporter UseReporter = IntelliJUltimateReporter.INSTANCE;
    public static String UseApprovalSubdirectory = "approval_files";
}