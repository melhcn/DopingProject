package com.dopingProject.config;

import com.dopingProject.model.*;
import com.dopingProject.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Component
public class DataInitializer {

    private final StudentRepository studentRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final StudentTestRepository studentTestRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(StudentRepository studentRepository,
                           TestRepository testRepository,
                           QuestionRepository questionRepository,
                           StudentTestRepository studentTestRepository,
                           PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.studentTestRepository = studentTestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Öğrenciler
        Student s1 = new Student();
        s1.setName("Ali");
        s1.setSurname("Yılmaz");
        s1.setStudentNo("1001");
        s1.setEmail("ali@example.com");
        s1.setPassword(passwordEncoder.encode("1234"));
        studentRepository.save(s1);

        Student s2 = new Student();
        s2.setName("Ayşe");
        s2.setSurname("Demir");
        s2.setStudentNo("1002");
        s2.setEmail("ayse@example.com");
        s2.setPassword(passwordEncoder.encode("1234"));
        studentRepository.save(s2);

        // Matematik Testi
        Test t1 = createTest("Matematik Testi", "Toplama, çıkarma, çarpma soruları",
            List.of(
                new QuestionData("2 + 2 kaçtır?", "4", List.of("4", "3", "5", "6")),
                new QuestionData("5 - 3 kaçtır?", "2", List.of("2", "1", "3", "4")),
                new QuestionData("3 * 4 kaçtır?", "12", List.of("12", "9", "10", "15")),
                new QuestionData("10 / 2 kaçtır?", "5", List.of("5", "4", "6", "8")),
                new QuestionData("6 + 7 kaçtır?", "13", List.of("13", "12", "14", "15"))
            )
        );

        // Fen Bilimleri Testi
        Test t2 = createTest("Fen Bilimleri Testi", "Basit fen bilgisi soruları",
            List.of(
                new QuestionData("Dünya'nın uydusu nedir?", "Ay", List.of("Ay", "Mars", "Venüs", "Güneş")),
                new QuestionData("H2O nedir?", "Su", List.of("Su", "Tuz", "Gaz", "Metal")),
                new QuestionData("Güneş bir ... ?", "Yıldız", List.of("Yıldız", "Gezegen", "Uydusu", "Dağ")),
                new QuestionData("Bitkiler fotosentez yapar mı?", "Evet", List.of("Evet", "Hayır", "Bilmiyorum", "Yok")),
                new QuestionData("Buz eriyince ne olur?", "Su", List.of("Su", "Buhar", "Kum", "Kar"))
            )
        );

        // Türkçe Testi
        Test t3 = createTest("Türkçe Testi", "Dil bilgisi ve anlam bilgisi",
            List.of(
                new QuestionData("Hangisi zamirdir?", "O", List.of("O", "Masa", "Büyük", "Güzel")),
                new QuestionData("Cümlenin ögesi bulunur mu?", "Evet", List.of("Evet", "Hayır", "Zor", "Kolay")),
                new QuestionData("Fiil nedir?", "Eylem", List.of("Eylem", "İsim", "Sıfat", "Zarf")),
                new QuestionData("Hangi kelime eş anlamlıdır?", "Mutlu - Mesut", List.of("Mutlu - Mesut", "Kuru - Islak", "Büyük - Küçük", "Tatlı - Ekşi")),
                new QuestionData("Hangi cümlede nesne vardır?", "Ali topu attı", List.of("Ali topu attı", "Güneş doğdu", "Yağmur yağdı", "Çocuk güldü"))
            )
        );

        // Tarih Testi
        Test t4 = createTest("Tarih Testi", "Tarih genel kültür soruları",
            List.of(
                new QuestionData("1071'de ne oldu?", "Malazgirt", List.of("Malazgirt", "Ankara", "Çanakkale", "Dumlupınar")),
                new QuestionData("1923 neyi ifade eder?", "Cumhuriyet", List.of("Cumhuriyet", "Saltanat", "Meşrutiyet", "Göktürk")),
                new QuestionData("Fatih hangi şehri aldı?", "İstanbul", List.of("İstanbul", "Bursa", "İzmir", "Edirne")),
                new QuestionData("Atatürk'ün doğum yılı?", "1881", List.of("1881", "1871", "1891", "1901")),
                new QuestionData("Osmanlı ilk padişahı?", "Osman Gazi", List.of("Osman Gazi", "Yıldırım", "Kanuni", "II. Mehmet"))
            )
        );

        // Öğrencileri bazı testlere çözmüş kaydet
        saveStudentTest(s1, t1, Map.of(
                t1.getQuestions().get(0).getId(), "4",
                t1.getQuestions().get(1).getId(), "2",
                t1.getQuestions().get(2).getId(), "12",
                t1.getQuestions().get(3).getId(), "5",
                t1.getQuestions().get(4).getId(), "13"
        ));
        saveStudentTest(s1, t3, Map.of(
                t3.getQuestions().get(0).getId(), "O",
                t3.getQuestions().get(1).getId(), "Evet",
                t3.getQuestions().get(2).getId(), "Eylem",
                t3.getQuestions().get(3).getId(), "Mutlu - Mesut",
                t3.getQuestions().get(4).getId(), "Ali topu attı"
        ));
        saveStudentTest(s2, t2, Map.of(
                t2.getQuestions().get(0).getId(), "Ay",
                t2.getQuestions().get(1).getId(), "Su",
                t2.getQuestions().get(2).getId(), "Yıldız",
                t2.getQuestions().get(3).getId(), "Evet",
                t2.getQuestions().get(4).getId(), "Su"
        ));
        saveStudentTest(s2, t4, Map.of(
                t4.getQuestions().get(0).getId(), "Malazgirt",
                t4.getQuestions().get(1).getId(), "Cumhuriyet",
                t4.getQuestions().get(2).getId(), "İstanbul",
                t4.getQuestions().get(3).getId(), "1881",
                t4.getQuestions().get(4).getId(), "Osman Gazi"
        ));
    }

    private Test createTest(String testName, String description, List<QuestionData> questionDataList) {
        Test test = new Test();
        test.setTestName(testName);
        test.setDescription(description);
        testRepository.save(test);

        List<Question> questions = new ArrayList<>();
        for (QuestionData data : questionDataList) {
            Question q = new Question();
            q.setContent(data.getContent());
            q.setCorrectAnswer(data.getCorrectAnswer());
            q.setOptions(data.getOptions());
            q.setTest(test);
            questionRepository.save(q);
            questions.add(q);
        }
        test.setQuestions(questions);
        return test;
    }

    private void saveStudentTest(Student student, Test test, Map<Long, String> answers) {
        int correct = 0;
        int wrong = 0;
        for (Question q : test.getQuestions()) {
            String selected = answers.get(q.getId());
            if (selected != null && selected.equals(q.getCorrectAnswer())) {
                correct++;
            } else {
                wrong++;
            }
        }

        StudentTest st = new StudentTest();
        st.setStudent(student);
        st.setTest(test);
        st.setCorrectCount(correct);
        st.setWrongCount(wrong);
        st.setAnswers(answers);
        st.setDateTaken(java.time.LocalDateTime.now());
        studentTestRepository.save(st);
    }

    // küçük DTO class
    private static class QuestionData {
        private final String content;
        private final String correctAnswer;
        private final List<String> options;

        public QuestionData(String content, String correctAnswer, List<String> options) {
            this.content = content;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getContent() { return content; }
        public String getCorrectAnswer() { return correctAnswer; }
        public List<String> getOptions() { return options; }
    }
}
