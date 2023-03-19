import java.util.ArrayList;

public class Network {

    /**
     * Class without the static keyword.
     */

    public class Member { // Member is an inner class of Network
        private String name;
        private ArrayList<Member> friends;

        public Member(String name) {
            this.name = name;
            friends = new ArrayList<>();
        }

    }

    private ArrayList<Member> members = new ArrayList<>();

    public Member enroll(String name) {
        var newMember = new Member(name);
        this.members.add(newMember);

        return newMember;

    }
}
