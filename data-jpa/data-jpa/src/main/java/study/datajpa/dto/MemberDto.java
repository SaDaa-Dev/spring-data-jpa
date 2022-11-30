package study.datajpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {
    private  Long id;
    private String userName;
    private String teamName;
    private int age;

    public MemberDto() {
    }

    @QueryProjection
    public MemberDto(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public MemberDto(Long id, String userName, String teamName) {
        this.id = id;
        this.userName = userName;
        this.teamName = teamName;
    }

    public MemberDto(Member member){
        this.id = member.getId();
        this.userName = member.getUserName();
    }
}
