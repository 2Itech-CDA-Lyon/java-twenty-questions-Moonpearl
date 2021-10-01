package twentyq.entity;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text")
    private String text;
    
    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    private Question parentQuestion;

    @Column(name = "parent_question_answer")
    private Boolean parentQuestionAnswer;

    public Question() { }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public Question getParentQuestion() {
        return parentQuestion;
    }
    
    public void setParentQuestion(Question parent) {
        this.parentQuestion = parent;
    }

    public Boolean getParentQuestionAnswer() {
        return parentQuestionAnswer;
    }

    public void setParentQuestionAnswer(Boolean parentQuestionAnswer) {
        this.parentQuestionAnswer = parentQuestionAnswer;
    }
}
