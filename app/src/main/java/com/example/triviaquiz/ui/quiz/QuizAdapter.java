package com.example.triviaquiz.ui.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaquiz.data.network.model.Quiz;
import com.example.triviaquiz.databinding.ViewQuizQueBinding;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends ListAdapter<Quiz, QuizAdapter.QuizQueViewHolder> {

    public static DiffUtil.ItemCallback<Quiz> DIFF_CALLBACK = new DiffUtil.ItemCallback<Quiz>() {
        @Override
        public boolean areItemsTheSame(@NonNull Quiz oldItem, @NonNull Quiz newItem) {
            return oldItem.getQuestion().equals(newItem.getQuestion());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Quiz oldItem, @NonNull Quiz newItem) {
            return true;
        }
    };

    private OnAnswerClickListener listener;

    public QuizAdapter(OnAnswerClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuizQueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuizQueViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizQueViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public interface OnAnswerClickListener {
        void onAnswerClick(String ans);
    }

    public static class QuizQueViewHolder extends RecyclerView.ViewHolder {

        private ViewQuizQueBinding binding;

        public QuizQueViewHolder(@NonNull ViewQuizQueBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Quiz quiz, OnAnswerClickListener listener) {
            binding.quizQuestion.setText(quiz.getQuestion());
            binding.quizOptionA.setText(quiz.getAnswers().get(0));
            binding.quizOptionB.setText(quiz.getAnswers().get(1));
            if (quiz.getAnswers().size() == 2) {
                binding.quizOptionC.setVisibility(View.GONE);
                binding.quizOptionD.setVisibility(View.GONE);
                binding.quizOptionE.setVisibility(View.GONE);
            } else {
                binding.quizOptionC.setText(quiz.getAnswers().get(2));
                binding.quizOptionD.setText(quiz.getAnswers().get(3));
                if (quiz.getAnswers().size() == 4) {
                    binding.quizOptionE.setVisibility(View.GONE);
                } else {
                    binding.quizOptionD.setText(quiz.getAnswers().get(4));
                }
            }

            binding.quizOptionA.setOnClickListener(v -> listener.onAnswerClick(quiz.getAnswers().get(0)));
            binding.quizOptionB.setOnClickListener(v -> listener.onAnswerClick(quiz.getAnswers().get(1)));

            switch (quiz.getAnswers().size()) {
                case 4:
                    binding.quizOptionC.setOnClickListener(v -> listener.onAnswerClick(quiz.getAnswers().get(2)));
                    binding.quizOptionD.setOnClickListener(v -> listener.onAnswerClick(quiz.getAnswers().get(3)));
                case 5:
                    binding.quizOptionE.setOnClickListener(v -> listener.onAnswerClick(quiz.getAnswers().get(4)));
            }
        }

        public static QuizQueViewHolder from (ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ViewQuizQueBinding binding = ViewQuizQueBinding.inflate(inflater, parent, false);
            return new QuizQueViewHolder(binding);
        }


    }
}
