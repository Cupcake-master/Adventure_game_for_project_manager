package ru.itis;

import ru.itis.models.Option;
import ru.itis.models.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tasker {
    private final List<String> answersAndEffects;
    private final List<String> questions;

    public Tasker(String filePathAnswers, String filePathTasks) {
        this.answersAndEffects = readFromFile(filePathAnswers);
        this.questions = readFromFile(filePathTasks);
    }

    public List<Task> getTasks() {
        List<HashMap<Option, String>> hashMaps = getHashMapOptionAndSting();
        return IntStream.range(0, questions.size()).mapToObj(i -> Task.builder()
                .condition(questions.get(i))
                .optionsAndEffects(hashMaps.get(i))
                .build()).collect(Collectors.toList());
    }

    private List<HashMap<Option, String>> getHashMapOptionAndSting() {
        List<String> effects = getEffects();
        List<Option> options = getOptions();
        return IntStream.range(0, questions.size())
                .mapToObj(i -> IntStream.range(i * 3, i * 3 + 3).boxed()
                        .collect(Collectors.toMap(options::get, effects::get, (a, b) -> b, HashMap::new)))
                .collect(Collectors.toList());
    }

    private List<Option> getOptions() {
        List<String> answers = getAnswers();
        List<String> conditions = getConditions();
        return IntStream.range(0, answers.size()).mapToObj(i -> Option.builder()
                .selectionCondition(conditions.get(i))
                .text(answers.get(i))
                .build()).collect(Collectors.toList());
    }

    private List<String> getConditions() {
        List<String> conditions = new ArrayList<>();
        for (int i = 0; i < answersAndEffects.size(); i += 3) {
            conditions.add(answersAndEffects.get(i));
        }
        return conditions;
    }


    private List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        for (int i = 1; i < answersAndEffects.size(); i += 3) {
            answers.add(answersAndEffects.get(i));
        }
        return answers;
    }

    private List<String> getEffects() {
        List<String> effects = new ArrayList<>();
        for (int i = 2; i < answersAndEffects.size(); i += 3) {
            effects.add(answersAndEffects.get(i));
        }
        return effects;
    }

    private List<String> readFromFile(String filepath) {
        List<String> lines = null;
        Path path = Paths.get(filepath);
        try (Stream<String> lineStream = Files.newBufferedReader(path).lines()) {
            lines = lineStream.filter(s -> !s.equals("")).collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }
}
