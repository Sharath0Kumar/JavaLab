package Xm_Practice;


public class Member {
    private int memberId;
    private String memberName;
    private String email;
    private String phone;

    public Member(int memberId, String memberName,
                  String email, String phone) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.phone = phone;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getEmail() {
        return email;
    }
}