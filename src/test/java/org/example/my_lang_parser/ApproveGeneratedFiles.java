package org.example.my_lang_parser;

import org.approvaltests.Approvals;
import org.approvaltests.namer.MultipleFilesLabeller;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class ApproveGeneratedFiles {
    private static RunFreeMarker run = new RunFreeMarker();
    private MultipleFilesLabeller labeller = NamerFactory.useMultipleFiles();


    @Test
    public void testList() throws IOException {
        String allCode = "";
        for (var inputFile : run.getInputFiles()) {
            String sourceCode = run.parseFileAndGenerateTest(inputFile);
            allCode += "\n===\n" + sourceCode;
        }
        Approvals.verify(allCode);
    }
}
