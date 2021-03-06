package commerce.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNumber;

    @Column(nullable = false, unique = true, updatable = false, length = 20)
    private String memberId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 30)
    private String memberName;

    @OneToMany(mappedBy = "owner", orphanRemoval = true, targetEntity = Address.class, cascade = CascadeType.ALL)
    private List<Address> addressList = new ArrayList<>();

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @ManyToOne(optional = true)
    private Corporation affiliated;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    //FIXME 일대일 연관 테스트를 위해 회원은 한 개의 장바구니만 갖는 걸로 일단 매핑. 고치자잉~
    @OneToOne
    @JoinColumn(name = "ACTVIE_CART_ID", referencedColumnName = "cartId", nullable = true)
    private Cart cart;

    public Member(String memberId) {
        this.memberId = memberId;
    }

    public Member(String memberId, String password, String memberName, MemberType memberType, String email) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.memberType = memberType;
        this.email = email;
        this.memberStatus = MemberStatus.H; // 가입 대기 상태
    }

    public void addAddress(Address address) {
        address.setOwner(this);
        this.addressList.add(address);
    }

    /**
     * 비즈니스 키(3번 케이스) 비교 구현
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;

        Member member = (Member) o;

        return new EqualsBuilder()
                .append(memberId, member.getMemberId())
                .append(email, member.getEmail())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(memberId).append(email).toHashCode();
    }

    //TODO enum 공통화?
    public enum MemberType {
        E("ENTERPRISE"), P("PERSONAL");

        private String description;

        MemberType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    //TODO enum 공통화?
    public enum MemberStatus {
        A("ACTIVE"), I("INACTIVE"), L("LOCKED"), H("HOLD_ON_JOIN");

        private String description;

        MemberStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
