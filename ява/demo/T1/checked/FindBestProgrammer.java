package com.example.demo.T1.checked;

import java.util.*;

class Programmer implements Comparable<Programmer> {
    private final String surname;
    private final int experience;
    private final int skillsCount;

    public Programmer(String surname, int experience, int skillsCount) {
        this.surname = surname;
        this.experience = experience;
        this.skillsCount = skillsCount;
    }

    public String getSurname() {
        return surname;
    }

    public int getExperience() {
        return experience;
    }

    public int getSkillsCount() {
        return skillsCount;
    }

    public int compareTo(Programmer other) {
        if (other == null) return -1;
        int cmp = Integer.compare(other.skillsCount, this.skillsCount);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(other.experience, this.experience);
        if (cmp != 0) return cmp;
        return this.surname.compareTo(other.surname);
    }

    @Override
    public String toString() {
        return surname + "::" + skillsCount + "::" + experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programmer)) return false;
        Programmer that = (Programmer) o;
        return experience == that.experience &&
                skillsCount == that.skillsCount &&
                Objects.equals(surname, that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, experience, skillsCount);
    }
}

class ProgrammerData {
    private final List<Programmer> programmers = new ArrayList<>();

    public void add(Programmer p) {
        if (p != null) programmers.add(p);
    }

    public List<Programmer> getSorted() {
        List<Programmer> copy = new ArrayList<>(programmers);
        Collections.sort(copy);
        return copy;
    }

    public boolean isEmpty() {
        return programmers.isEmpty();
    }
}

class ProcessingProgrammerData {
    public List<String> processInputLines(List<String> inputLines) {
        ProgrammerData data = new ProgrammerData();

        if (inputLines == null || inputLines.isEmpty()) {
            return new ArrayList<>();
        }

        for (String raw : inputLines) {
            if (raw == null) continue;
            String line = raw.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("::", -1);
            if (parts.length != 3) continue;

            String surname = parts[0];
            String experienceStr = parts[1];
            String skillsPart = parts[2];

            if (surname == null || surname.isEmpty() ||
                    experienceStr == null || experienceStr.isEmpty() ||
                    skillsPart == null || skillsPart.isEmpty()) {
                continue;
            }

            int experience;
            try {
                experience = Integer.parseInt(experienceStr);
            } catch (NumberFormatException ex) {
                continue;
            }

            String[] skills = skillsPart.split(",", -1);
            int skillsCount = 0;
            for (String s : skills) {
                if (s != null && !s.isEmpty()) skillsCount++;
            }
            if (skillsCount == 0) continue;

            Programmer p = new Programmer(surname, experience, skillsCount);
            data.add(p);
        }

        List<Programmer> sorted = data.getSorted();
        List<String> result = new ArrayList<>(sorted.size());
        for (Programmer p : sorted) {
            result.add(p.toString());
        }
        return result;
    }
}

public class FindBestProgrammer {
    public static void main(String[] args) {
        ProcessingProgrammerData proc = new ProcessingProgrammerData();
        List<String> input = List.of(
                "Кошелев::9::Java,Python,C#,JavaScript,TypeScript,Go,Rust,Kotlin,Dart",
                "Мартынов::1::Scala",
                "Миронов::9::Java,Python,C#,JavaScript,TypeScript,Go,Rust,Kotlin,Dart",
                "Филимонов::1::Haskell",
                "   Сазонов::3::Elixir,Clojure",
                "Комиссаров::7::Swift,Ruby",
                "Прохоров::2::Perl,Lua",
                "Вишняков::8::Bash,Python,PHP",
                "Горбунов::1::HTML",
                "Еремин::9::Java,Python,C#,JavaScript,TypeScript,Go,Rust,Kotlin,Dart",
                "Зуев::1::CSS",
                "Трофимов::4::SQL,PLSQL",
                "Шевелёв::9::Java,Python,C#,JavaScript,TypeScript,Go,Rust,Kotlin,Dart",
                "Щукин::1::MySQL"
        );
        proc.processInputLines(input).forEach(System.out::println);
    }
}



