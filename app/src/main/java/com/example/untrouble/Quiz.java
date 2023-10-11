package com.example.untrouble;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

    private TextView totalQuestionsTextView;
    private TextView questionTextView;
    private Button ansA, ansB, ansC, ansD;
    private Button submitBtn;

    private int score = 0;
    private int totalQuestion;
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private List<Integer> questionIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        bindViews();
        setListeners();

        totalQuestion = QuestionAnswer.question.length;
        questionIndexes = new ArrayList<>();
        for (int i = 0; i < totalQuestion; i++) {
            questionIndexes.add(i);
        }
        Collections.shuffle(questionIndexes);

        loadNewQuestion();
    }

    private void bindViews() {
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
    }

    private void setListeners() {
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundResource(R.drawable.button_rounded);
        ansB.setBackgroundResource(R.drawable.button_rounded);
        ansC.setBackgroundResource(R.drawable.button_rounded);
        ansD.setBackgroundResource(R.drawable.button_rounded);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            if (selectedAnswer.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please select an answer before submitting.")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                if (selectedAnswer.equalsIgnoreCase(QuestionAnswer.correctAnswers[questionIndexes.get(currentQuestionIndex)])) {
                    score++;
                }
                currentQuestionIndex++;

                // Animasi tombol submit
                submitBtn.setScaleX(0.9f);
                submitBtn.setScaleY(0.9f);
                submitBtn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();

                loadNewQuestion();
            }
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundResource(R.drawable.button_rounded_gray);
        }
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == 10) {
            finishQuiz();
            return;
        }

        selectedAnswer = "";
        int questionIndex = questionIndexes.get(currentQuestionIndex);

        questionTextView.setText(QuestionAnswer.question[questionIndex]);
        List<String> choices = Arrays.asList(QuestionAnswer.choices[questionIndex]);
        Collections.shuffle(choices);
        ansA.setText(choices.get(0));
        ansB.setText(choices.get(1));
        ansC.setText(choices.get(2));
        ansD.setText(choices.get(3));
        totalQuestionsTextView.setText("Question number " + (currentQuestionIndex + 1) + " of 10");
    }

    private void finishQuiz() {
        String passStatus = score >= 6 ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + currentQuestionIndex)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        Collections.shuffle(questionIndexes);
        loadNewQuestion();
    }
}
