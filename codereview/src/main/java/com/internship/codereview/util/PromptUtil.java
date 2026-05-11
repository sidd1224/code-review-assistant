package com.internship.codereview.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PromptUtil {
    private String promptTemplate="You are a senior software engineer performing a professional code review.\n" +
            "\n" +
            "Analyze the following Java code and identify issues.\n" +
            "\n" +
            "Instructions:\n" +
            "\n" +
            "* Detect bugs, code smells, and best practice violations\n" +
            "* Be precise and concise\n" +
            "* Do NOT explain unless necessary\n" +
            "* Focus only on actionable issues\n" +
            "\n" +
            "Return the output STRICTLY in JSON format with this structure:\n" +
            "\n" +
            "{\n" +
            "\"issues\": [\n" +
            "{\n" +
            "\"line\": number,\n" +
            "\"problem\": \"short description\",\n" +
            "\"suggestion\": \"clear fix\"\n" +
            "}\n" +
            "],\n" +
            "\"improvedCode\": \"full corrected code\"\n" +
            "}\n" +
            "\n" +
            "Rules:\n" +
            "\n" +
            "* Line numbers must match the given code exactly\n" +
            "* Do NOT return anything outside JSON\n" +
            "* Do NOT include markdown or explanations\n" +
            "* If no issues found, return empty issues array\n" +
            "\n" +
            "Code:\n" ;
}
