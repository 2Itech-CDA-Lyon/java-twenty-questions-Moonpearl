package twentyq.entity;

import javax.persistence.*;

@Entity
@Table(name = "solutions")
public class Solution
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    private Question parentQuestion;

    @Column(name = "parent_question_answer")
    private Boolean parentQuestionAnswer;

    public Solution() { }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Question getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(Question parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public Boolean getParentQuestionAnswer() {
        return parentQuestionAnswer;
    }

    public void setParentQuestionAnswer(Boolean parentQuestionAnswer) {
        this.parentQuestionAnswer = parentQuestionAnswer;
    }
}
