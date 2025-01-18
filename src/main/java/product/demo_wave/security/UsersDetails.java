package product.demo_wave.security;

import java.io.Serial;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

import product.demo_wave.entity.User;

public class UsersDetails implements UserDetails {

	@Serial private static final long serialVersionUID = -5466552448849562078L;

	@Getter
	private final User user;
	private final Collection<? extends GrantedAuthority> authorities;

	public UsersDetails(User user) {
		this.user = user;
		this.authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	/*
	public String getStaffName() {
		return user.getUserProfile().getStaffName();
	}

	public String getCompanyName() {
		return user.getUserProfile().getCompanyName();
	}
	public String getTel() {
		return user.getUserProfile().getTel();
	}

	public String getZip() {
		return user.getUserProfile().getZip();
	}

	public String getAddress() {
		return user.getUserProfile().getAddress();
	}

	public String getBankName() {
		return user.getUserProfile().getBankName();
	}

	public String getBankCode() {
		return user.getUserProfile().getBankCode();
	}

	public String getBranchName() {
		return user.getUserProfile().getBranchName();
	}

	public String getBranchCode() {
		return user.getUserProfile().getBranchCode();
	}

	public String getAccountType() {
		return user.getUserProfile().getAccountType();
	}

	public String getAccountNumber() {
		return user.getUserProfile().getAccountNumber();
	}

	public String getAccountHolder() {
		return user.getUserProfile().getAccountHolder();
	}
	*/

	public int getAccountId() {
		return user.getAccount().getId();
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
