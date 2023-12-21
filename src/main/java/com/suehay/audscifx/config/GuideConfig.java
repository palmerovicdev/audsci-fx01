package com.suehay.audscifx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suehay.audscifx.model.templates.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.suehay.audscifx.utils.FileExtractor.*;
import static com.suehay.audscifx.utils.FileLocator.getPath;
import static java.lang.String.valueOf;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.list;

public class GuideConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String CONFIG_PATH = "resources" + File.separator + "config.json";
    private final String GUIDE_TEMPLATES_PATH = "resources" + File.separator + "test-templates";
    private final String LOGS_PATH = "resources" + File.separator + "logs";
    private final String TEST_RESULTS_PATH = "resources" + File.separator + "test-results";
    // results for test, this list is used to save the test results in the json files in the resources/test-results directory
    public static List<TestResult> testResults = new ArrayList<>();
    // templates for tests
    public static List<TestResult> testTemplates = new ArrayList<>();

    private long logsCount;

    // method to set the isUpdated property of the sonfig.json file to true
    public void updateGuidesTemplates() throws IOException, URISyntaxException {
        var config = objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class);
        saveConfig(config, true, logsCount);
    }

    /**
     * This method is used to save the test templates in the json files in the resources/test-templates directory
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public void saveTestTemplates() throws URISyntaxException, IOException {
        var config = objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class);
        logsCount = config.getLogsCount();
        try {
            processConfig();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(new PrintWriter(new FileWriter(getPath(LOGS_PATH) + (logsCount++) + ".json")));
            saveConfig(config, true, logsCount);
        }
    }

    /**
     * This method is used to save the test templates in the json files in the resources/test-templates directory
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public void chargeTestTemplates() throws URISyntaxException, IOException {
        var config = objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class);
        logsCount = config.getLogsCount();
        try {
            if (objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class).getIsUpdated()) processTemplatesCharge();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(new PrintWriter(new FileWriter(getPath(LOGS_PATH) + (logsCount++) + ".json")));
            saveConfig(config, true, logsCount);
        }
    }

    /**
     * This method is used to save the test results in the json files in the resources/test-results directory
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public void saveTestResultList() throws URISyntaxException, IOException {
        try {
            writeTestResultsToFile(testResults, Path.of(getPath(TEST_RESULTS_PATH)), objectMapper);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(new PrintWriter(new FileWriter(getPath(LOGS_PATH) + (logsCount++) + ".json")));
        }
    }

    /**
     * This method is used to charge the test results from the json files in the resources/test-results directory
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public void chargeTestResults() throws URISyntaxException, IOException {
        var config = objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class);
        logsCount = config.getLogsCount();
        try {
            if (objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class).getIsUpdated()) processResultsCharge();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(new PrintWriter(new FileWriter(getPath(LOGS_PATH) + (logsCount++) + ".json")));
            saveConfig(config, true, logsCount);
        }
    }

    private void saveConfig(Config config, boolean isUpdated, long logsCount) throws IOException, URISyntaxException {
        config.setLogsCount(logsCount);
        config.setIsUpdated(isUpdated);
        Files.writeString(Path.of(getPath(CONFIG_PATH)), objectMapper.writeValueAsString(config));
    }


    private void processTemplatesCharge() throws URISyntaxException, IOException {
        var resourcesPath = Path.of(getPath(GUIDE_TEMPLATES_PATH));
        testTemplates.clear();

        list(resourcesPath).forEach(path -> {
            try {
                testTemplates.add(objectMapper.readValue(loadFileAsAString(path.toString()), TestResult.class));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void processResultsCharge() throws URISyntaxException, IOException {
        var resourcesPath = Path.of(getPath(TEST_RESULTS_PATH));
        testTemplates.clear();
        list(resourcesPath).forEach(path -> {
            try {
                testResults.add(objectMapper.readValue(loadFileAsAString(path.toString()), TestResult.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void processConfig() throws IOException, URISyntaxException {
        var config = objectMapper.readValue(loadFileAsAString(getPath(CONFIG_PATH)), Config.class);

        if (!config.getIsUpdated()) {
            testTemplates = createTestResults();
            var guideTemplatesPath = Path.of(getPath(GUIDE_TEMPLATES_PATH));

            deleteFilesInDirectory(guideTemplatesPath);
            writeTestResultsToFile(testTemplates, guideTemplatesPath, objectMapper);
        }
        saveConfig(config, true, logsCount);
    }

    private void deleteFilesInDirectory(Path directoryPath) throws IOException {
        if (new File(valueOf(directoryPath)).list() != null) try (Stream<Path> paths = list(directoryPath)) {
            paths.forEach(path -> {
                try {
                    delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void writeTestResultsToFile(List<TestResult> testResults, Path directoryPath, ObjectMapper objectMapper) {
        testResults.forEach(testResult -> {
            var filePath = directoryPath.resolve(valueOf(testResult.getTest().getGuideVersion()));
            try {
                new File(filePath + ".json").createNewFile();
                Files.writeString(Path.of(filePath + ".json"), objectMapper.writeValueAsString(testResult));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public List<TestResult> createTestResults() throws URISyntaxException, IOException {
        var testResults = new ArrayList<TestResult>();
        var guidesPath = getPath("resources" + File.separator + "guides");
        // create a folder with mkdir
        var guideVersions = directoryDirectoryList(guidesPath + File.separator);
        for (String guideVersion : guideVersions) {
            testResults.add(TestResult.builder().test(createTestTemplate(guideVersion)).build());
        }
        return testResults;
    }

    private TestTemplate createTestTemplate(String guideVersion) throws IOException {
        var componentTemplates = list(Path.of(guideVersion)).map(Path::toString).toList().stream().map(s -> {
            try {
                return createComponentTemplate(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));
        var substring = guideVersion.substring(guideVersion.lastIndexOf(File.separator) + 1);
        return TestTemplate.builder().guideVersion(substring).componentTemplates(componentTemplates).build();
    }

    private ComponentTemplate createComponentTemplate(String componentPath) throws IOException {
        var componentTemplate =
                ComponentTemplate.builder().regulationTemplates(new ArrayList<>()).label(componentPath.substring(
                        componentPath.lastIndexOf(File.separator) + 1,
                        componentPath.lastIndexOf("."))).build();
        var componentContent = loadFileAsStringArr(componentPath);
        var lastRegulation = new Stack<RegulationTemplate>();
        var lastQuestion = new Stack<QuestionTemplate>();
        var lastSubQuestion = new Stack<QuestionTemplate>();

        for (var line : componentContent) {
            if (Objects.isNull(line) || line.isEmpty() || line.isBlank()) continue;
            if (Character.isUpperCase(line.charAt(0))) {
                if (!lastQuestion.isEmpty() && !lastRegulation.isEmpty()) lastRegulation.peek().getQuestionTemplates().add(lastQuestion.pop());
                if (!lastRegulation.isEmpty()) componentTemplate.getRegulationTemplates().add(lastRegulation.pop());
                lastRegulation.push(RegulationTemplate.builder().label(line).questionTemplates(new ArrayList<>()).build());

            } else if (Character.isDigit(line.charAt(0))) {
                if (!lastSubQuestion.isEmpty() && !lastQuestion.isEmpty()) lastQuestion.peek().getSubQuestions().add(lastSubQuestion.pop());
                if (!lastQuestion.isEmpty()) lastRegulation.peek().getQuestionTemplates().add(lastQuestion.pop());
                lastQuestion.push(QuestionTemplate.builder().subQuestions(new ArrayList<>()).label(line).build());
            } else if (Character.isLowerCase(line.charAt(0))) {
                if (!lastSubQuestion.isEmpty()) lastQuestion.peek().getSubQuestions().add(lastSubQuestion.pop());
                lastSubQuestion.push(QuestionTemplate.builder().label(line).build());
            }
        }
        if (!lastSubQuestion.isEmpty())
            lastQuestion.peek().getSubQuestions().add(lastSubQuestion.pop());
        if (!lastRegulation.isEmpty() && !lastQuestion.isEmpty())
            lastRegulation.peek().getQuestionTemplates().add(lastQuestion.pop());
        if (!lastRegulation.isEmpty())
            componentTemplate.getRegulationTemplates().add(lastRegulation.pop());
        return componentTemplate;
    }

    public void setTestResults() {
        // map the tests to test results

    }



/*    public static void main(String[] args) throws IOException, URISyntaxException {
        GuideConfig guideConfig = new GuideConfig();
        //guideConfig.updateGuidesTemplates();
        guideConfig.chargeTestResultList();
    }*/
}