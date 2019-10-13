package com.just.chat.util;

import java.security.Principal;
import java.util.Objects;

public class PrincipalImp implements Principal {

	private String Name;
	
	
	public void setName(String name) {
		Name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Name;
	}
	
	@Override
    public boolean equals(Object another) {
        if (!(another instanceof Principal))
            return false;

        Principal principal = (Principal) another;
        return principal.getName() == Name;

    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }
}
