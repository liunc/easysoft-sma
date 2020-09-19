package com.easysoft.sma;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class UserNameAuditorAware implements AuditorAware<String> {

	@Value("admin")
	private String username;

	/**
	 * 获取当前创建或修改的用户
	 * 
	 * @return
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(this.username);

		// UserDetails user;
		// try {
		// user = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// return Optional.ofNullable(user.getUsername());
		// }catch (Exception e){
		// return Optional.empty();
		// }
	}
}
