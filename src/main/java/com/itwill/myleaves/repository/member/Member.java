package com.itwill.myleaves.repository.member;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itwill.myleaves.dto.member.MemberUpdateDto;
import com.itwill.myleaves.repository.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "USER_INFO")
public class Member implements UserDetails {
	
	@Id
	private String userId;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = false)
	private String pwd;
	
	@Column(nullable = true)
	private String gender;
	
	@Column(nullable = true)
	private String birth;
	
	@Column(nullable = true)
	private String phone;
	
	@Column(nullable = true)
	private String email;
	
    @Column(nullable = true)
    private Role division;
    
    @Column(nullable = true)
	@CreationTimestamp
	private LocalDateTime joinDate;
	
    public Member update(MemberUpdateDto dto) {
    	this.gender = dto.getGender();
    	this.phone = dto.getPhone();
    	this.birth = dto.getBirth();
    	
    	return this;
    }
    
    public Member update(String newPwd) {
        this.pwd = newPwd;
        return this;
    }
    
	@Builder
	private Member(String userId, String name, String pwd, String gender, String birth, String phone, String email) {
		this.userId = userId;
		this.name = name;
		this.pwd = pwd;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
		this.email = email;
		this.division = Role.USER;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(division.getKey()));
	}

	@Override
	public String getUsername() {
		return userId;
	}
	
	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
